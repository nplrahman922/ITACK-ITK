package org.example;

// Import kelas-kelas yang diperlukan
import javax.swing.SwingUtilities;
import org.example.GUI.loginInterface; // Import kelas antarmuka login Anda

/**
 * Kelas Main utama untuk menjalankan seluruh aplikasi ITACK GUI.
 * Ini adalah titik awal (entry point) dari aplikasi.
 */
public class Main {
    
    public static void main(String[] args) {
        // SwingUtilities.invokeLater memastikan bahwa semua kode GUI
        // dijalankan pada 'Event Dispatch Thread', yang merupakan
        // praktik terbaik untuk aplikasi Swing agar terhindar dari masalah threading.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Panggil metode untuk memastikan database dan tabel sudah siap.
                // Metode initDatabase() akan membuat tabel jika belum ada.
                Login.initDatabase();

                // 2. Buat dan tampilkan jendela login utama.
                // Ini akan memulai alur aplikasi GUI.
                loginInterface loginFrame = new loginInterface("ITACK - Login");
                loginFrame.setVisible(true);
            }
        });
    }
}