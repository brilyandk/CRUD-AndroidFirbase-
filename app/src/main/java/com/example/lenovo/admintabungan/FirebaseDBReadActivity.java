package com.example.lenovo.admintabungan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FirebaseDBReadActivity extends AppCompatActivity implements AdapterNewsRecyclerView.FirebaseDataListener {

    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<News> daftarNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Mengeset layout
         */
        setContentView(R.layout.activity_db_read);

        /**
         * Inisialisasi RecyclerView & komponennya
         */
        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        /**
         * Mengambil data News dari Firebase Realtime DB
         */
        database.child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarNews = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object News
                     * Dan juga menyimpan primary key pada object News
                     * untuk keperluan Edit dan Delete data
                     */
                    News news = noteDataSnapshot.getValue(News.class);
                    news.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object News yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarNews.add(news);
                }

                /**
                 * Inisialisasi adapter dan data News dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new AdapterNewsRecyclerView(daftarNews, FirebaseDBReadActivity.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }


    public static Intent getActIntent(Activity activity){
        return new Intent(activity, FirebaseDBReadActivity.class);
    }

    @Override
    public void onDeleteData(News news, int position) {

        if(database!=null){database.child("news").child(news.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FirebaseDBReadActivity.this,"Data Dihapus", Toast.LENGTH_LONG).show();
            }
        });

        }
    }
}

