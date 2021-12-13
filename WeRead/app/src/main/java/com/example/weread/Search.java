package com.example.weread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
=======
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    ProgressDialog pDialog;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView recyclerView;
    private Context mContext;
    //firebase:
    private DatabaseReference myRef;
    //variabel:
    private ArrayList<Book>bookList;
    private AdapterRecyclerViewHome adapterRecyclerViewHome;
    String search = "";
>>>>>>> d30b291 (final 1.0)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //isi search
<<<<<<< HEAD
        mSearchField =(EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.imageButton);
        mResultList = (RecyclerView) findViewById(R.id.result_list);
=======
        mContext = this;
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        mSearchField =(EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.imageButton);
        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        //arraylist
        bookList = new ArrayList<>();
        GetDataFromFirebase("All");
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDialog();
                //clear arraylist
                ClearAll();
                GetDataFromFirebase(mSearchField.getText().toString());
            }
        });
>>>>>>> d30b291 (final 1.0)

        //inisialisasi and assign variabel
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.search);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       overridePendingTransition(0,0);
<<<<<<< HEAD
=======
                       finishAffinity();
>>>>>>> d30b291 (final 1.0)
                        return true;
                    case R.id.search:
//                        startActivity(new Intent(getApplicationContext(), Search.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.library:
                        startActivity(new Intent(getApplicationContext(), Library.class));
                        overridePendingTransition(0,0);
<<<<<<< HEAD
=======
                        finishAffinity();
>>>>>>> d30b291 (final 1.0)
                        return true;
                }
                return false;
            }
        });
    }
<<<<<<< HEAD
=======

    private void GetDataFromFirebase(String search1) {
        if (search1.equals("")) {
            search = "All";
        } else {
            // merubah huruf kecil ke awal huruf besar
            String search2 = search1.toLowerCase();
            String[] search3 = search2.split(" ");
            int key = 0;
            search = "";
            for (String s : search3) {
                if (key == 0) {
                    search += s.substring(0, 1).toUpperCase() + s.substring(1);
                } else {
                    search += " " + s.substring(0, 1).toUpperCase() + s.substring(1);
                }
                key++;
            }
        }
        Query query= myRef.child("book");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                int no = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (search.equals("All")) {
                        // ambil data keseluruhan
                        Book book = new Book();
                        book.setImageURL(snapshot.child("image").getValue().toString());
                        book.setTitle(snapshot.child("title").getValue().toString());
                        book.setId(snapshot.child("id").getValue().toString());

                        bookList.add(book);
                        no++;
                    } else {
                        // ambil data sesuai pencarian
                        if (snapshot.child("title").getValue().toString().contains(search)) {
                            Book book = new Book();
                            book.setImageURL(snapshot.child("image").getValue().toString());
                            book.setTitle(snapshot.child("title").getValue().toString());
                            book.setId(snapshot.child("id").getValue().toString());

                            bookList.add(book);
                            no++;
                        }
                    }
                }
                if (no == 0) {
                    Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                }
                adapterRecyclerViewHome = new AdapterRecyclerViewHome(getApplicationContext(), bookList);
                recyclerView.setAdapter(adapterRecyclerViewHome);
                hideDialog();
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
>>>>>>> d30b291 (final 1.0)
}