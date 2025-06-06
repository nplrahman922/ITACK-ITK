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
import javax.swing.table.DefaultTableModel; // Diperlukan jika ingin mengembalikan TableModel

// Asumsikan MainModel dan SettingData sudah ada dan benar
public class admin extends MainModel implements SettingData {

    private static final String DB_URL = "jdbc:sqlite:database.db";
    private LocalDate tanggal;
    private String PWD;
    private String id_admin;

    // Field untuk menyimpan data tabel yang di-refresh untuk GUI
    private Vector<Vector<Object>> dataTabel;
    private Vector<String> kolomTabel;

    public admin(String id_data, String Deskripsi, String Tempat, boolean Status, int tahun, int bulan, int hari, String password, String id_admin) {
        super(id_data, Deskripsi, Tempat, Status);
        this.tanggal = LocalDate.of(tahun, bulan, hari);
        this.PWD = password;
        this.id_admin = id_admin;
        // Inisialisasi Vector agar tidak null
        this.dataTabel = new Vector<>();
        this.kolomTabel = new Vector<>();
    }

    // Metode getter
    public LocalDate getTanggal() { return tanggal; }
    public Vector<Vector<Object>> getDataVector() { return dataTabel; }
    public Vector<String> getColumnNamesVector() { return kolomTabel; }

    // Metode koneksi private
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Koneksi database gagal: " + e.getMessage());
        }
        return conn;
    }

    // Metode dari interface
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
            System.out.println("[ADMIN] Tabel 'data' berhasil diperiksa/dibuat.");
        } catch (SQLException e) {
            System.err.println("[ADMIN] Gagal membuat tabel: " + e.getMessage());
        }
    }

    @Override
    public void Tbh_data() {
        Save_data();
    }

    @Override
    public void Save_data() {
        String sql = "INSERT OR REPLACE INTO data (id_data, tanggal, deskripsi, tempat, status) VALUES(?,?,?,?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getId_data());
            pstmt.setString(2, getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(3, getDeskripsi());
            pstmt.setString(4, getTempat());
            pstmt.setString(5, getStatus() ? "Baik" : "Rusak");
            pstmt.executeUpdate();
            System.out.println("[ADMIN] Data dengan ID: " + getId_data() + " berhasil disimpan.");
        } catch (SQLException e) {
            System.err.println("[ADMIN] Gagal menyimpan data: " + e.getMessage());
        }
    }

    @Override
    public void Tmpl_data() {
        // ... (Implementasi untuk cetak ke konsol) ...
        System.out.println("\n--- [ADMIN] Menampilkan data ke konsol... ---");
        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM data")){
            while(rs.next()){
                System.out.println("  ID: "+rs.getString("id_data")+", Status: "+rs.getString("status"));
            }
        }catch(SQLException e){
            System.err.println("[ADMIN] Gagal menampilkan data: " + e.getMessage());
        }
    }

    /**
     * Metode ini sekarang berfungsi untuk REFRESH.
     * Ia mengambil data terbaru dari database dan menyimpannya ke field
     * 'dataTabel' dan 'kolomTabel' agar bisa diambil oleh GUI.
     */
    @Override
    public void updt_tabel() {
        System.out.println("--- [ADMIN] Proses Refresh Data Tabel Dimulai ---");

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

            // Simpan hasil query ke field instance kelas ini
            this.dataTabel = data;
            this.kolomTabel = columnNames;

            System.out.println("Data berhasil dimuat ulang dari database untuk di-refresh.");

        } catch (SQLException e) {
            System.err.println("[ADMIN] Gagal me-refresh data: " + e.getMessage());
        }
    }

    // --- METODE TAMBAHAN KHUSUS KELAS ADMIN ---

    /**
     * Metode ini berfungsi khusus untuk MENGUBAH (UPDATE) data di database.
     * Ia menggunakan data dari objek 'admin' saat ini.
     */
    public void Edit_data() {
        System.out.println("--- [ADMIN] Proses Edit Data ke Database Dimulai ---");
        String sql = "UPDATE data SET tanggal = ?, deskripsi = ?, tempat = ?, status = ? WHERE id_data = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(2, getDeskripsi());
            pstmt.setString(3, getTempat());
            pstmt.setString(4, getStatus() ? "Baik" : "Rusak");
            pstmt.setString(5, getId_data());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[ADMIN] Data dengan ID: " + getId_data() + " berhasil di-update.");
            } else {
                System.out.println("[ADMIN] Tidak ada data yang di-update. ID: " + getId_data() + " mungkin tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.err.println("[ADMIN] Gagal meng-update data: " + e.getMessage());
        }
    }

    /**
     * Metode ini berfungsi khusus untuk MENGHAPUS data dari database.
     * @param id_data_hapus ID dari data yang ingin dihapus.
     */
    public void Hapus_data(String id_data_hapus) {
        System.out.println("--- [ADMIN] Proses Hapus Data Dimulai ---");
        String sql = "DELETE FROM data WHERE id_data = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_data_hapus);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[ADMIN] Data dengan ID: " + id_data_hapus + " berhasil dihapus.");
            } else {
                System.out.println("[ADMIN] Tidak ada data yang dihapus. ID: " + id_data_hapus + " mungkin tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.err.println("[ADMIN] Gagal menghapus data: " + e.getMessage());
        }
    }
}