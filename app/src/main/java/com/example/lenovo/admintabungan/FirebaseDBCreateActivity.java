package com.example.lenovo.admintabungan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class FirebaseDBCreateActivity extends AppCompatActivity {

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etJudul;
    private EditText etKategori;
    private EditText etKeterangan;
    private EditText etLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_create);

        // inisialisasi fields EditText dan Button
        etJudul = (EditText) findViewById(R.id.et_judulNews);
        etKategori = (EditText) findViewById(R.id.et_kategoriNews);
        etKeterangan = (EditText) findViewById(R.id.et_keterangan);
        etLink = (EditText) findViewById(R.id.et_linkNews);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        // kode yang dipanggil ketika tombol Submit diklik
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(etJudul.getText().toString()) && !isEmpty(etKategori.getText().toString()) && !isEmpty(etKeterangan.getText().toString()) && !isEmpty(etLink.getText().toString()))
                    submitnews(new News(etJudul.getText().toString(), etKategori.getText().toString(), etKeterangan.getText().toString(), etLink.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.bt_submit), "Data news tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etJudul.getWindowToken(), 0);
            }
        });

    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateNews(News news) {
        /**
         * Baris kode yang digunakan untuk mengupdate data news
         * yang sudah dimasukkan di Firebase Realtime Database
         */
        database.child("news") //akses parent index, ibaratnya seperti nama tabel
                .child(news.getKey()) //select news berdasarkan key
                .setValue(news) //set value news yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        /**
                         * Baris kode yang akan dipanggil apabila proses update news sukses
                         */
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diupdate", Snackbar.LENGTH_LONG).setAction("Sip", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }

    private void submitnews(News news) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("news").push().setValue(news).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etJudul.setText("");
                etKategori.setText("");
                etKeterangan.setText("");
                etLink.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, FirebaseDBCreateActivity.class);
    }
}