package com.example.lenovo.admintabungan;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;


@IgnoreExtraProperties
public class News implements Serializable {

    private String judul;
    private String kategori;
    private String keterangan;
    private String link;
    private String key;

    public News() {

    }


    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String toString() {
        return " "+judul+"\n" +
                " "+kategori +"\n" +
                " "+keterangan +"\n" +
                " "+link;

    }

    public News(String jd, String ktg, String ktr, String lnk){
        judul = jd;
        kategori = ktg;
        keterangan = ktr;
        link = lnk;

    }
}