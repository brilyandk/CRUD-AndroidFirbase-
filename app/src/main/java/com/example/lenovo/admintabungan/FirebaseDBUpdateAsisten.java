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


public class FirebaseDBUpdateAsisten extends AppCompatActivity {

    //variable untuk refers ke Firebase Realtime Database
    private DatabaseReference database;

    //variable komponen dalam layout
    private Button update;

    private EditText etnama;
    private EditText etalamat;
    private EditText etemail;
    private EditText etnohp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten_update);

        //inisiasi komponen dari layout
        update = (Button) findViewById(R.id.update);
        etnama = (EditText) findViewById(R.id.et_namaAsisten);
        etalamat = (EditText) findViewById(R.id.et_alamatAsisten);
        etemail = (EditText) findViewById(R.id.et_emailAsisten);
        etnohp = (EditText) findViewById(R.id.et_nohpAsisten);

        //mengambil reference database ke Firebase
        database = FirebaseDatabase.getInstance().getReference();

        final Asisten dataModel = (Asisten) getIntent().getSerializableExtra("data");

        if (dataModel != null) {
            etnama.setText(dataModel.getNama());
            etalamat.setText(dataModel.getAlamat());
            etemail.setText(dataModel.getEmail());
            etnohp.setText(dataModel.getNohp());

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataModel.setNama(etnama.getText().toString());
                    dataModel.setAlamat(etalamat.getText().toString());
                    dataModel.setEmail(etemail.getText().toString());
                    dataModel.setNohp(etnohp.getText().toString());
                }
            });
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = dataModel.getKey();
                String nama = etnama.getText().toString().trim();
                String alamat = etalamat.getText().toString().trim();
                String email = etemail.getText().toString().trim();
                String nohp = etnohp.getText().toString().trim();

                updateDataModel(key, nama, alamat, email, nohp);

            }
        });
    }

    private boolean updateDataModel(String key, String nama, String alamat, String email, String nohp)
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("asisten").child(key);

        Asisten dataModel = new Asisten(nama, alamat, email, nohp);

        databaseReference.setValue(dataModel);

        Toast.makeText(this, "Data Berhasil diubah", Toast.LENGTH_LONG).show();

        return true;
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, FirebaseDBUpdateAsisten.class);
    }
}