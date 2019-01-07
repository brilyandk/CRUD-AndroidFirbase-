package com.example.lenovo.admintabungan;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Asisten implements Serializable {

    private String nama;
    private String alamat;
    private String email;
    private String nohp;
    private String key;

    public Asisten() {
    }

    public Asisten(String nama, String alamat, String email, String nohp) {
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.nohp = nohp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return " "+nama+"\n" +
                " "+alamat +"\n" +
                " "+email +"\n" +
                " "+nohp;
    }
}