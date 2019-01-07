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




public class FirebaseDBCreateAsisten extends AppCompatActivity {

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    // variable fields EditText dan Buttonl;p
    private Button btSubmit;
    private EditText etNama;
    private EditText etAlamat;
    private EditText etEmail;
    private EditText etNohp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten_create);

        // inisialisasi fields EditText dan Button
        etNama = (EditText) findViewById(R.id.et_namaAsisten);
        etAlamat = (EditText) findViewById(R.id.et_alamatAsisten);
        etEmail = (EditText) findViewById(R.id.et_emailAsisten);
        etNohp = (EditText) findViewById(R.id.et_nohpAsisten);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        // kode yang dipanggil ketika tombol Submit diklik
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(etNama.getText().toString()) && !isEmpty(etAlamat.getText().toString()) && !isEmpty(etEmail.getText().toString()) && !isEmpty(etNohp.getText().toString()))
                    submitasisten(new Asisten(etNama.getText().toString(), etAlamat.getText().toString(), etEmail.getText().toString(), etNohp.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.bt_submit), "Data asisten tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etNama.getWindowToken(), 0);
            }
        });

    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateAsisten(Asisten asisten) {
        /**
         * Baris kode yang digunakan untuk mengupdate data asisten
         * yang sudah dimasukkan di Firebase Realtime Database
         */
        database.child("asisten") //akses parent index, ibaratnya seperti nama tabel
                .child(asisten.getKey()) //select asisten berdasarkan key
                .setValue(asisten) //set value asisten yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        /**
                         * Baris kode yang akan dipanggil apabila proses update asisten sukses
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

    private void submitasisten(Asisten asisten) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("asisten").push().setValue(asisten).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNama.setText("");
                etAlamat.setText("");
                etEmail.setText("");
                etNohp.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, FirebaseDBCreateAsisten.class);
    }
}