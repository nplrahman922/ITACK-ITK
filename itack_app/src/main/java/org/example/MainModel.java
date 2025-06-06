package org.example;

import java.time.format.DateTimeFormatter; // import fungsi tanggal

public abstract class MainModel {
    private final DateTimeFormatter tanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private boolean Status;
    private String Deskripsi;
    private String Tempat;
    private String id_data;

    public MainModel(String id_data, String Deskripsi, String Tempat, boolean Status ) {
        this.id_data = id_data;
        this.Deskripsi = Deskripsi;
        this.Tempat = Tempat;
        this.Status = Status;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setTempat(String tempat) {
        Tempat = tempat;
    }

    public String getTempat() {
        return Tempat;
    }

    public void setId_data(String id_data) {
        this.id_data = id_data;
    }

    public String getId_data() {
        return id_data;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public boolean getStatus() {
        return Status;
    }

    public abstract  void  Tbh_data ();

    public abstract void Tmpl_data ();

}
