package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// hanya class login gk pake class public void main
public class login {
    private String id;
    private String password;
    private String pengguna;
    private static final String DB_URL = "jdbc:sqlite:database.db";

    public login(String id, String password, String pengguna) {
        this.id = id;
        this.password = password;
        this.pengguna = pengguna;
    }

    // tambahahan setter buat ambil data dari GUI :\
    void setid(String id) { //alasan pake string karena admin type nya string
        this.id = id;
    }

    void setpassword(String password) {
        this.password = password;
    }

    void setpengguna(String pengguna) {
        this.pengguna = pengguna;
    }

    // method getter untuk mengambil data private
    String getpassword() {
        return password;
    }

    String getpengguna() {
        return pengguna;
    }

    String getid() {
        return id;
    }

    void validlogin() {
        String idUser = getid();
        String passwordUser = getpassword();
        String penggunaInput = getpengguna();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null; // Ini adalah rs lingkup-method, akan digunakan oleh blok "admin"

        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Koneksi ke SQLite berhasil dibuat.");

            if ("admin".equals(penggunaInput)) {
                // Validasi Admin
                // PENTING: Query ini mengambil SEMUA admin, Anda perlu WHERE clause untuk id dan password
                // String sql = "SELECT id_admin, password FROM admin WHERE id_admin = ? AND password = ?";
                // Untuk contoh ini, kita tetap pada query asli Anda, tapi ini bukan validasi login yang aman/benar.
                String sql = "SELECT id_admin, password FROM admin"; // Asumsikan ada tabel 'admin'

                stmt = conn.createStatement(); // Sebaiknya gunakan PreparedStatement juga untuk admin
                rs = stmt.executeQuery(sql);   // 'rs' lingkup-method diisi di sini

                List<String> daftarAdminInfo = new ArrayList<>();
                boolean adminValid = false;

                while (rs.next()) {
                    String id_admin_db = rs.getString("id_admin"); // Pastikan tipe data cocok
                    String password_db = rs.getString("password");

                    if (id_admin_db.equals(idUser) && password_db.equals(passwordUser)) {
                        adminValid = true;
                        System.out.println("Login admin berhasil untuk ID: " + id_admin_db);
                        // TODO: Lakukan aksi setelah login admin berhasil
                        break; // Keluar dari loop jika admin valid ditemukan
                    }
                    // Kode untuk daftarAdminInfo bisa dipertahankan jika diperlukan untuk logging
                    String infoAdmin = id_admin_db + " " + password_db;
                    daftarAdminInfo.add(infoAdmin);
                }

                if (!adminValid) {
                    System.out.println("Login admin gagal atau data tidak ditemukan.");
                }

                // for (String info : daftarAdminInfo) {
                //     System.out.println("Debug Info Admin dari DB: " + info);
                // }

            } else if ("user".equals(penggunaInput)) {
                System.out.println("Memproses login untuk tipe: user");

                int targetIdUser;
                try {
                    targetIdUser = Integer.parseInt(idUser.trim());
                } catch (NumberFormatException e) {
                    System.err.println("Format ID User tidak valid: " + idUser);
                    if (conn != null) try {
                        conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    return;
                }

                String sqlCheckUser = "SELECT id_user FROM \"user\" WHERE id_user = ?";
                String sqlInsertUser = "INSERT INTO \"user\" (id_user) VALUES (?)"; // Asumsikan tabel bernama "user"
                boolean userDitemukanDiDB = false;

                // 'rs' di dalam try-with-resources berikut adalah LOKAL dan berbeda dari 'rs' lingkup-method.
                // Ini adalah praktik yang baik.
                try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckUser)) {
                    pstmtCheck.setInt(1, targetIdUser);

                    try (ResultSet localRs = pstmtCheck.executeQuery()) { // Menggunakan nama berbeda 'localRs' untuk kejelasan,
                        // atau tetap 'rs' juga valid karena shadowing.
                        if (localRs.next()) {
                            userDitemukanDiDB = true;
                            int id_user_db = localRs.getInt("id_user");
                            System.out.println("Login user berhasil untuk ID: " + id_user_db);
                            // TODO: Lakukan aksi setelah login user berhasil
                        }
                    } // localRs (atau rs lokal) otomatis ditutup di sini

                } catch (SQLException e) {
                    System.err.println("Error saat memeriksa data user: " + e.getMessage());
                    e.printStackTrace();
                    if (conn != null) try {
                        conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    return;
                } // pstmtCheck otomatis ditutup di sini

                if (!userDitemukanDiDB) {
                    System.out.println("User dengan ID " + targetIdUser + " tidak ditemukan. Mencoba membuat user baru...");
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertUser)) {
                        pstmtInsert.setInt(1, targetIdUser);
                        int rowsAffected = pstmtInsert.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("User dengan ID " + targetIdUser + " berhasil dibuat.");
                            // TODO: Lakukan aksi setelah user baru berhasil dibuat
                        } else {
                            System.err.println("Gagal membuat user dengan ID " + targetIdUser + " (tidak ada baris yang terpengaruh).");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error saat membuat user baru: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                System.out.println("Proses untuk user dengan ID " + targetIdUser + " selesai.");

            } else {
                System.out.println("Tipe pengguna tidak dikenal: " + penggunaInput);
            }

        } catch (SQLException e) {
            System.err.println("Operasi database atau koneksi gagal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Tutup resources dalam urutan terbalik dari pembukaannya
            // 'rs' di sini merujuk pada 'rs' lingkup-method (yang digunakan blok admin)
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
