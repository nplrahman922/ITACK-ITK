package org.example;

import java.sql.*;

public class Login {
    private String id;
    private String password;
    private String pengguna;
    private static final String DB_URL = "jdbc:sqlite:Data.db";

    public Login(String id, String password, String pengguna) {
        this.id = id;
        this.password = password;
        this.pengguna = pengguna;
        initDatabase(); // Inisialisasi otomatis saat objek dibuat
    }

    public void setId(String id) { this.id = id; }
    public void setPassword(String password) { this.password = password; }
    public void setPengguna(String pengguna) { this.pengguna = pengguna; }

    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getPengguna() { return pengguna; }

    // Inisialisasi database
    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Buat tabel admin
            String sqlCreateAdmin = "CREATE TABLE IF NOT EXISTS admin (" +
                    "id_admin TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL)";
            stmt.execute(sqlCreateAdmin);

            // Buat tabel user
            String sqlCreateUser = "CREATE TABLE IF NOT EXISTS user (" +
                    "id_user INTEGER PRIMARY KEY)";
            stmt.execute(sqlCreateUser);

            // Cek apakah admin default sudah ada
            String sqlCheckAdmin = "SELECT COUNT(*) AS total FROM admin";
            ResultSet rs = stmt.executeQuery(sqlCheckAdmin);
            if (rs.next() && rs.getInt("total") == 0) {
                String sqlInsertAdmin = "INSERT INTO admin (id_admin, password) VALUES ('admin', 'admin123')";
                stmt.execute(sqlInsertAdmin);
                System.out.println("Admin default berhasil ditambahkan (ID: admin, password: admin123).");
            }

        } catch (SQLException e) {
            System.err.println("Gagal inisialisasi database: " + e.getMessage());
        }
    }

    // Validasi login
    public void validLogin() {
        String idUser = getId();
        String passwordUser = getPassword();
        String penggunaInput = getPengguna();

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            System.out.println("Koneksi ke SQLite berhasil dibuat.");

            if ("admin".equalsIgnoreCase(penggunaInput)) {
                String sql = "SELECT * FROM admin WHERE id_admin = ? AND password = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, idUser);
                    pstmt.setString(2, passwordUser);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("Login admin berhasil untuk ID: " + idUser);
                        } else {
                            System.out.println("Login admin gagal atau data tidak ditemukan.");
                        }
                    }
                }

            } else if ("user".equalsIgnoreCase(penggunaInput)) {
                System.out.println("Memproses login untuk tipe: user");

                int targetIdUser;
                try {
                    targetIdUser = Integer.parseInt(idUser.trim());
                } catch (NumberFormatException e) {
                    System.err.println("Format ID User tidak valid: " + idUser);
                    return;
                }

                String sqlCheckUser = "SELECT id_user FROM user WHERE id_user = ?";
                boolean userDitemukanDiDB = false;

                try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckUser)) {
                    pstmtCheck.setInt(1, targetIdUser);
                    try (ResultSet rs = pstmtCheck.executeQuery()) {
                        if (rs.next()) {
                            userDitemukanDiDB = true;
                            System.out.println("Login user berhasil untuk ID: " + targetIdUser);
                        }
                    }
                }

                if (!userDitemukanDiDB) {
                    System.out.println("User dengan ID " + targetIdUser + " tidak ditemukan. Mencoba membuat user baru...");
                    String sqlInsertUser = "INSERT INTO user (id_user) VALUES (?)";
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertUser)) {
                        pstmtInsert.setInt(1, targetIdUser);
                        int rowsAffected = pstmtInsert.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("User dengan ID " + targetIdUser + " berhasil dibuat.");
                        } else {
                            System.err.println("Gagal membuat user.");
                        }
                    }
                }

            } else {
                System.out.println("Tipe pengguna tidak dikenal: " + penggunaInput);
            }

        } catch (SQLException e) {
            System.err.println("Operasi database atau koneksi gagal: " + e.getMessage());
        }
    }
}
