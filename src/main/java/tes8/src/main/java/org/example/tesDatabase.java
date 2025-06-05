
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class tesDatabase {

    private static final String DB_URL = "jdbc:sqlite:database.db"; // Contoh path relatif

    public static Connection connect() {
        Connection conn = null;
        try {
            // Driver implicitly loaded with modern JDBC drivers - no Class.forName needed.
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Koneksi ke SQLite berhasil dibuat.");
            System.out.println("Lokasi database: " + conn.getMetaData().getURL().substring("jdbc:sqlite:".length()));
        } catch (SQLException e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS produk ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nama TEXT NOT NULL,"
                + " harga REAL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabel 'produk' berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.err.println("Error membuat tabel: " + e.getMessage());
        }
    }

    public static void insertProduct(String nama, double harga) {
        String sql = "INSERT INTO teslKonek(nama, harga) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setDouble(2, harga);
            pstmt.executeUpdate();
            System.out.println("Produk '" + nama + "' berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Error menyisipkan produk: " + e.getMessage());
        }
    }

    public static void selectAllProducts() {
        String sql = "SELECT id, nama, FROM tesKonek";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            System.out.println("\nDaftar Produk:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("nama") + "\t" +
                        rs.getDouble("harga"));
            }
        } catch (SQLException e) {
            System.err.println("Error mengambil produk: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewTable();

        // Uncomment to insert some test data
        // insertProduct("Laptop Super", 15000000.00);
        // insertProduct("Mouse Gaming", 350000.00);
        // insertProduct("Keyboard Mekanik", 750000.00);

        selectAllProducts();
    }
}

