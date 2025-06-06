// File: user.java
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class user extends MainModel implements SettingData {

    private static final String DB_URL = "jdbc:sqlite:database.db"; // File DB akan dibuat di root proyek
    private LocalDate tanggal;

    // Field untuk menyimpan data tabel yang di-refresh
    private Vector<Vector<Object>> dataTabel;
    private Vector<String> kolomTabel;
    private int id_user;

    public user(String id_data, String Deskripsi, String Tempat, boolean Status, int tahun, int bulan, int hari, int id_user) {
        super(id_data, Deskripsi, Tempat, Status);
        this.tanggal = LocalDate.of(tahun, bulan, hari);
        this.dataTabel = new Vector<>();
        this.kolomTabel = new Vector<>();
        this.id_user = id_user;
    }

    // Getter untuk tanggal dan data tabel
    public LocalDate getTanggal() { return tanggal; }
    public Vector<Vector<Object>> getDataVector() { return dataTabel; }
    public Vector<String> getColumnNamesVector() { return kolomTabel; }
    public int getId_user() { return id_user; } // implement ID user pake di nilai yang ada di login !!

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Koneksi database gagal: " + e.getMessage());
        }
        return conn;
    }

    @Override
    public void buat_tabel() {
        String sql = "CREATE TABLE IF NOT EXISTS data ("
                + " id_data TEXT PRIMARY KEY,"
                + " tanggal TEXT NOT NULL,"
                + " deskripsi TEXT,"
                + " tempat TEXT,"
                + " status TEXT NOT NULL"
                + ");";
        try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabel 'data' berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.err.println("Gagal membuat tabel: " + e.getMessage());
        }
    }

    @Override
    public void Tbh_data() {
        Save_data();
    }

    @Override
    public void updt_tabel() {
        System.out.println("Proses Refresh Data Tabel Dimulai...");
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID Data");
        columnNames.add("Tanggal");
        columnNames.add("Deskripsi");
        columnNames.add("Tempat");
        columnNames.add("Status");

        Vector<Vector<Object>> data = new Vector<>();
        String sql = "SELECT id_data, tanggal, deskripsi, tempat, status FROM data ORDER BY id_data";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("id_data"));
                row.add(rs.getString("tanggal"));
                row.add(rs.getString("deskripsi"));
                row.add(rs.getString("tempat"));
                row.add(rs.getString("status"));
                data.add(row);
            }
            this.dataTabel = data;
            this.kolomTabel = columnNames;
            System.out.println("Data berhasil dimuat ulang dari database.");

        } catch (SQLException e) {
            System.err.println("Gagal memuat ulang data: " + e.getMessage());
        }
    }

    @Override
    public void Save_data() {
        // Menggunakan INSERT OR REPLACE agar bisa menambah atau menimpa data
        String sql = "INSERT OR REPLACE INTO data (id_data, tanggal, deskripsi, tempat, status) VALUES(?,?,?,?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getId_data());
            pstmt.setString(2, getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(3, getDeskripsi());
            pstmt.setString(4, getTempat());
            pstmt.setString(5, getStatus() ? "Baik" : "Rusak");
            pstmt.executeUpdate();
            System.out.println("Data dengan ID: " + getId_data() + " berhasil disimpan.");
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    @Override
    public void Tmpl_data() {
        System.out.println("\n[AKSI] Tmpl_data() dipanggil. Mencetak data ke konsol...");
        // Query untuk mengambil semua data
        String sql = "SELECT id_data, tanggal, deskripsi, tempat, status FROM data ORDER BY id_data";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            System.out.println("\n--- MENAMPILKAN SEMUA DATA (DARI KONSOL) ---");
            boolean adaData = false;
            // Loop melalui hasil query dan cetak setiap baris
            while (rs.next()) {
                adaData = true;
                System.out.println("---------------------------------");
                System.out.println("ID        : " + rs.getString("id_data"));
                System.out.println("  Tanggal   : " + rs.getString("tanggal"));
                System.out.println("  Deskripsi : " + rs.getString("deskripsi"));
                System.out.println("  Tempat    : " + rs.getString("tempat"));
                System.out.println("  Status    : " + rs.getString("status"));
            }

            if (!adaData) {
                System.out.println("Tidak ada data di dalam database.");
            }
            System.out.println("---------------------------------");

        } catch (SQLException e) {
            System.err.println("Gagal menampilkan data ke konsol: " + e.getMessage());
            e.printStackTrace();
        }
    }
}