package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kelas Abstrak MainModel sesuai dengan desain UML.
 * Menjadi blueprint dasar untuk kelas turunan seperti Admin dan User.
 */
public abstract class MainModel {
    // Atribut dasar yang diwarisi oleh kelas turunan
    private int id_data;
    private LocalDate Tanggal;
    private boolean Status; // true = Baik, false = Rusak/Perbaikan
    private String Deskripsi;
    private String Tempat;

    /**
     * Konstruktor untuk MainModel.
     * Akan dipanggil oleh konstruktor dari kelas anak (admin, user).
     */
    public MainModel(int id_data, LocalDate Tanggal, boolean Status, String Deskripsi, String Tempat) {
        this.id_data = id_data;
        this.Tanggal = Tanggal;
        this.Status = Status;
        this.Deskripsi = Deskripsi;
        this.Tempat = Tempat;
    }

    // --- METODE ABSTRAK (WAJIB DIIMPLEMENTASIKAN OLEH KELAS ANAK) ---
    
    /**
     * Metode abstrak untuk logika penambahan data.
     */
    public abstract void Tbh_data();

    /**
     * Metode abstrak untuk logika penampilan data.
     */
    public abstract void Tmpl_data();

    // --- GETTER DAN SETTER SESUAI UML ---

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
    
    /**
     * Memberikan informasi status dalam bentuk String.
     * @return String representasi dari status.
     */
    public String getStatusInfo() {
        return this.Status ? "Baik" : "Rusak";
    }
}