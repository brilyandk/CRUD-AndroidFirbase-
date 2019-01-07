package com.example.lenovo.admintabungan;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FirebaseDBReadSingleActivity extends AppCompatActivity {

    private Button btSubmit;
    private EditText etJudul;
    private EditText etKategori;
    private EditText etKeterangan;
    private EditText etLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_create);
        etJudul = (EditText) findViewById(R.id.et_judulNews);
        etKategori = (EditText) findViewById(R.id.et_kategoriNews);
        etKeterangan = (EditText) findViewById(R.id.et_keterangan);
        etLink = (EditText) findViewById(R.id.et_linkNews);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        etJudul.setEnabled(false);
        etKategori.setEnabled(false);
        etKeterangan.setEnabled(false);
        etLink.setMovementMethod(LinkMovementMethod.getInstance());
        btSubmit.setVisibility(View.GONE);

        News news = (News) getIntent().getSerializableExtra("data");
        if(news!=null){
            etJudul.setText(news.getJudul());
            etKategori.setText(news.getKategori());
            etKeterangan.setText(news.getKeterangan());
            etLink.setText(news.getLink());
        }
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, FirebaseDBReadSingleActivity.class);
    }
}