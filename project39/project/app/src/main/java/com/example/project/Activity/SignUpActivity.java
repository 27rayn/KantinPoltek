package com.example.project.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

public class    SignUpActivity extends AppCompatActivity {

    TextView textSignIn;
    AppCompatButton SignUp;
    // menampung nilai dari inputan
    String username, nim, email, password;
    EditText ETName, ETUsername, ETEmail, ETPassword;
    // loading
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ETName        = findViewById(R.id.name);
        ETUsername    = findViewById(R.id.username);
        ETEmail       = findViewById(R.id.email);
        ETPassword    = findViewById(R.id.pass);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_sign_up);

        SignUp = (AppCompatButton) findViewById(R.id.SignUp_btn);
        textSignIn = (TextView) findViewById(R.id.signinn_btn);

        progressDialog = new ProgressDialog(this);

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
                SignUpActivity.this.finish();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sign Up..."); // pesan yang muncul
                progressDialog.setCancelable(false); // mengirim data tidak dapat di cancel
                progressDialog.show(); // menampilkan dialog

                // ambil data
                username    = ETName.getText().toString();
                nim         = ETUsername.getText().toString();
                email       = ETEmail.getText().toString();
                password    = ETPassword.getText().toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validasiData();
                    }
                },1000);
            }
        });
    }

    // berisi kondisi
    void validasiData(){
        // jika tidak ada data yang diinputkan
        if(username.equals("") || nim.equals("") || email.equals("") || password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "Data cannot be empty! Please enter a valid data.", Toast.LENGTH_SHORT).show();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    kirimData();
                }
            },1000);
        }
    }

    // kirim data
    void kirimData(){
        // menggunakan library dengan menggunakan post untuk mengirim data
        AndroidNetworking.post("https://tkjb2019.com/mobile/api_kelompok_7/signUp.php")
                // parameter2 / apa2 saja yg ingin dimasukkan
                .addBodyParameter("username",""+username)
                .addBodyParameter("nim",""+nim)
                .addBodyParameter("email",""+email)
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
                            Toast.makeText(SignUpActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            // jika status true
                            if(status){
                                new AlertDialog.Builder(SignUpActivity.this)
                                        .setMessage("Registration completed successfully! Please sign in to access your account!")
                                        .setCancelable(false)
                                        .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                                                startActivity(i);
                                                SignUpActivity.this.finish();
                                            }
                                        })
                                        .show();
                            }
                            // jika status false
                            else{
                                new AlertDialog.Builder(SignUpActivity.this)
                                        .setMessage("Registration failed! Please try again.")
                                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                //Intent i = new Intent(SignUpActivity.this, SignUpActivity.class);
                                                //startActivity(i);
                                                //SignUpActivity.this.finish();
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
}