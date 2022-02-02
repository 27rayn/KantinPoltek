package com.example.project.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.CartListAdapter;
import com.example.project.Helper.ManagementCart;
import com.example.project.Interface.ChangeNumberItemsListener;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    ManagementCart managementCart;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, TVcheckout;
    double tax, total, totalHarga;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        managementCart = new ManagementCart(this);

        TVcheckout = findViewById(R.id.textView16);
        TVcheckout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateCard();
                String wpurl="http://wa.me/+62895801210788?text=" +
                        "Halo! Saya memesan makanan di kantin Anda\n\n" +
                        "Total harga orderan Saya adalah Rp " + totalHarga + "\n" +
                        "\n -Kantin Poltek Project-";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });

        initView();
        initList();
        calculateCard();
        bottomNavigation();
    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }
    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCard(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    public void calculateCard() {
        double percentTax = 0.02;
        int delivery = 10;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
        total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;

        totalFeeTxt.setText("Rp " + itemTotal);
        taxTxt.setText("Rp " + tax);
        deliveryTxt.setText("Rp " + delivery);
        totalTxt.setText("Rp " + total);

        totalHarga = total;
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerview);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView4);
    }
}