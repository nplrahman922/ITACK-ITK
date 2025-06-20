package org.example;

// Import kelas-kelas yang diperlukan
import javax.swing.SwingUtilities;
import org.example.GUI.loginInterface; // Import kelas antarmuka login Anda
import org.example.logic.Login;

/**
 * Kelas Main utama untuk menjalankan seluruh aplikasi ITACK GUI.
 * Ini adalah titik awal (entry point) dari aplikasi.
 */
public class Main {
    
    public static void main(String[] args) {
        // semua kode GUI
        // dijalankan pada 'Event Dispatch Thread',
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //memuat database
                Login.initDatabase();

                loginInterface loginFrame = new loginInterface("ITACK - Login");
                loginFrame.setVisible(true);
            }
        });
    }
}