package com.example.project.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.CategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    SharedPreferences sp1;
    String nama;
    TextView TVnama;
    Button BTNMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();

        ImageView imageView = findViewById(R.id.imageView3);

        sp1 = getSharedPreferences("AllData", MODE_PRIVATE);

        TVnama = findViewById(R.id.textView4);
        nama = sp1.getString("username", null);
        TVnama.setText(nama);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfilePage.class));
            }
        });

        BTNMenu = findViewById(R.id.menu_btn);

        BTNMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListMenu.class));
            }
        });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout profileHelp = findViewById(R.id.profileHelp);
        LinearLayout profileInfo = findViewById(R.id.profileInfo);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilePage.class));
            }
        });

//        profileHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, HelpPage.class));
//            }
//        });
//
//        profileInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, InfoPage.class));
//            }
//        });
    }



    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Nasi Telur Komplit", "nasitelur", " Nasi disertai telur yang renyah ", 10000));
        foodlist.add(new FoodDomain("Nasi Ayam Lalapan", "nasiayam", " Nasi dengan ayam yang gurih ", 13000));
        foodlist.add(new FoodDomain("Nasi Goreng", "nasigoreng", " Nasi goreng dengan pilihan berbagai topping", 11000));

        adapter2 = new PopularAdapter(foodlist);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("Ayam", "ayam"));
        categoryList.add(new CategoryDomain("Mie", "mie"));
        categoryList.add(new CategoryDomain("Snack", "snack"));
        categoryList.add(new CategoryDomain("Minuman", "cat_4"));
        categoryList.add(new CategoryDomain("Donat", "kantinpoltek"));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
    }
}