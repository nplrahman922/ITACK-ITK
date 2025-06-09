package org.example;
import java.util.Date;


public abstract class MainModel {
    private Date Tanggal;
    private boolean Status;
    private String Deskripsi;
    private String Tempat;
    private int id_data;

    public MainModel(Date Tanggal, boolean Status, String Deskripsi, String Tempat, int id_data) {}

    public MainModel(String tanggal, boolean status, String deskripsi, String tempat, int idData) {
    }

    public abstract void Tbl_data();
    public abstract void Tmp_data();

    public Date getTanggal() { return null; }
    public String getDeskripsi() { return null; }
    public String getStatus() { return null; }
    public String getTempat() { return null; }
    public int getId_data() { return 0; }

    public void setStatus(boolean Status) {}
}
