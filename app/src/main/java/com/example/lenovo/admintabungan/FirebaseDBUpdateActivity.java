package com.example.lenovo.admintabungan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseDBUpdateActivity extends AppCompatActivity {

    //variable untuk refers ke Firebase Realtime Database
    private DatabaseReference database;

    //variable komponen dalam layout
    private Button update;

    private EditText etjudul;
    private EditText etkategori;
    private EditText etketerangan;
    private EditText etlink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_update);

        //inisiasi komponen dari layout
        update = (Button) findViewById(R.id.update);
        etjudul = (EditText) findViewById(R.id.et_judulNews);
        etkategori = (EditText) findViewById(R.id.et_kategoriNews);
        etketerangan = (EditText) findViewById(R.id.et_keterangan);
        etlink = (EditText) findViewById(R.id.et_linkNews);

        //mengambil reference database ke Firebase
        database = FirebaseDatabase.getInstance().getReference();

        final News dataModel = (News) getIntent().getSerializableExtra("data");

        if (dataModel != null) {
            etjudul.setText(dataModel.getJudul());
            etkategori.setText(dataModel.getKategori());
            etketerangan.setText(dataModel.getKeterangan());
            etlink.setText(dataModel.getLink());

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataModel.setJudul(etjudul.getText().toString());
                    dataModel.setKategori(etkategori.getText().toString());
                    dataModel.setKeterangan(etketerangan.getText().toString());
                    dataModel.setLink(etlink.getText().toString());
                }
            });
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = dataModel.getKey();
                String judul = etjudul.getText().toString().trim();
                String kategori = etkategori.getText().toString().trim();
                String keterangan = etketerangan.getText().toString().trim();
                String link = etlink.getText().toString().trim();

                updateDataModel(key, judul, kategori, keterangan, link);

            }
        });
    }

    private boolean updateDataModel(String key, String judul, String kategori, String keterangan, String link)
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("news").child(key);

        News dataModel = new News(judul, kategori, keterangan,link);

        databaseReference.setValue(dataModel);

        Toast.makeText(this, "Data Berhasil diubah", Toast.LENGTH_LONG).show();

        return true;
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, FirebaseDBUpdateActivity.class);
    }
}