package org.example.logic;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
// class admin berfungsi sebagai logic dalam admin
public class admin extends MainModel implements SettingData {

    // Atribut spesifik untuk kelas Admin sesuai UML
    private String id_admin;
    private String PWD;

    private String statusDetail;
    // konstruktor class
    public admin(String id_admin, String PWD, int id_data, LocalDate Tanggal, String status, String Deskripsi, String Tempat) {
        super(id_data, Tanggal, status.equalsIgnoreCase("Baik"), Deskripsi, Tempat);
        this.id_admin = id_admin;
        this.PWD = PWD;
        this.statusDetail = status;
    }

    // ===================================================================
    // Implementasi method dari settingdata
    // ===================================================================

    @Override
    public void Save_data() {
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "INSERT OR REPLACE INTO data (id_data, Tanggal, Status, Deskripsi, Tempat) VALUES(?,?,?,?,?)";

        String tanggalStr = getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // GUNAKAN statusDetail, BUKAN konversi dari boolean
        String statusStr = this.statusDetail;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getId_data());
            pstmt.setString(2, tanggalStr);
            pstmt.setString(3, statusStr); // Simpan status ke format string
            pstmt.setString(4, getDeskripsi());
            pstmt.setString(5, getTempat());
            pstmt.executeUpdate();
            System.out.println("Data dengan ID " + getId_data() + " berhasil disimpan.");
        } catch (SQLException e) {
            System.err.println("ERROR saat menyimpan data: " + e.getMessage());
        }
    }

    @Override
    public void buat_tabel() {
        // Mendelegasikan pembuatan tabel ke metode inisialisasi global.
        Login.initDatabase();
    }

    @Override
    public void updt_tabel() {
        // Dalam aplikasi ini, pembaruan tabel ditangani oleh GUI.
        System.out.println("Pembaruan tabel ditangani oleh GUI dengan memanggil getTableModel().");
    }

    // ===================================================================
    // Implementasi method abstract dari mainmodel
    // ===================================================================

    @Override
    public void Tbh_data() {
        // logic dari method ini berada di save_data()
        Save_data();
    }

    @Override
    public void Tmpl_data() {
        // Menampilkan data untuk satu objek spesifik.
        System.out.println("Menampilkan data untuk objek Admin:");
        System.out.println("ID Admin: " + this.id_admin);
        System.out.println("ID Laporan: " + getId_data());
        System.out.println("Tanggal: " + getTanggal().toString());
        System.out.println("Status: " + getStatusInfo());
        System.out.println("Tempat: " + getTempat());
        System.out.println("Deskripsi: " + getDeskripsi());
    }


    // ===================================================================
    // GETTER DAN SETTER SESUAI UML
    // ===================================================================

    public void setID_Admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public String getPWD() {
        return this.PWD;
    }

    @Override
    public boolean getStatus() {
        return super.getStatus();
    }

    @Override
    public LocalDate getTanggal() {
        return super.getTanggal();
    }

    @Override
    public String getStatusInfo() {
        // Memberikan representasi String dari status boolean
        return getStatus() ? "Baik" : "Rusak";
    }

    @Override
    public String getTempat() {
        return super.getTempat();
    }

    // Metode getter dan setter lainnya dari MainModel diwarisi secara otomatis
    // (getId_data, setId_data, getDeskripsi, setDeskripsi, dll.)

    public static boolean hapusData(int id_data) {
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "DELETE FROM data WHERE id_data = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_data);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ERROR saat menghapus data: " + e.getMessage());
            return false;
        }
    }

    public static int getLatestId() {
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "SELECT MAX(id_data) FROM data";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ERROR saat mencari ID terbaru: " + e.getMessage());
        }
        return 0;
    }

    public static DefaultTableModel getTableModel() {
        // 1. Tambahkan kolom "No." untuk ditampilkan dan "ID" untuk internal
        String[] columnNames = {"ID", "No.", "Tanggal", "Status", "Deskripsi", "Tempat"};

        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
            // Membuat sel tidak bisa diedit
            return false;
            }
        };

        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "SELECT * FROM data ORDER BY id_data";

        // 2. Buat variabel counter untuk nomor urut
        int rowNumber = 1;

        try (Connection conn = DriverManager.getConnection(dbUrl);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id_data")); // Kolom 0: ID asli (akan disembunyikan)
                row.add(rowNumber);                  // Kolom 1: Nomor urut yang rapi

                // Tambahkan sisa data
                row.add(rs.getString("Tanggal"));
                row.add(rs.getString("Status"));
                row.add(rs.getString("Deskripsi"));
                row.add(rs.getString("Tempat"));
                model.addRow(row);

                // 4. Naikkan counter
                rowNumber++;
            }
        } catch (SQLException e) {
            System.err.println("ERROR saat mengambil data untuk tabel: " + e.getMessage());
        }
        return model;
    }
}