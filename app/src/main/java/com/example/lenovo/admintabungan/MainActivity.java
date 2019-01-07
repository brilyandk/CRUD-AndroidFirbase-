package com.example.lenovo.admintabungan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;




public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView tambahBerita,lihatBerita,tambahAsisten,lihatAsisten,lihatMaps,buatDiskon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambahBerita 	= (CardView) findViewById(R.id.bt_createdata);
        lihatBerita 	= (CardView) findViewById(R.id.bt_viewdata);
        tambahAsisten	= (CardView) findViewById(R.id.bt_createasisten);
        lihatAsisten 	= (CardView) findViewById(R.id.bt_viewasisten);
        buatDiskon 	    = (CardView) findViewById(R.id.bt_diskon);


        //buat listener

        tambahBerita.setOnClickListener(this);
        lihatBerita.setOnClickListener(this);
        tambahAsisten.setOnClickListener(this);
        lihatAsisten.setOnClickListener(this);
        buatDiskon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent i;

        switch (v.getId()) {
            case R.id.bt_createdata : i = new Intent(MainActivity.this, FirebaseDBCreateActivity.class); startActivity(i); break ;
            case R.id.bt_viewdata : i = new Intent(MainActivity.this, FirebaseDBReadActivity.class); startActivity(i); break ;
            case R.id.bt_createasisten : i = new Intent(MainActivity.this, FirebaseDBCreateAsisten.class); startActivity(i); break ;
            case R.id.bt_viewasisten : i = new Intent(MainActivity.this, FirebaseDBReadAsisten.class); startActivity(i); break ;
            case R.id.bt_diskon : i = new Intent(MainActivity.this, CapturePictureActivity.class); startActivity(i); break ;

            default:break;

        }
    }

}