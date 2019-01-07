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


public class FirebaseDBReadAsisten extends AppCompatActivity implements AdapterAsistenRecyclerView.FirebaseDataListener {

    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Asisten> daftarAsisten;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Mengeset layout
         */
        setContentView(R.layout.activity_asisten_read);

        /**
         * Inisialisasi RecyclerView & komponennya
         */
        rvView = (RecyclerView) findViewById(R.id.rv_mainAsisten);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        /**
         * Mengambil data Asisten dari Firebase Realtime DB
         */
        database.child("asisten").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarAsisten = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Asisten
                     * Dan juga menyimpan primary key pada object Asisten
                     * untuk keperluan Edit dan Delete data
                     */
                    Asisten asisten = noteDataSnapshot.getValue(Asisten.class);
                    asisten.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Asisten yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarAsisten.add(asisten);
                }

                /**
                 * Inisialisasi adapter dan data Asisten dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new AdapterAsistenRecyclerView(daftarAsisten, FirebaseDBReadAsisten.this);
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
        return new Intent(activity, FirebaseDBReadAsisten.class);
    }

    @Override
    public void onDeleteData(Asisten asisten, int position) {

        if(database!=null){database.child("asisten").child(asisten.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FirebaseDBReadAsisten.this,"Data Dihapus", Toast.LENGTH_LONG).show();
            }
        });

        }
    }
}

