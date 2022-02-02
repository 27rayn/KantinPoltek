//package com.example.project.Activity;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.project.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Profile extends Fragment {
//
//    View view;
//
//    RecyclerView GridRecycle;
//
//    private List<InfoPages> listInfoPages;
//
//    public Profile() {}
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        GridRecycle = view.findViewById(R.id.gridInfo);
//
//        InfoPageAdapter infoPageAdapter = new InfoPageAdapter(getContext(), listInfoPages);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2, GridLayoutManager.VERTICAL, false);
//        GridRecycle.setLayoutManager(gridLayoutManager);
//        GridRecycle.setAdapter(infoPageAdapter);
//
//        return view;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        listInfoPages = new ArrayList<>();
//        listInfoPages.add(new InfoPages("Orange Fun Fact", "Haeratunnisa", R.drawable.nasiayam, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Grape Benefits", "Wiah", R.drawable.ayam, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Strawberries", "Isda", R.drawable.burger, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Kiwi for Baby", "Riskatul", R.drawable.cat_3, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Apple and smile", "Dian", R.drawable.cat_4, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Rare Berries", "Adila", R.drawable.cat_5, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("2 Oranges", "Nurul", R.drawable.nasiayam, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("A Grape", "Iska", R.drawable.nasigoreng, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Red is good?", "Zahra", R.drawable.nasigoreng, R.drawable.ic_icon_viewall));
//        listInfoPages.add(new InfoPages("Berries", "Husnul", R.drawable.nasitelur, R.drawable.ic_icon_viewall));
//
//    }
//
//}