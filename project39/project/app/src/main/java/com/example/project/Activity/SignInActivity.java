package com.example.project.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.project.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    EditText ETUsername, ETPassword;
    String id, username, password, nim, email;

    TextView textSignup, forgotPass;
    AppCompatButton SignIn;

    ProgressDialog progressDialog;

    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        textSignup = findViewById(R.id.textsignin);
        SignIn = findViewById(R.id.SignIn_btn);

        ETUsername = (EditText) findViewById(R.id.username);
        ETPassword = (EditText) findViewById(R.id.pass);

        progressDialog = new ProgressDialog(this);


        sp1 = getSharedPreferences("AllData", MODE_PRIVATE);

        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sign In..."); // pesan yang muncul
                progressDialog.setCancelable(false); // mengirim data tidak dapat di cancel
                progressDialog.show(); // menampilkan dialog

                username = ETUsername.getText().toString();
                password = ETPassword.getText().toString();

                //Intent toCurrent;
                //toCurrent = new Intent(SignInActivity.this, MainActivity.class);
                //toCurrent.putExtra("Username", username);
                //startActivity(toCurrent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validasiData();
                    }
                },1000);
            }
        });
    }

    void validasiData(){
        // jika tidak ada data yang diinputkan
        if(username.equals("") || password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(SignInActivity.this, "Username and password can not be blank!", Toast.LENGTH_SHORT).show();
        }else {

            kirimData();
            getData();

        }}

    void kirimData(){
        // menggunakan library dengan menggunakan post untuk mengirim data
        AndroidNetworking.post("https://tkjb2019.com/mobile/api_kelompok_7/signIn.php")
                // parameter2 / apa2 saja yg ingin dimasukkan
                .addBodyParameter("username",""+username)
                .addBodyParameter("password",""+password)
                // prioritas misalkan ada data yang ingin sama2 mengirim
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(SignInActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            // jika status true
                            if(status){

                                Intent toCurrent;
                                toCurrent = new Intent(SignInActivity.this, MainActivity.class);
                                //toCurrent.putExtra("Username", username);
                                startActivity(toCurrent);

                                //Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                //startActivity(i);
                                //SignInActivity.this.finish();
                            }
                            // jika status false
                            else{
                                new AlertDialog.Builder(SignInActivity.this)
                                        .setMessage("Wrong password or username!")
                                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                //Intent i = new Intent(SignInActivity.this, SignInActivity.class);
                                                //startActivity(i);
                                                //SignInActivity.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    // jika ada error
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }

    void getData(){
        AndroidNetworking.post("http://tkjb2019.com/mobile/api_kelompok_7/getDataSP.php")
                .addBodyParameter("username",username)
                .addBodyParameter("password", password)
                .setPriority( Priority.MEDIUM)
                .setTag("getData")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener () {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekdata",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
//                            String pesan = response.getString("result");
//                            Toast.makeText(SignInActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status","" + status);
                            if (status) {
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);
                                    id = jo.getString("id");
                                    username = jo.getString("username");
                                    nim = jo.getString("nim");
                                    email = jo.getString("email");
                                    password = jo.getString("password");
                                }
                                SharedPreferences.Editor editor = sp1.edit();
                                editor.putString("id", id);
                                editor.putString ( "username",username );
                                editor.putString("nim",nim );
                                editor.putString ( "email",email );
                                editor.putString ( "password",password );
                                editor.commit ();

                              Toast.makeText(SignInActivity.this, "berhasil "+ username, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignInActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorLogin",""+anError.getErrorBody());
                    }
                });
    }
}