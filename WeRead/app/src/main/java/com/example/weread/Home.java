package com.example.weread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //inisialisasi and assign variabel
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
//                       startActivity(new Intent(getApplicationContext(), Home.class));
//                       overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.library:
                        startActivity(new Intent(getApplicationContext(), Library.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        int []image = {
                R.drawable.dr_pergi, R.drawable.dr_sebatas_mimpi,
                R.drawable.dr_hujan, R.drawable.dr_renungan_pagi,
                R.drawable.dr_anarrative, R.drawable.dr_the_spine,
                R.drawable.dr_sang_pemimpi, R.drawable.dr_pulang
        };

        RecyclerView recyclerView;
        AdapterRecyclerViewHome adapterRecyclerViewHome;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        adapterRecyclerViewHome = new AdapterRecyclerViewHome(image);
        recyclerView.setAdapter(adapterRecyclerViewHome);
    }
}