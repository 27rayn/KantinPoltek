package com.example.project.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.project.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListMenu extends AppCompatActivity {

    ListView listView;
    ArrayList<String> array_nama, array_harga, array_foto;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        listView = findViewById(R.id.listmenu);

        getData();
    }

    void initializeArray(){
        array_nama = new ArrayList<String>();
        array_harga = new ArrayList<String>();
        array_foto = new ArrayList<String>();

        array_nama.clear();
        array_harga.clear();
        array_foto.clear();
    }

    void getData(){
            initializeArray();
            AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_7/getDataMenu.php")
                    .setTag("Get Data")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Boolean status = response.getBoolean("status");
                                if (status) {
                                    JSONArray ja = response.getJSONArray("result");
                                    Log.d("respon", "" + ja);
                                    for (int i = 0; i < ja.length(); i++) {
                                        JSONObject jo = ja.getJSONObject(i);
                                        array_nama.add(jo.getString("nama"));
                                        array_harga.add(jo.getString("harga"));
                                        array_foto.add(jo.getString("foto"));
                                    }

                                    final ListViewMenuModel adapter = new ListViewMenuModel(ListMenu.this, array_nama, array_harga, array_foto);
                                    listView.setAdapter(adapter);


                                } else {
                                    Toast.makeText(ListMenu.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }
