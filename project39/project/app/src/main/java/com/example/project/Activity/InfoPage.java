package com.example.project.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

public class InfoPage extends AppCompatActivity {

    Button BTNLogout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        BTNLogout = findViewById(R.id.logout_btn);

        BTNLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoPage.this, MainActivity.class));
            }
        });

//        BTNLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id==R.id.logout_btn){
//            new AlertDialog.Builder(ProfilePage.this)
//                    .setMessage("Do you want to logout your account ?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            progressDialog.setMessage("Logout...");
//                            progressDialog.setCancelable(false);
//                            progressDialog.show();
//
//                                                    new AlertDialog.Builder(ProfilePage.this)
//                                                            .setMessage("Your account logged out successfully !")
//                                                            .setCancelable(false)
//                                                            .setPositiveButton("back", new DialogInterface.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(DialogInterface dialog, int which) {
//                                                                    Intent i = new Intent(ProfilePage.this, SignInActivity.class);
//                                                                    startActivity(i);
//                                                                    ProfilePage.this.finish();
//                                                                }
//                                                            })
//                                                            .show();
//                        }
//                    })
//                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    })
//                    .show();
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}