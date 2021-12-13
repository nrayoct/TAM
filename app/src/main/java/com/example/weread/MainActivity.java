package com.example.weread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //widget
    RecyclerView recyclerView;

    //firebase:
    private DatabaseReference myRef;

    //variabel:
    private ArrayList<Book>bookList;
    private AdapterRecyclerViewHome adapterRecyclerViewHome;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);


        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //arraylist
        bookList = new ArrayList<>();


        //clear arraylist
        ClearAll();

        //get data method;
        GetDataFromFirebase();

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
                       return true;
                   case R.id.search:
                       startActivity(new Intent(getApplicationContext(), Search.class));
                       overridePendingTransition(0,0);
                       finishAffinity();
                       return true;
                   case R.id.library:
                       startActivity(new Intent(getApplicationContext(), Library.class));
                       overridePendingTransition(0,0);
                       finishAffinity();
                       return true;
               }
                return false;
            }
        });

    }

    private void GetDataFromFirebase() {
        Query query = myRef.child("book");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Book book = new Book();

                    book.setImageURL(snapshot.child("image").getValue().toString());
                    book.setTitle(snapshot.child("title").getValue().toString());
                    book.setId(snapshot.child("id").getValue().toString());

                    bookList.add(book);
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