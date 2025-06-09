package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    // Fields, Constructor, dan Getters
    private String Tanggal;
    private boolean Status;
    private String Deskripsi;
    private String Tempat;
    private int id_data;

    public User(String Tanggal, boolean Status, String Deskripsi, String Tempat, int id_data) {
        this.Tanggal = Tanggal;
        this.Status = Status;
        this.Deskripsi = Deskripsi;
        this.Tempat = Tempat;
        this.id_data = id_data;
    }

    public String getTanggal() { return Tanggal; }
    public boolean getStatus() { return Status; }
    public String getDeskripsi() { return Deskripsi; }
    public String getTempat() { return Tempat; }
    public int getIdData() { return id_data; }

    public void Tmp_data() {
        System.out.println("--------------------------------------");
        System.out.println("ID Laporan : " + getIdData());
        System.out.println("Tanggal    : " + getTanggal());
        System.out.println("Status     : " + (getStatus() ? "Bagus" : "Rusak"));
        System.out.println("Deskripsi  : " + getDeskripsi());
        System.out.println("Tempat     : " + getTempat());
        System.out.println("--------------------------------------");
    }

    public void Save_data() {
        String dbName = "Data.db";
        String tableName = "data";
        String dbUrl = "jdbc:sqlite:" + dbName;
        String insertDataSql = "INSERT INTO " + tableName + "(id_data, Tanggal, Status, Deskripsi, Tempat) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(insertDataSql)) {

            pstmt.setInt(1, getIdData());
            pstmt.setString(2, getTanggal());
            pstmt.setBoolean(3, getStatus());
            pstmt.setString(4, getDeskripsi());
            pstmt.setString(5, getTempat());
            pstmt.executeUpdate();
            System.out.println("✅ SUKSES: Laporan dengan ID " + getIdData() + " telah disimpan.");

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("❌ GAGAL: Laporan dengan ID " + getIdData() + " sudah ada di database.");
            } else {
                System.err.println("❌ ERROR: Terjadi masalah saat menyimpan laporan: " + e.getMessage());
            }
        }
    }

    // --- METHOD STATIC ---

    public static void buat_tabel() {
        String dbName = "Data.db";
        String tableName = "data";
        String dbUrl = "jdbc:sqlite:" + dbName;
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id_data INTEGER PRIMARY KEY,"
                + "Tanggal TEXT NOT NULL,"
                + "Status BOOLEAN NOT NULL,"
                + "Deskripsi TEXT NOT NULL,"
                + "Tempat TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.err.println("❌ ERROR: Gagal membuat atau menyiapkan tabel: " + e.getMessage());
        }
    }

    public static void tampilkan_semua_data() {
        String dbUrl = "jdbc:sqlite:Data.db";
        String tableName = "data";
        String sql = "SELECT * FROM " + tableName;
        List<User> daftarLaporan = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                daftarLaporan.add(new User(
                        rs.getString("Tanggal"),
                        rs.getBoolean("Status"),
                        rs.getString("Deskripsi"),
                        rs.getString("Tempat"),
                        rs.getInt("id_data")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR saat mengambil data: " + e.getMessage());
            return;
        }

        System.out.println("\n--- TABEL SEMUA LAPORAN INVENTARIS ---");
        if (daftarLaporan.isEmpty()) {
            System.out.println("Tidak ada data untuk ditampilkan.");
            return;
        }

        System.out.println("+------------+------------+---------+-------------------------+--------------------+");
        System.out.println("| ID LAPORAN | TANGGAL    | STATUS  | DESKRIPSI               | TEMPAT             |");
        System.out.println("+------------+------------+---------+-------------------------+--------------------+");

        for (User item : daftarLaporan) {
            String statusStr = item.getStatus() ? "Bagus" : "Rusak";
            System.out.printf("| %-10d | %-10s | %-7s | %-23s | %-18s |\n",
                    item.getIdData(), item.getTanggal(), statusStr, item.getDeskripsi(), item.getTempat());
        }
        System.out.println("+------------+------------+---------+-------------------------+--------------------+");
    }

    /**
     * PERUBAHAN UTAMA DI SINI
     * Menambahkan data baru. Jika ID yang dimasukkan sudah ada,
     * ID baru akan dibuat secara otomatis.
     */
    public static void Tbh_data() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("--- Tambah Data Laporan Baru ---");
            System.out.print("Masukkan ID Laporan (angka): ");
            int id_data = Integer.parseInt(scanner.nextLine());

            // Cek jika ID yang dimasukkan sudah ada
            if (cekIdExists(id_data)) {
                // Jika ID sudah ada, cari ID baru secara otomatis
                int idBaru = get_id_terbaru() + 1;
                System.out.println("⚠️  PERINGATAN: ID " + id_data + " sudah digunakan.");
                System.out.println("✅ INFO: ID baru " + idBaru + " akan digunakan secara otomatis.");
                id_data = idBaru; // Ganti id_data dengan yang baru ditemukan
            }

            System.out.print("Masukkan Tanggal (YYYY-MM-DD): ");
            String tanggal = scanner.nextLine();
            System.out.print("Masukkan Status (Bagus / Rusak): ");
            boolean status = scanner.nextLine().equalsIgnoreCase("Bagus");
            System.out.print("Masukkan Deskripsi: ");
            String deskripsi = scanner.nextLine();
            System.out.print("Masukkan Tempat: ");
            String tempat = scanner.nextLine();

            User dataBaru = new User(tanggal, status, deskripsi, tempat, id_data);
            System.out.println("\nData yang akan disimpan:");
            dataBaru.Tmp_data();
            dataBaru.Save_data();

        } catch (NumberFormatException e) {
            System.err.println("❌ ERROR: ID harus berupa angka.");
        } catch (Exception e) {
            System.err.println("❌ ERROR: Terjadi kesalahan saat input data: " + e.getMessage());
        }
    }

    // --- Helper Methods ---

    private static boolean cekIdExists(int id_data) {
        String dbUrl = "jdbc:sqlite:Data.db";
        String checkIdSql = "SELECT 1 FROM data WHERE id_data = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(checkIdSql)) {
            pstmt.setInt(1, id_data);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * METODE HELPER BARU
     * Mengambil ID tertinggi dari database.
     * @return ID tertinggi yang ada, atau 0 jika tabel kosong.
     */
    private static int get_id_terbaru() {
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "SELECT MAX(id_data) FROM data";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                // Mengambil nilai MAX dari kolom pertama
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR saat mencari ID terbaru: " + e.getMessage());
        }
        // Mengembalikan 0 jika tabel kosong atau terjadi error
        return 0;
    }
}
