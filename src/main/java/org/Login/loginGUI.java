package org.Login; // Sesuaikan dengan package Anda jika perlu

import javax.swing.*;
import java.awt.*;
import java.net.URL; // Diperlukan untuk memuat resource dari classpath

public class loginGUI extends JFrame {

    public loginGUI(String judul) {
        super(judul);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Ukuran frame kita definisikan di sini
        int frameWidth = 550;
        int frameHeight = 700;
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);

        // Membuat JLayeredPane sebagai dasar utama
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.WHITE); // Atur latar belakang JLayeredPane menjadi putih
        layeredPane.setOpaque(true);         // Pastikan JLayeredPane opak agar warnanya terlihat

        // Mengatur JLayeredPane sebagai content pane dari JFrame
        setContentPane(layeredPane);

        // 1. Panel Latar Belakang Internal (Background Panel dengan border dan margin)
        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(Color.WHITE); // Latar belakang panel ini juga putih
        // Membuat border garis hitam dengan ketebalan 3
        panelBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        //atur margin untuk panelBackground
        int margin = 25; // Margin yang diinginkan untuk panelBackground
        // Perhitungan lebar dan tinggi panelBackground yang dikoreksi:
        int panelBgWidth = frameWidth - 25 - (2 * margin);
        int panelBgHeight = frameHeight - 50 - (2 * margin);

        panelBackground.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(panelBackground, JLayeredPane.DEFAULT_LAYER); // Layer paling bawah

        // --- Komponen-komponen yang akan diletakkan di atas panelBackground ---

        // B. Panel untuk Tulisan "ITACK" (panelKontenLogin) - DITEMPATKAN PERTAMA
        JPanel panelTeksJudul = new JPanel(); // Mengganti nama variabel agar lebih jelas
        panelTeksJudul.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTeksJudul.setOpaque(false); // Buat transparan agar menyatu

        JLabel labelJudul = new JLabel("ITACK"); // Mengganti nama variabel
        labelJudul.setFont(new Font("Arial", Font.BOLD, 30)); // Perbesar font
        panelTeksJudul.add(labelJudul);

        int judulWidth = 200;  // Lebar yang cukup untuk teks "ITACK"
        int judulHeight = 60; // Tinggi yang cukup untuk teks
        // Posisi panelTeksJudul di tengah atas panelBackground
        int judulX = margin + (panelBgWidth - judulWidth) / 2;
        int judulY = margin + 50; // Jarak 50px dari atas panelBackground

        panelTeksJudul.setBounds(judulX, judulY, judulWidth, judulHeight);
        // Layer untuk teks bisa sedikit di atas background, atau sama dengan foto jika tidak ada overlap
        layeredPane.add(panelTeksJudul, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10));


        // A. Panel untuk GIF Animasi - DITEMPATKAN SETELAH TEKS JUDUL
        JPanel panelFoto = new JPanel();
        panelFoto.setLayout(new FlowLayout(FlowLayout.CENTER)); // Agar GIF di tengah panelFoto
        panelFoto.setOpaque(false); // Buat transparan agar menyatu dengan panelBackground

        URL gifUrl = getClass().getResource("/animasi.gif"); // Ganti "animasi.gif"
        JLabel labelGif;

        if (gifUrl != null) {
            ImageIcon ft = new ImageIcon(gifUrl);
            labelGif = new JLabel(ft);
        } else {
            labelGif = new JLabel("GIF tidak ditemukan!");
            labelGif.setForeground(Color.RED);
        }
        panelFoto.add(labelGif);

        int fotoWidth = 300;  // Lebar area untuk GIF
        int fotoHeight = 250; // Tinggi area untuk GIF
        // Posisi panelFoto di tengah panelBackground, di bawah panelTeksJudul
        int fotoX = margin + (panelBgWidth - fotoWidth) / 2;
        // judulY + judulHeight memberikan posisi y tepat di bawah panelTeksJudul
        // + 20 untuk memberi sedikit jarak antara teks ITACK dan GIF
        int fotoY = judulY + judulHeight + 20;

        panelFoto.setBounds(fotoX, fotoY, fotoWidth, fotoHeight);
        layeredPane.add(panelFoto, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 5));

        JPanel panelTombol = new JPanel();
        // Menggunakan GridLayout: 1 baris, 2 kolom, jarak horizontal 10px, jarak vertikal 0px
        panelTombol.setLayout(new GridLayout(1, 2, 10, 0));
        panelTombol.setOpaque(false); // Buat transparan agar menyatu dengan panelBackground

        // Tombol User
        JButton tombolUser = new JButton("User");
        tombolUser.setFont(new Font("Arial", Font.PLAIN, 16));
        tombolUser.setFocusPainted(false); // Menghilangkan border fokus
        tombolUser.setBackground(new Color(70, 130, 180)); // Warna SteelBlue
        tombolUser.setForeground(Color.WHITE); // Warna teks putih
        tombolUser.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        // Efek saat mouse hover (opsional, sederhana)
        tombolUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombolUser.setBackground(new Color(100, 160, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tombolUser.setBackground(new Color(70, 130, 180));
            }
        });


        // Tombol Admin
        JButton tombolAdmin = new JButton("Admin");
        tombolAdmin.setFont(new Font("Arial", Font.PLAIN, 16));
        tombolAdmin.setFocusPainted(false);
        tombolAdmin.setBackground(new Color(220, 20, 60)); // Warna Crimson
        tombolAdmin.setForeground(Color.WHITE);
        tombolAdmin.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        tombolAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombolAdmin.setBackground(new Color(240, 50, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tombolAdmin.setBackground(new Color(220, 20, 60));
            }
        });

        // Menambahkan tombol ke panelTombol
        panelTombol.add(tombolUser);
        panelTombol.add(tombolAdmin);

        // Mengatur posisi dan ukuran panelTombol
        int tombolPanelWidth = 350; // Sesuaikan lebar panel tombol
        int tombolPanelHeight = 70;  // Sesuaikan tinggi panel tombol (termasuk padding tombol)
        // Perhitungan ini sudah benar untuk menengahkan panelTombol secara horizontal
        int tombolPanelX = margin + (panelBgWidth - tombolPanelWidth) / 2;
        // Posisi di bawah panelFoto
        int tombolPanelY = fotoY + fotoHeight + 30; // Jarak 30px dari bawah GIF

        panelTombol.setBounds(tombolPanelX, tombolPanelY, tombolPanelWidth, tombolPanelHeight);
        layeredPane.add(panelTombol, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));
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