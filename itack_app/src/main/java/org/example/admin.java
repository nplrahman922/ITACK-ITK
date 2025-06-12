package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Kelas Admin yang sepenuhnya sesuai dengan desain UML.
 * Kelas ini mewarisi MainModel dan mengimplementasikan SettingData.
 * Ini merepresentasikan seorang admin dan juga data laporan yang sedang dikelola.
 */
public class admin extends MainModel implements SettingData {

    // Atribut spesifik untuk kelas Admin sesuai UML
    private String id_admin;
    private String PWD;

    /**
     * Konstruktor untuk kelas Admin.
     * Menginisialisasi atribut dari kelas Admin dan kelas parent (MainModel).
     */
    public admin(String id_admin, String PWD, int id_data, LocalDate Tanggal, boolean Status, String Deskripsi, String Tempat) {
        // Memanggil konstruktor dari parent class (MainModel)
        super(id_data, Tanggal, Status, Deskripsi, Tempat);
        this.id_admin = id_admin;
        this.PWD = PWD;
    }

    // ===================================================================
    // IMPLEMENTASI METODE DARI INTERFACE SettingData
    // ===================================================================

    @Override
    public void Save_data() {
        // Logika untuk menyimpan data objek ini ke database.
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "INSERT OR REPLACE INTO data (id_data, Tanggal, Status, Deskripsi, Tempat) VALUES(?,?,?,?,?)";
        
        // Konversi tipe data agar sesuai dengan skema database
        String tanggalStr = getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String statusStr = getStatus() ? "Baik" : "Rusak"; // true = Baik, false = Rusak

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getId_data());
            pstmt.setString(2, tanggalStr);
            pstmt.setString(3, statusStr);
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
    // IMPLEMENTASI METODE ABSTRAK DARI MainModel
    // ===================================================================

    @Override
    public void Tbh_data() {
        // Logika untuk "Tambah Data" seharusnya memanggil Save_data()
        // karena keduanya bertujuan untuk menyimpan data ke DB.
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

    // Metode getter dari MainModel sudah di-override di sini untuk kejelasan
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

    // ===================================================================
    // METODE STATIS (HELPER) - TIDAK TERMASUK DALAM UML UNTUK INSTANCE
    // Metode ini tetap dipertahankan untuk mendukung fungsionalitas GUI
    // ===================================================================
    
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
        String[] columnNames = {"ID", "Tanggal", "Status", "Deskripsi", "Tempat"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
             @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };

        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "SELECT * FROM data ORDER BY id_data";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id_data"));
                row.add(rs.getString("Tanggal"));
                row.add(rs.getString("Status")); 
                row.add(rs.getString("Deskripsi"));
                row.add(rs.getString("Tempat"));
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println("ERROR saat mengambil data untuk tabel: " + e.getMessage());
        }
        return model;
    }
}