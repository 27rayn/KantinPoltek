package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.project.R;

public class IntroActivity extends AppCompatActivity {

    private ConstraintLayout startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startBtn=findViewById(R.id.startbtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next;
                next = new Intent(IntroActivity.this, SignInActivity.class);
                startActivity(next);

            }
        });
    }
}