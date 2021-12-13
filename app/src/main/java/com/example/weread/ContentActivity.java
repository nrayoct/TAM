package com.example.weread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ContentActivity extends AppCompatActivity {

    Button buttonAdd;
    Button buttonBaca;
    TextView textTitle, textDeskripsi, textAuthor;
    ImageView priview;
    String book_id = "";
    Context mContext;
    //firebase:
    private DatabaseReference myRef;
    ProgressDialog progressDialog;
    int paporit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mContext = this;
        book_id = getIntent().getExtras().getString("book_id");
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonBaca = findViewById(R.id.buttonBaca);
        textDeskripsi = findViewById(R.id.textDeskripsi);
        textTitle = findViewById(R.id.textTitle);
        textAuthor = findViewById(R.id.textAuthor);
        priview = findViewById(R.id.priview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);

        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFavorite();
            }
        });
        buttonBaca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity1 = new Intent(mContext, PDFActivity.class);
                newActivity1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newActivity1.putExtra("book_id", book_id);
                startActivity(newActivity1);
            }
        });
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
                        Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentHome);
                        finishAffinity();
                        return true;
                    case R.id.search:
                        Intent intentSearch = new Intent(getApplicationContext(), Search.class);
                        startActivity(intentSearch);
                        finishAffinity();
                        return true;
                    case R.id.library:
                        Intent intentLibrary = new Intent(getApplicationContext(), Library.class);
                        startActivity(intentLibrary);
                        finishAffinity();
                        return true;
                }
                return false;
            }
        });
    }

    private void addFavorite() {
        String cild = book_id;
        if (paporit == 0) {
            myRef.child("book").child(cild).child("favorite").setValue(String.valueOf(1));
            Toast.makeText(mContext, "Ditambahkan ke Favorit", Toast.LENGTH_LONG).show();
            buttonAdd.setText("Hapus Favorite");
        } else {
            myRef.child("book").child(cild).child("favorite").setValue(String.valueOf(0));
            Toast.makeText(mContext, "Dihapus Dari Favorit", Toast.LENGTH_LONG).show();
            buttonAdd.setText("Tambah Favorite");
        }
    }

    private void GetDataFromFirebase() {
        Query query = myRef.child("book").orderByChild("id").equalTo(book_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("favorite").getValue().toString().equals("0")) {
                        paporit = 0;
                        buttonAdd.setText("Tambah Favorite");
                    } else {
                        paporit = 1;
                        buttonAdd.setText("Hapus Favorite");
                    }
                    textTitle.setText(snapshot.child("title").getValue().toString());
                    textDeskripsi.setText(snapshot.child("description").getValue().toString());
                    textAuthor.setText(snapshot.child("author").getValue().toString());
                    Glide.with(getApplicationContext())
                            .load(snapshot.child("image").getValue().toString()).centerCrop()
                            .into(priview);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}