package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Kelas Login yang secara ketat sesuai dengan desain UML untuk tujuan akademis.
 * Kelas ini mencakup semua atribut dan metode yang ditentukan dalam diagram.
 */
public class Login {
    // Atribut sesuai UML
    private int id_login;
    private String Password;

    /**
     * Konstruktor untuk membuat objek Login.
     * @param id_login ID pengguna atau admin.
     * @param password Kata sandi (terutama untuk admin).
     */
    public Login(int id_login, String password) {
        this.id_login = id_login;
        this.Password = password;
    }

    // ===================================================================
    // METODE GETTER DAN SETTER SESUAI UML
    // ===================================================================

    public String getPass() {
        return this.Password;
    }

    public void setPass(String password) {
        this.Password = password;
    }

    public int getPengguna() { // Menggunakan 'getPengguna' sesuai UML untuk mendapatkan ID
        return this.id_login;
    }

    public void setPengguna(int id_login) { // Menggunakan 'setPengguna' sesuai UML untuk mengatur ID
        this.id_login = id_login;
    }


    // ===================================================================
    // METODE VERIFIKASI SESUAI UML
    // ===================================================================

    /**
     * Metode verifikasi login sesuai dengan yang ada di UML.
     * Dalam implementasi praktis, metode yang lebih spesifik di bawah ini lebih berguna.
     */
    public void verif_login() {
        // Metode ini ada untuk memenuhi kontrak UML.
        // Logika verifikasi yang sebenarnya dipecah menjadi verif_login_admin dan verif_login_user.
        System.out.println("Metode verifikasi umum dipanggil.");
    }

    /**
     * Metode pembantu untuk memverifikasi admin (menggunakan String ID dan Password).
     * Ini adalah implementasi praktis yang dipanggil oleh GUI.
     * @param adminId ID admin dalam bentuk String.
     * @param adminPass Password admin.
     * @return true jika valid, false jika tidak.
     */
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

    /**
     * Metode pembantu untuk memverifikasi user (menggunakan int ID).
     * Ini adalah implementasi praktis yang dipanggil oleh GUI.
     * @param userId ID user dalam bentuk integer.
     * @return true jika valid atau berhasil dibuat, false jika gagal.
     */
    public static boolean verif_login_user(int userId) {
        String checkSql = "SELECT id_user FROM user WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.db");
             PreparedStatement pstmtCheck = conn.prepareStatement(checkSql)) {
            pstmtCheck.setInt(1, userId);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    return true; // User ditemukan
                } else {
                    // Buat user baru jika tidak ditemukan
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

    // ===================================================================
    // METODE INISIALISASI DATABASE (TETAP STATIS)
    // ===================================================================
    
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