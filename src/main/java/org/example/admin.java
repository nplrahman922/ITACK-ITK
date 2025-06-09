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

public class admin {
    // Fields
    private String Tanggal;
    private boolean Status;
    private String Deskripsi;
    private String Tempat;
    private int id_data;

    // Constructor
    public admin(String Tanggal, boolean Status, String Deskripsi, String Tempat, int id_data) {
        this.Tanggal = Tanggal;
        this.Status = Status;
        this.Deskripsi = Deskripsi;
        this.Tempat = Tempat;
        this.id_data = id_data;
    }

    // Getters
    public String getTanggal() { return Tanggal; }
    public boolean getStatus() { return Status; }
    public String getDeskripsi() { return Deskripsi; }
    public String getTempat() { return Tempat; }
    public int getIdData() { return id_data; }


    public void Tmp_data() {
        System.out.println("--------------------------------------");
        System.out.println("ID Data : " + getIdData());
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
        List<admin> daftarLaporan = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                daftarLaporan.add(new admin(
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

        System.out.println("\n--- TABEL SEMUA DATA LAPORAN ---");
        if (daftarLaporan.isEmpty()) {
            System.out.println("Tidak ada data untuk ditampilkan.");
            return;
        }

        System.out.println("+------------+------------+---------+-------------------------+--------------------+");
        System.out.println("| ID DATA    | TANGGAL    | STATUS  | DESKRIPSI               | TEMPAT             |");
        System.out.println("+------------+------------+---------+-------------------------+--------------------+");

        for (admin item : daftarLaporan) {
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
            System.out.println("--- Tambah Data Baru (sebagai Admin) ---");
            System.out.print("Masukkan ID Data (angka): ");
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

            admin dataBaru = new admin(tanggal, status, deskripsi, tempat, id_data);
            System.out.println("\nData yang akan disimpan:");
            dataBaru.Tmp_data();
            dataBaru.Save_data();

        } catch (NumberFormatException e) {
            System.err.println("❌ ERROR: ID harus berupa angka.");
        } catch (Exception e) {
            System.err.println("❌ ERROR: Terjadi kesalahan saat input data admin: " + e.getMessage());
        }
    }

    public static void edit_data() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Edit Laporan (Admin) ---");
        System.out.print("Masukkan ID Data yang akan diedit: ");
        int id_data;
        try {
            id_data = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("❌ ERROR: ID harus berupa angka.");
            return;
        }

        admin dataLama = get_laporan_by_id(id_data);
        if (dataLama == null) {
            System.err.println("❌ GAGAL: Data dengan ID " + id_data + " tidak ditemukan.");
            return;
        }

        try {
            System.out.println("Data ditemukan. Tekan ENTER untuk melewati dan menggunakan data lama.");
            System.out.print("Tanggal baru (" + dataLama.getTanggal() + "): ");
            String tanggalBaru = scanner.nextLine();
            if (tanggalBaru.isEmpty()) tanggalBaru = dataLama.getTanggal();

            System.out.print("Status baru (Bagus/Rusak) (" + (dataLama.getStatus() ? "Bagus" : "Rusak") + "): ");
            String statusInput = scanner.nextLine();
            boolean statusBaru = statusInput.isEmpty() ? dataLama.getStatus() : statusInput.equalsIgnoreCase("Bagus");

            System.out.print("Deskripsi baru (" + dataLama.getDeskripsi() + "): ");
            String deskripsiBaru = scanner.nextLine();
            if (deskripsiBaru.isEmpty()) deskripsiBaru = dataLama.getDeskripsi();

            System.out.print("Tempat baru (" + dataLama.getTempat() + "): ");
            String tempatBaru = scanner.nextLine();
            if (tempatBaru.isEmpty()) tempatBaru = dataLama.getTempat();

            String dbUrl = "jdbc:sqlite:Data.db";
            String updateSql = "UPDATE data SET Tanggal = ?, Status = ?, Deskripsi = ?, Tempat = ? WHERE id_data = ?";
            try (Connection conn = DriverManager.getConnection(dbUrl);
                 PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, tanggalBaru);
                pstmt.setBoolean(2, statusBaru);
                pstmt.setString(3, deskripsiBaru);
                pstmt.setString(4, tempatBaru);
                pstmt.setInt(5, id_data);
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("✅ SUKSES: Data dengan ID " + id_data + " telah diperbarui oleh Admin.");
                }
            } catch (SQLException e) {
                System.err.println("❌ ERROR saat memperbarui data: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("❌ ERROR: Terjadi kesalahan saat proses input data: " + e.getMessage());
        }
    }

    public static void hapus_data() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Hapus Data (Admin) ---");
        System.out.print("Masukkan ID Laporan yang akan dihapus: ");
        int id_data;
        try {
            id_data = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("❌ ERROR: ID harus berupa angka.");
            return;
        }

        if (!cekIdExists(id_data)) {
            System.err.println("❌ GAGAL: Data dengan ID " + id_data + " tidak ditemukan.");
            return;
        }

        System.out.print("Yakin ingin menghapus data dengan ID " + id_data + "? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            String dbUrl = "jdbc:sqlite:Data.db";
            String deleteSql = "DELETE FROM data WHERE id_data = ?";
            try (Connection conn = DriverManager.getConnection(dbUrl);
                 PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, id_data);
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("✅ SUKSES: Data dengan ID " + id_data + " telah dihapus.");
                }
            } catch (SQLException e) {
                System.err.println("❌ ERROR saat menghapus data: " + e.getMessage());
            }
        } else {
            System.out.println("Penghapusan dibatalkan.");
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

    private static admin get_laporan_by_id(int id_data) {
        String dbUrl = "jdbc:sqlite:Data.db";
        String sql = "SELECT * FROM data WHERE id_data = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_data);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new admin(
                            rs.getString("Tanggal"),
                            rs.getBoolean("Status"),
                            rs.getString("Deskripsi"),
                            rs.getString("Tempat"),
                            rs.getInt("id_data")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR saat mengambil laporan by ID: " + e.getMessage());
        }
        return null;
    }
}
