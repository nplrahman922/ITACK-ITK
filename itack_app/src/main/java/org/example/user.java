package org.example;

import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

// Kelas ini sekarang strukturnya mirip dengan kelas admin yang sudah diperbaiki
public class user {
    private String Tanggal, Status, Deskripsi, Tempat;
    private int id_data;

    // Konstruktor yang cocok dengan panggilan dari GUI: (String, String, String, String, int)
    public user(String Tanggal, String Status, String Deskripsi, String Tempat, int id_data) {
        this.Tanggal = Tanggal;
        this.Status = Status;
        this.Deskripsi = Deskripsi;
        this.Tempat = Tempat;
        this.id_data = id_data;
    }

    // Metode untuk menyimpan data ke database
    public boolean simpanData() {
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "INSERT OR REPLACE INTO data (id_data, Tanggal, Status, Deskripsi, Tempat) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id_data);
            pstmt.setString(2, this.Tanggal);
            pstmt.setString(3, this.Status); // Menyimpan status sebagai String
            pstmt.setString(4, this.Deskripsi);
            pstmt.setString(5, this.Tempat);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("ERROR saat menyimpan data: " + e.getMessage());
            return false;
        }
    }

    // Metode statis untuk mendapatkan ID terakhir dari database
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

    // Metode statis untuk mengambil semua data dan menampilkannya di JTable
    public static DefaultTableModel getTableModel() {
        String[] columnNames = {"ID", "Tanggal", "Status", "Deskripsi", "Tempat"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
             @Override
            public boolean isCellEditable(int row, int column) {
               return false; // Membuat sel tabel tidak bisa diedit
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
                row.add(rs.getString("Status")); // Membaca status sebagai String
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