package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

public class wa extends AppCompatActivity {

    Button button;
    EditText editnama, editemail, editnomor, editpesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wa);

        editnama = findViewById(R.id.editnama);
        editemail = findViewById(R.id.editemail);
        editnomor = findViewById(R.id.editnomor);
        editpesan = findViewById(R.id.editpesan);

        button = findViewById(R.id.btnkirim);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pesan1 = editnama.getText().toString();
                String pesan2 = editemail.getText().toString();
                String pesan3 = editnomor.getText().toString();
                String pesan4 = editpesan.getText().toString();

                String semuapesan = "Nama: " +pesan1 + "\n" + "Email : " +pesan2 + "\n" + "Nomor Telepon : " +pesan3 + "\n" + "Pesan : " +pesan4;

                Intent kirimWA = new Intent(Intent.ACTION_SEND);
                kirimWA.setType("text/plain");
                kirimWA.putExtra(Intent.EXTRA_TEXT, semuapesan);
                kirimWA.putExtra("isda", "082150006167" + "@s.whatsapp.net");
                kirimWA.setPackage("com.whatsapp");

                startActivity(kirimWA);
            }
        });
    }
}