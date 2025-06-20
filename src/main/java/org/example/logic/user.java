package org.example.logic;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class user extends MainModel implements SettingData {

    public user(int id_data, LocalDate Tanggal, String status, String Deskripsi, String Tempat) {
        // Menggunakan super() untuk meneruskan data ke konstruktor MainModel.
        super(id_data, Tanggal, status.equalsIgnoreCase("Baik"), Deskripsi, Tempat);
    }

    // --- Implementasi Metode dari MainModel (Abstrak) ---

    @Override
    public void Tbh_data() {
        // Mendelegasikan tugas penambahan data ke metode Save_data() dari interface.
        Save_data();
    }

    @Override
    public void Tmpl_data() {
        // Menampilkan detail data dari objek saat ini ke konsol.
        System.out.println("Menampilkan data untuk objek Laporan User:");
        System.out.println("ID Laporan: " + getId_data());
        System.out.println("Tanggal: " + getTanggal().toString());
        System.out.println("Status: " + getStatusInfo());
        System.out.println("Tempat: " + getTempat());
        System.out.println("Deskripsi: " + getDeskripsi());
    }

    // --- Implementasi Metode dari SettingData (Interface) ---

    @Override
    public void buat_tabel() {
        // Mendelegasikan pembuatan tabel ke metode inisialisasi database global
        Login.initDatabase();
    }

    @Override
    public void updt_tabel() {
        // Dalam aplikasi ini, pembaruan tabel ditangani oleh GUI
        System.out.println("Pembaruan tabel ditangani oleh GUI dengan memanggil getTableModel().");
    }

    @Override
    public void Save_data() {
        String dbUrl = "jdbc:sqlite:Data.db"; //
        String sql = "INSERT OR REPLACE INTO data (id_data, Tanggal, Status, Deskripsi, Tempat) VALUES(?,?,?,?,?)";

        String tanggalStr = getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String statusStr = getStatusInfo(); // Mendapatkan "Baik" atau "Rusak"

        try (Connection conn = DriverManager.getConnection(dbUrl); //
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, getId_data());
            pstmt.setString(2, tanggalStr);
            pstmt.setString(3, statusStr);
            pstmt.setString(4, getDeskripsi());
            pstmt.setString(5, getTempat());
            pstmt.executeUpdate();
            System.out.println("Data laporan dengan ID " + getId_data() + " berhasil disimpan.");

        } catch (SQLException e) {
            System.err.println("ERROR saat menyimpan data laporan: " + e.getMessage());
        }
    }


    // --- Metode Statis (Utility) untuk GUI ---

    public static int getLatestId() {
        String dbUrl = "jdbc:sqlite:Data.db"; //
        String sql = "SELECT MAX(id_data) FROM data";
        try (Connection conn = DriverManager.getConnection(dbUrl); //
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
        String[] columnNames = {"ID", "No.", "Tanggal", "Status", "Deskripsi", "Tempat"};

        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String dbUrl = "jdbc:sqlite:Data.db"; //
        String sql = "SELECT * FROM data ORDER BY id_data";

        int rowNumber = 1;

        try (Connection conn = DriverManager.getConnection(dbUrl); //
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id_data"));
                row.add(rowNumber);
                row.add(rs.getString("Tanggal"));
                row.add(rs.getString("Status"));
                row.add(rs.getString("Deskripsi"));
                row.add(rs.getString("Tempat"));
                model.addRow(row);
                rowNumber++;
            }
        } catch (SQLException e) {
            System.err.println("ERROR saat mengambil data untuk tabel: " + e.getMessage());
        }
        return model;
    }
}