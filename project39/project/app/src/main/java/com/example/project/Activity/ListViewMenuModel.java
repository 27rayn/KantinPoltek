package com.example.project.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListViewMenuModel extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vNama;
    private ArrayList<String> vHarga;
    private ArrayList<String> vFoto;

    public ListViewMenuModel(Activity context, ArrayList<String> Nama, ArrayList<String> Harga, ArrayList<String> Foto)
    {
        super(context, R.layout.listview_menu, Nama);
        this.context = context;
        this.vNama = Nama;
        this.vHarga = Harga;
        this.vFoto = Foto;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View Rview = inflater.inflate(R.layout.listview_menu, null, false);

        TextView tvNama = Rview.findViewById(R.id.tv_list_nama);
        TextView tvHarga = Rview.findViewById(R.id.tv_list_harga);
        ImageView foto = Rview.findViewById(R.id.fotomenu);

        tvNama.setText(vNama.get(position));
        tvHarga.setText(vHarga.get(position));
        if (vFoto.get(position).equals(""))
        {
            Picasso.get().load("https://tkjb2019.com/mobile/image/barang_user_R/boxdefault.png").into(foto);
        }else
        {
            Picasso.get().load("https://tkjb2019.com/mobile/image/"+vFoto.get(position)).into(foto);
        }

        return Rview;
    }


}
