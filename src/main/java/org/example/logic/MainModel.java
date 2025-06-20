package org.example.logic;

import java.time.LocalDate;


//Kelas Abstrak MainModel
public abstract class MainModel {
    // Atribut dasar yang diwarisi oleh kelas turunan
    private int id_data;
    private LocalDate Tanggal;
    private boolean Status;
    private String Deskripsi;
    private String Tempat;

    //Konstruktor MainModel
    public MainModel(int id_data, LocalDate Tanggal, boolean Status, String Deskripsi, String Tempat) {
        this.id_data = id_data;
        this.Tanggal = Tanggal;
        this.Status = Status;
        this.Deskripsi = Deskripsi;
        this.Tempat = Tempat;
    }

    //Bagian Metode Abstrak
    //Metode Abstrak untuk Penambahan data
    public abstract void Tbh_data();
    
    //Metode Abstrak untuk menampilkan data
    public abstract void Tmpl_data();

    public int getId_data() {
        return id_data;
    }

    public void setId_data(int id_data) {
        this.id_data = id_data;
    }

    public LocalDate getTanggal() {
        return Tanggal;
    }
    
    public void setTanggal(LocalDate Tanggal) {
        this.Tanggal = Tanggal;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }
    
    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String Deskripsi) {
        this.Deskripsi = Deskripsi;
    }

    public String getTempat() {
        return Tempat;
    }

    public void setTempat(String Tempat) {
        this.Tempat = Tempat;
    }
    
    // Memberikan info status
    public String getStatusInfo() {
        return this.Status ? "Baik" : "Rusak";
    }
}
