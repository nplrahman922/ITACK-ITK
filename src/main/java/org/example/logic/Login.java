package org.example.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Kelas Login yang disesuaikan agar ketat sesuai dengan desain UML.
 */
public class Login {
    // --- PERUBAHAN ATRIBUT ---
    private String id_login;
    private String Password;

    /**
     * Konstruktor untuk membuat objek Login.
     * @param id_login ID pengguna atau admin.
     * @param password Kata sandi.
     */
    // --- PERUBAHAN KONSTRUKTOR ---
    // Parameter id_login diubah menjadi String.
    public Login(String id_login, String password) {
        this.id_login = id_login;
        this.Password = password;
    }

    // ===================================================================
    // METODE GETTER DAN SETTER
    // ===================================================================


    public void getPassword() {
        // Logika untuk metode ini bisa ditambahkan jika diperlukan,
        // namun berdasarkan UML, metode ini tidak mengembalikan nilai.
        System.out.println("Metode getPassword() dipanggil.");
    }

    public void setPass(String password) {
        this.Password = password;
    }

    public void getPengguna() {
        System.out.println("Metode getPengguna() dipanggil. ID: " + this.id_login);
    }

    public void setPengguna(String id_log, String password) {
        this.id_login = id_log;
        this.Password = password;
    }


    // ===================================================================
    // METODE VERIFIKASI
    // ===================================================================

    /**
     * Metode verifikasi login sesuai dengan yang ada di UML.
     */
    public void verif_login() {
        System.out.println("Metode verifikasi umum dipanggil.");
    }

    public static boolean verif_login_admin(String adminId, String adminPass) {
        String sql = "SELECT * FROM admin WHERE id_admin = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            pstmt.setString(2, adminPass);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error validasi admin: " + e.getMessage());
            return false;
        }
    }

    public static boolean verif_login_user(int userId) {
        String checkSql = "SELECT id_user FROM user WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.db");
             PreparedStatement pstmtCheck = conn.prepareStatement(checkSql)) {
            pstmtCheck.setInt(1, userId);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                    String insertSql = "INSERT INTO user (id_user) VALUES (?)";
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(insertSql)) {
                        pstmtInsert.setInt(1, userId);
                        pstmtInsert.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Operasi database user gagal: " + e.getMessage());
            return false;
        }
    }

    public static void initDatabase() {
        String DB_URL = "jdbc:sqlite:Data.db";
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS data (id_data INTEGER PRIMARY KEY, Tanggal TEXT NOT NULL, Status TEXT NOT NULL, Deskripsi TEXT NOT NULL, Tempat TEXT NOT NULL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS admin (id_admin TEXT PRIMARY KEY, password TEXT NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS user (id_user INTEGER PRIMARY KEY)");

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM admin WHERE id_admin = 'admin'")) {
                if (rs.next() && rs.getInt("total") == 0) {
                    stmt.execute("INSERT INTO admin (id_admin, password) VALUES ('admin', 'admin123')");
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal inisialisasi database: " + e.getMessage());
        }
    }
}