package org.example;

// Import kelas-kelas yang diperlukan
import javax.swing.SwingUtilities;
import org.example.GUI.loginInterface;
import org.example.logic.Login;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Memuat database
                Login.initDatabase();

                loginInterface loginFrame = new loginInterface("ITACK - Login");
                loginFrame.setVisible(true);
            }
        });
    }
}
