// jan diubah2 layout nya sumpah @IBNU setengah mati aku buat co , emg java swing A#%$#g
// kalo kamu bisa rapiin ya well ths si :v

package org.example; // Sesuaikan dengan package Anda jika perlu

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Kelas GUI Login Utama
public class loginGUI extends JFrame {

    public loginGUI(String judul) {
        super(judul);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 550;
        int frameHeight = 700;
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setOpaque(true);
        setContentPane(layeredPane);

        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(Color.WHITE);
        panelBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        int margin = 25;
        int panelBgWidth = frameWidth - 20 - (2 * margin);
        int panelBgHeight = frameHeight - 30 - (2 * margin);
        panelBackground.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(panelBackground, JLayeredPane.DEFAULT_LAYER);

        JPanel panelTeksJudul = new JPanel();
        panelTeksJudul.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTeksJudul.setOpaque(false);
        JLabel labelJudul = new JLabel("ITACK");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 30));
        panelTeksJudul.add(labelJudul);
        int judulWidth = 200;
        int judulHeight = 60;
        int judulX = margin + (panelBgWidth - judulWidth) / 2;
        int judulY = margin + 25;
        panelTeksJudul.setBounds(judulX, judulY, judulWidth, judulHeight);
        layeredPane.add(panelTeksJudul, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10));


        int fotoWidth = 350;
        int fotoHeight = 300;
        int fotoX = margin + (panelBgWidth - fotoWidth) / 2;
        int fotoY = judulY + judulHeight + 10; // Posisi Y di bawah judul

        // Buat instance PhotoAnimator
        int durasiPerFrameAnimasiMs = 150; // Atur kecepatan animasi (misal 150ms per frame)
        PhotoAnimator panelAnimasi = new PhotoAnimator(durasiPerFrameAnimasiMs);
        panelAnimasi.setOpaque(false); // Sesuai dengan panelFoto sebelumnya

        // Muat gambar untuk animasi
        // PENTING: Sesuaikan pathPrefix, jumlah frame, dan ekstensi file!
        panelAnimasi.loadImages(
                "/frame/frame", // Contoh: /folder_di_classpath/nama_awal_file_
                1,                      // Nomor frame awal
                31,                     // Nomor frame akhir (total 30 foto)
                ".png",                 // Ekstensi file (bisa .jpg, .jpeg, dll.)
                fotoWidth,              // Lebar target untuk setiap frame
                fotoHeight              // Tinggi target untuk setiap frame
        );

        if (panelAnimasi.isAnimationLoadedSuccessfully()) {
            panelAnimasi.startAnimation();
        } else {
            // Jika gagal memuat, PhotoAnimator akan menampilkan pesan internal
            // atau Anda bisa menambahkan JLabel error ke panelAnimasi di sini jika mau
            System.err.println("Peringatan: Gagal memuat frame untuk animasi.");
        }

        panelAnimasi.setBounds(fotoX, fotoY, fotoWidth, fotoHeight);
        layeredPane.add(panelAnimasi, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 5)); // Layer yang sama dengan panelFoto
        // === AKHIR BAGIAN ANIMASI FOTO ===

        JPanel panelTombol = new JPanel();
        panelTombol.setLayout(new GridLayout(1, 2, 10, 0));
        panelTombol.setOpaque(false);


        JButton tombolUser = new JButton("User");
        styleButton(tombolUser, new Color(49, 51, 51), new Color(128, 128, 128));

        JButton tombolAdmin = new JButton("Admin");
        styleButton(tombolAdmin, new Color(49, 51, 51), new Color(128, 128, 128));

        panelTombol.add(tombolUser);
        panelTombol.add(tombolAdmin);

        int tombolPanelWidth = 350;
        int tombolPanelHeight = 70;
        int tombolPanelX = margin + (panelBgWidth - tombolPanelWidth) / 2;
        int tombolPanelY = fotoY + 30 + fotoHeight + 30;
        panelTombol.setBounds(tombolPanelX, tombolPanelY, tombolPanelWidth, tombolPanelHeight);
        layeredPane.add(panelTombol, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // ActionListener untuk tombol User
        tombolUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutup frame loginGUI saat ini
                dispose();
                // Tampilkan UserLoginGUI
                SwingUtilities.invokeLater(() -> new UserLoginGUI().setVisible(true));
            }
        });

        // ActionListener untuk tombol Admin
        tombolAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutup frame loginGUI saat ini
                dispose();
                // Tampilkan AdminLoginGUI
                SwingUtilities.invokeLater(() -> new AdminLoginGUI().setVisible(true));
            }
        });
    }

    // Metode helper untuk styling tombol
    private void styleButton(JButton button, Color bgColor, Color hoverColor) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loginGUI frameSaya = new loginGUI("ITACK - Login");
                frameSaya.setVisible(true);
            }
        });
    }
}

// Kelas untuk GUI Login User
class UserLoginGUI extends JFrame {
    public UserLoginGUI() {
        super("Login User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 500; // Lebar frame UserLoginGUI
        int frameHeight = 500; // Tinggi frame UserLoginGUI disesuaikan untuk field password
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setOpaque(true);
        setContentPane(layeredPane);

        // Panel Background dengan border dan margin dari tepi frame
        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(Color.WHITE);
        panelBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        int margin = 50;
        int panelBgWidth = frameWidth - 20 - (2 * margin);
        int panelBgHeight = frameHeight - 35 - (2 * margin);
        panelBackground.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(panelBackground, JLayeredPane.DEFAULT_LAYER);

        // Panel utama yang berisi komponen login
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Menggunakan BoxLayout untuk susunan vertikal
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Margin internal untuk mainPanel
        mainPanel.setOpaque(false); // Buat mainPanel transparan

        // Komponen ID User

        JLabel labelId = new JLabel("ID :");
        labelId.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField inputIdField = new JTextField(20);
        inputIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        JPanel panelID = new JPanel();
        panelID.add(labelId);
        panelID.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelID.setSize(new Dimension(Integer.MAX_VALUE, 10));
        panelID.setOpaque(false);
        // Mengatur agar field bisa melebar tapi tingginya tetap

        JPanel panelInputWrapper = new JPanel(new GridBagLayout());
        panelInputWrapper.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelInputWrapper.setOpaque(false);
        inputIdField.setOpaque(false);
        inputIdField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Kolom 0
        gbc.gridy = 0; // Baris 0
        gbc.anchor = GridBagConstraints.CENTER; // Menengahkan komponen di dalam selnya (vertikal dan horizontal)
        gbc.fill = GridBagConstraints.HORIZONTAL; // Biarkan field mengisi lebar sel jika sel lebih lebar
        gbc.weightx = 1.0;

        panelInputWrapper.add(inputIdField, gbc);

        // Tombol Login User
        JButton tombolLoginUser = new JButton("Login");
        tombolLoginUser.setFont(new Font("Arial", Font.BOLD, 16));
        tombolLoginUser.setAlignmentX(Component.CENTER_ALIGNMENT); // Tombol di tengah
        tombolLoginUser.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        tombolLoginUser.setMaximumSize(new Dimension(Integer.MAX_VALUE , 50));
        tombolLoginUser.setFocusPainted(false);
        tombolLoginUser.setBackground(new Color(49, 51, 51));
        tombolLoginUser.setForeground(Color.WHITE); // Teks putih karena latar gelap
        tombolLoginUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1), // Border abu-abu
                BorderFactory.createEmptyBorder(10, 25, 10, 25) // Padding
        ));
        tombolLoginUser.setOpaque(true);



        // Menambahkan komponen ke mainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(0,60)));
        mainPanel.add(panelID);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak kecil
        mainPanel.add(panelInputWrapper);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 45))); // Jarak lebih besar
        mainPanel.add(Box.createVerticalGlue()); // Mendorong tombol ke bawah jika ada sisa ruang
        mainPanel.add(tombolLoginUser);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Jarak di bawah tombol

        tombolLoginUser.addActionListener(e -> {
            String id = inputIdField.getText();
            JOptionPane.showMessageDialog(this,
                    "ID User: " + id +
                            "\n(Logika login belum diimplementasikan)");
        });

        mainPanel.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(mainPanel,Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10));
    }
}

// Kelas untuk GUI Login Admin
class AdminLoginGUI extends JFrame {
    public AdminLoginGUI() {
        super("Login Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 500; // Lebar frame AdminLoginGUI
        int frameHeight = 550; // Tinggi frame AdminLoginGUI disesuaikan
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.WHITE); // Latar belakang layeredPane putih
        layeredPane.setOpaque(true);
        setContentPane(layeredPane); // layeredPane menjadi content pane

        // Panel Background dengan border dan margin dari tepi frame
        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(Color.WHITE); // Warna latar sama dengan layeredPane
        panelBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Border garis

        int margin = 50; // Margin panelBackground dari tepi layeredPane (frame)
        // Perhitungan ukuran panelBackground yang dikoreksi agar simetris
        int panelBgWidth = frameWidth - 30 - (2 * margin);
        int panelBgHeight = frameHeight - 50 - (2 * margin);
        panelBackground.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(panelBackground, JLayeredPane.DEFAULT_LAYER); // Tambahkan ke layer bawah

        // Panel utama yang berisi komponen login (ID Label, ID Field, Password Label, Password Field, Tombol Login)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Menggunakan BoxLayout untuk susunan vertikal
        // Beri margin internal untuk mainPanel agar komponennya tidak terlalu mepet ke border panelBackground
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Margin: atas, kiri, bawah, kanan
        mainPanel.setOpaque(false); // Buat mainPanel transparan agar warna panelBackground terlihat

        // Komponen ID Admin
        JLabel labelIdAdmin = new JLabel("ID Admin:");
        labelIdAdmin.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField inputIdAdminField = new JTextField(20); // Jumlah kolom preferensi
        inputIdAdminField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputIdAdminField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        inputIdAdminField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1), // Border garis DARK_GRAY (sesuai permintaan Anda)
                BorderFactory.createEmptyBorder(5, 8, 5, 8)      // Padding internal: atas, kiri, bawah, kanan
        ));
        JPanel panelIDAdmin = new JPanel();
        panelIDAdmin.add(labelIdAdmin);
        panelIDAdmin.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelIDAdmin.setSize(new Dimension(Integer.MAX_VALUE, 10));
        panelIDAdmin.setOpaque(false);


        // Komponen Password Admin
        JLabel labelPasswordAdmin = new JLabel("Password:");
        labelPasswordAdmin.setFont(new Font("Arial", Font.BOLD, 16));
        JPasswordField inputPasswordAdminField = new JPasswordField(20);
        inputPasswordAdminField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPasswordAdminField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        inputPasswordAdminField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1), // Border garis DARK_GRAY
                BorderFactory.createEmptyBorder(5, 8, 5, 8)      // Padding internal
        ));
        JPanel panelPasswordAdmin = new JPanel();
        panelPasswordAdmin.add(labelPasswordAdmin);
        panelPasswordAdmin.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelPasswordAdmin.setSize(new Dimension(Integer.MAX_VALUE, 10));
        panelPasswordAdmin.setOpaque(false);


        // Tombol Login Admin
        JButton tombolLoginAdmin = new JButton("Login");
        tombolLoginAdmin.setFont(new Font("Arial", Font.BOLD, 16));
        tombolLoginAdmin.setAlignmentX(Component.CENTER_ALIGNMENT); // Tombol di tengah
        tombolLoginAdmin.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50)); // Ukuran tombol disesuaikan
        tombolLoginAdmin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Agar ukuran tombol tetap
        // Anda bisa memanggil metode styleButton dari loginGUI jika dibuat public static
        // atau menerapkan styling serupa di sini:
        tombolLoginAdmin.setBackground(new Color(49, 51, 51)); // Abu-abu
        tombolLoginAdmin.setForeground(Color.WHITE);
        tombolLoginAdmin.setFocusPainted(false);
        tombolLoginAdmin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80,80,80), 1),
                BorderFactory.createEmptyBorder(10,25,10,25)
        ));


        // Menambahkan komponen ke mainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(0,40)));
        mainPanel.add(panelIDAdmin);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8))); // Jarak kecil
        mainPanel.add(inputIdAdminField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Jarak lebih besar
        mainPanel.add(panelPasswordAdmin);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(inputPasswordAdminField);
        mainPanel.add(Box.createRigidArea(new Dimension(0,40)));
        mainPanel.add(Box.createVerticalGlue()); // Mendorong tombol ke bawah jika ada sisa ruang
        mainPanel.add(tombolLoginAdmin);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Jarak di bawah tombol


        // ActionListener untuk tombol login admin
        tombolLoginAdmin.addActionListener(e -> {
            String id = inputIdAdminField.getText();
            String password = new String(inputPasswordAdminField.getPassword());
            JOptionPane.showMessageDialog(this, "ID Admin: " + id + "\nPassword: " + password + "\n(Logika login belum diimplementasikan)");
            // Tambahkan logika validasi dan autentikasi di sini
        });

        // Mengatur bounds untuk mainPanel agar berada di atas dan di dalam panelBackground
        mainPanel.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(mainPanel,Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10)); // Layer di atas panelBackground
    }
}