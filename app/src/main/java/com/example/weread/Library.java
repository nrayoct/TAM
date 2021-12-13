package com.example.weread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Library extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context mContext;
    //firebase:
    private DatabaseReference myRef;
    //variabel:
    private ArrayList<Book>bookList;
    private AdapterRecyclerViewHome adapterRecyclerViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        //arraylist
        bookList = new ArrayList<>();
        GetDataFromFirebase();
        //inisialisasi and assign variabel
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.library);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       overridePendingTransition(0,0);
                       finishAffinity();
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.library:
//                        startActivity(new Intent(getApplicationContext(), Library.class));
//                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void GetDataFromFirebase() {
        Query query= myRef.child("book");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                int no = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("favorite").getValue().toString().equals("1")) {
                        Book book = new Book();
                        book.setImageURL(snapshot.child("image").getValue().toString());
                        book.setTitle(snapshot.child("title").getValue().toString());
                        book.setId(snapshot.child("id").getValue().toString());

                        bookList.add(book);
                        no++;
                    }
                }
                if (no == 0) {
                    Toast.makeText(mContext, "Data Favorite Kosong", Toast.LENGTH_LONG).show();
                }
                adapterRecyclerViewHome = new AdapterRecyclerViewHome(getApplicationContext(), bookList);
                recyclerView.setAdapter(adapterRecyclerViewHome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll(){
        if (bookList != null){
            bookList.clear();
            if (adapterRecyclerViewHome != null){
                adapterRecyclerViewHome.notifyDataSetChanged();
            }
        }
        bookList = new ArrayList<>();
    }
}