package org.example.GUI;

import org.example.user; // Import kelas logika user

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// import javax.swing.border.Border;
// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;

public class usermenu extends JFrame {
    // Deklarasi komponen sebagai field kelas
    private JTable table;
    private JTextField tanggal_text, tempat_text, deskripsi_text;
    private JRadioButton radioButtonBaik, radioButtonRusak;
    private JButton tambah;
    private String userId;

    public usermenu(String judul, String userId) {
        super(judul);
        this.userId = userId;

        // --- Pengaturan Frame (Sesuai Kode Asli) ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 1000;
        int frameHeight = 700;
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setOpaque(true);
        setContentPane(layeredPane);

        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(Color.WHITE);
        panelBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        int margin = 25;
        panelBackground.setBounds(margin, margin, frameWidth - 2 * margin - 15, frameHeight - 2 * margin - 40);
        layeredPane.add(panelBackground, JLayeredPane.DEFAULT_LAYER);

        // --- Kolom Kiri (Sesuai Kode Asli) ---
        JPanel fotoPanel = new JPanel(new BorderLayout());
        fotoPanel.setBackground(Color.WHITE);
        fotoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        fotoPanel.setBounds(50, 50, 200, 150);
        URL imageUrl = getClass().getResource("/profile/user_profile.png");
        if (imageUrl != null) {
            ImageIcon scaledIcon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH));
            fotoPanel.add(new JLabel(scaledIcon), BorderLayout.CENTER);
        } else {
            fotoPanel.add(new JLabel("Gambar tidak ditemukan", SwingConstants.CENTER));
        }
        layeredPane.add(fotoPanel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10));

        JPanel tampil_ID = new JPanel();
        JLabel labelID = new JLabel("ID: " + this.userId);
        labelID.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        tampil_ID.add(labelID);
        tampil_ID.setBounds(50, 200, 200, 40);
        tampil_ID.setOpaque(false);
        layeredPane.add(tampil_ID, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));
        
        JPanel komen = new JPanel();
        JLabel labelKomen = new JLabel("W e l c o m e . . . . U s e r s :");
        labelKomen.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        komen.add(labelKomen);
        komen.setOpaque(false);
        komen.setBounds(40, 250, 250, 40);
        layeredPane.add(komen, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JPanel garis = new JPanel();
        garis.setBackground(Color.black);
        garis.setBounds(55,280,280,3);
        layeredPane.add(garis, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));
        
        JPanel panelquotes = new JPanel();
        panelquotes.setBackground(Color.WHITE);
        panelquotes.setBounds(50, 300, 280, 60);
        panelquotes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel labelQuotes = new JLabel("<html>W h a t . d o . y o u . w a n n a<br>r e p o r t s . . . . T o d a y ?</html>");
        labelQuotes.setFont(new Font(Font.SANS_SERIF,NORMAL, 16));
        panelquotes.add(labelQuotes);
        layeredPane.add(panelquotes, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel komentar_lap = new JLabel("<html>B e r i k a n . . <B>l a p o r a n</B> m u . . ! ! </html>");
        komentar_lap.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 14));
        komentar_lap.setBounds(55,370,300,40);
        layeredPane.add(komentar_lap, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JPanel konten_kiri = new JPanel();
        konten_kiri.setOpaque(false);
        konten_kiri.setBounds(50, 420, 350, 180);
        konten_kiri.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(konten_kiri, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel tanggal = new JLabel("D a t e . . =");
        tanggal.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tanggal.setBounds(60,421,200,40);
        layeredPane.add(tanggal, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        tanggal_text = new JTextField(20);
        tanggal_text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        tanggal_text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        tanggal_text.setBounds(65, 470 ,310, 40);
        layeredPane.add(tanggal_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        JLabel status = new JLabel("S t a t u s . . = ");
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        status.setBounds(60,510,200,40);
        layeredPane.add(status, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonBaik = new JRadioButton("Baik");
        radioButtonBaik.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        radioButtonBaik.setBackground(Color.WHITE);
        radioButtonBaik.setBounds(60,540,150,40);
        layeredPane.add(radioButtonBaik, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonRusak = new JRadioButton("Rusak");
        radioButtonRusak.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        radioButtonRusak.setBackground(Color.WHITE);
        radioButtonRusak.setBounds(210,540,150,40);
        layeredPane.add(radioButtonRusak, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));
        
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(radioButtonBaik);
        statusGroup.add(radioButtonRusak);
        radioButtonBaik.setSelected(true);
        
        // --- Kolom Kanan (Sesuai Kode Asli) ---
        JPanel konten_kanan = new JPanel();
        konten_kanan.setBackground(Color.WHITE);
        konten_kanan.setBounds(510,70,420,220);
        konten_kanan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(konten_kanan, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));
        
        JLabel tempat = new JLabel("T e m p a t . . = ");
        tempat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tempat.setBounds(520,75,200,40);
        layeredPane.add(tempat, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));
        
        tempat_text = new JTextField();
        tempat_text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        tempat_text.setBounds(525,120,390,40);
        layeredPane.add(tempat_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        JLabel deskripsi = new JLabel("D e s k r i p s i . . = ");
        deskripsi.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        deskripsi.setBounds(520,165,200,40);
        layeredPane.add(deskripsi, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        deskripsi_text = new JTextField();
        deskripsi_text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        deskripsi_text.setBounds(525,215,390,40);
        layeredPane.add(deskripsi_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));
        
        JButton tambah = new JButton("Tambah");
        tambah.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tambah.setForeground(Color.WHITE);
        tambah.setBackground(new Color(49, 51, 51));
        tambah.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1), // Border abu-abu
                BorderFactory.createEmptyBorder(10, 25, 10, 25) // Padding
        ));
        tambah.setLayout(new FlowLayout(FlowLayout.CENTER));
        tambah.setOpaque(true);
        tambah.setFocusPainted(false);
        tambah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tambah.setBounds(510,300,420,30);
        layeredPane.add(tambah, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // --- Tabel Data ---
        JPanel tabel_panel = new JPanel(new BorderLayout());
        tabel_panel.setBackground(Color.WHITE);
        tabel_panel.setBounds(510,350,420,250);
        tabel_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(tabel_panel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        table = new JTable();
        refreshTable(); // Muat data awal
        JScrollPane scrollPane = new JScrollPane(table);
        tabel_panel.add(scrollPane, BorderLayout.CENTER);

        // --- HUBUNGKAN ACTION LISTENER ---
        tambah.addActionListener(e -> tambahData());
    }

    // --- METODE LOGIKA ---
    
    private void refreshTable() {
        DefaultTableModel model = user.getTableModel();
        table.setModel(model);

        // Menyembunyikan kolom pertama (kolom "ID" asli) dari tampilan
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // Mengatur lebar kolom yang terlihat agar rapi
        table.getColumnModel().getColumn(1).setPreferredWidth(40);   // Kolom "No."
        table.getColumnModel().getColumn(2).setPreferredWidth(90);   // Kolom "Tanggal"
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Kolom "Status"
        table.getColumnModel().getColumn(4).setPreferredWidth(120);  // Kolom "Deskripsi"
        table.getColumnModel().getColumn(5).setPreferredWidth(90);   // Kolom "Tempat"
    }

    private void tambahData() {
        try {
            if (tanggal_text.getText().trim().isEmpty() || tempat_text.getText().trim().isEmpty() || deskripsi_text.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate.parse(tanggal_text.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            String status = radioButtonBaik.isSelected() ? "Baik" : "Rusak";
            int newId = user.getLatestId() + 1; // Menggunakan kelas 'user'
            
            user dataBaru = new user(tanggal_text.getText(), status, deskripsi_text.getText(), tempat_text.getText(), newId);
            if (dataBaru.simpanData()) {
                JOptionPane.showMessageDialog(this, "Laporan berhasil ditambahkan!");
                refreshTable();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan laporan.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Format Tanggal salah! Gunakan format yyyy-MM-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void bersihkanForm() {
        tanggal_text.setText("");
        tempat_text.setText("");
        deskripsi_text.setText("");
        radioButtonBaik.setSelected(true);
    }

    // Main method untuk testing individual
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inisialisasi DB sebelum frame dibuat untuk testing
            org.example.Login.initDatabase();
            new usermenu("GUI-User Menu", "user-test").setVisible(true);
        });
    }
}