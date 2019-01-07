package com.example.lenovo.admintabungan;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FirebaseDBReadSingleAsisten extends AppCompatActivity {

    private Button btSubmit;
    private EditText etNama;
    private EditText etAlamat;
    private EditText etEmail;
    private EditText etNohp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten_create);
        etNama = (EditText) findViewById(R.id.et_namaAsisten);
        etAlamat = (EditText) findViewById(R.id.et_alamatAsisten);
        etEmail = (EditText) findViewById(R.id.et_emailAsisten);
        etNohp = (EditText) findViewById(R.id.et_nohpAsisten);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        etNama.setEnabled(false);
        etAlamat.setEnabled(false);
        etEmail.setEnabled(false);
        etNohp.setEnabled(false);
        btSubmit.setVisibility(View.GONE);

        Asisten asisten = (Asisten) getIntent().getSerializableExtra("data");
        if(asisten!=null){
            etNama.setText(asisten.getNama());
            etAlamat.setText(asisten.getAlamat());
            etEmail.setText(asisten.getEmail());
            etNohp.setText(asisten.getNohp());
        }
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, FirebaseDBReadSingleAsisten.class);
    }
}