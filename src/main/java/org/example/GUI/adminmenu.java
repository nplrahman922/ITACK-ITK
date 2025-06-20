package org.example.GUI;

import org.example.logic.admin; // Import kelas logika baru
import org.example.logic.Login; // Diperlukan untuk main method

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class adminmenu extends JFrame {
    // Deklarasi komponen (tetap sama)
    private JTable table;
    private JTextField tanggal_text, tempat_text;
    private JTextArea deskripsi_text;
    private JRadioButton radioButtonSelesai, radioButtonProgress, radioButtonEditBaik, radioButtonEditProgress, radioButtonEditRusak;
    private JButton tambah, button_edit, button_hapus;
    private String adminId;

    public adminmenu(String judul, String adminId) {
        super(judul);
        this.adminId = adminId;

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
        URL imageUrl = getClass().getResource("/profile/admin.jpg");
        if (imageUrl != null) {
            ImageIcon scaledIcon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH));
            fotoPanel.add(new JLabel(scaledIcon), BorderLayout.CENTER);
        } else {
            fotoPanel.add(new JLabel("Gambar tidak ada", SwingConstants.CENTER));
        }
        layeredPane.add(fotoPanel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10));

        JPanel tampil_ID = new JPanel();
        JLabel labelID = new JLabel("ID: " + this.adminId);
        labelID.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        tampil_ID.add(labelID);
        tampil_ID.setBounds(50, 200, 200, 40);
        tampil_ID.setOpaque(false);
        layeredPane.add(tampil_ID, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JPanel komen = new JPanel();
        JLabel labelKomen = new JLabel("H e l l o . . . . A d m i n  ! !");
        labelKomen.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        komen.add(labelKomen);
        komen.setOpaque(false);
        komen.setBounds(40, 230, 250, 40);
        layeredPane.add(komen, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JPanel panelquotes = new JPanel();
        panelquotes.setBackground(Color.WHITE);
        panelquotes.setBounds(50, 280, 280, 60);
        panelquotes.setOpaque(true);
        panelquotes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel labelQuotes = new JLabel("<html>W h a t . d o . <b>y o u</b> . w a n n a<br><b>F i x</b> . . . . T o d a y ?</html>");
        labelQuotes.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 16));
        panelquotes.add(labelQuotes);
        layeredPane.add(panelquotes, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel komentar_lap = new JLabel("<html>B e r i k a n . . <B>l a p o r a n</B> m u . . ! ! </html>");
        komentar_lap.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 14));
        komentar_lap.setOpaque(false);
        komentar_lap.setBounds(55,340,300,40);
        layeredPane.add(komentar_lap, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JPanel konten_kiri = new JPanel();
        konten_kiri.setOpaque(false);
        konten_kiri.setBounds(50, 380, 350, 220);
        konten_kiri.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(konten_kiri, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel tanggal = new JLabel("D a t e . . =");
        tanggal.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tanggal.setOpaque(false);
        tanggal.setBounds(60,400,200,30);
        layeredPane.add(tanggal, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        tanggal_text = new JTextField(20);
        tanggal_text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        tanggal_text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        tanggal_text.setBounds(65, 430 ,310, 30);
        layeredPane.add(tanggal_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        JLabel status = new JLabel("S t a t u s . . = ");
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        status.setOpaque(false);
        status.setBounds(60,460,200,30);
        layeredPane.add(status, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonSelesai = new JRadioButton("Baik");
        radioButtonSelesai.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        radioButtonSelesai.setBackground(Color.WHITE);
        radioButtonSelesai.setBounds(60,490,150,30);
        layeredPane.add(radioButtonSelesai, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonProgress = new JRadioButton("Rusak");
        radioButtonProgress.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        radioButtonProgress.setBackground(Color.WHITE);
        radioButtonProgress.setBounds(210,490,150,30);
        layeredPane.add(radioButtonProgress, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        ButtonGroup statusGroupLeft = new ButtonGroup();
        statusGroupLeft.add(radioButtonSelesai);
        statusGroupLeft.add(radioButtonProgress);
        radioButtonSelesai.setSelected(true);

        JLabel tempat = new JLabel("T e m p a t . . = ");
        tempat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tempat.setBounds(60,520,200,30);
        layeredPane.add(tempat, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        tempat_text = new JTextField();
        tempat_text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        tempat_text.setBounds(65,550,310,30);
        layeredPane.add(tempat_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // --- Kolom Kanan ---
        JPanel konten_kanan = new JPanel();
        konten_kanan.setBackground(Color.WHITE);
        konten_kanan.setBounds(510,50,420,130);
        konten_kanan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(konten_kanan, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel deskripsi = new JLabel("D e s k r i p s i . . = ");
        deskripsi.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        deskripsi.setBounds(520,60,200,40);
        layeredPane.add(deskripsi, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        deskripsi_text = new JTextArea();
        deskripsi_text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        deskripsi_text.setLineWrap(true);
        deskripsi_text.setWrapStyleWord(true);
        JScrollPane deskripsiScrollPane = new JScrollPane(deskripsi_text);
        deskripsiScrollPane.setBounds(525,100,390,60);
        layeredPane.add(deskripsiScrollPane, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        tambah = new JButton("Tambah");
        tambah.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tambah.setForeground(Color.WHITE);
        tambah.setBackground(new Color(49, 51, 51));
        tambah.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1), BorderFactory.createEmptyBorder(10, 25, 10, 25)));
        tambah.setLayout(new FlowLayout(FlowLayout.CENTER));
        tambah.setOpaque(true);
        tambah.setFocusPainted(false);
        tambah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tambah.setBounds(510,190,420,30);
        layeredPane.add(tambah, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JPanel panel_edit = new JPanel();
        panel_edit.setOpaque(false);
        panel_edit.setBounds(510,230,420,80);
        panel_edit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(panel_edit, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel status_edit = new JLabel("S t a t u s . . = ");
        status_edit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        status_edit.setBounds(520,235,200,30);
        layeredPane.add(status_edit, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonEditBaik = new JRadioButton("Baik");
        radioButtonEditBaik.setBounds(525,265,80,30);
        radioButtonEditBaik.setBackground(Color.WHITE);
        layeredPane.add(radioButtonEditBaik, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonEditProgress = new JRadioButton("Perbaikan");
        radioButtonEditProgress.setBounds(640,265,100,30);
        radioButtonEditProgress.setBackground(Color.WHITE);
        layeredPane.add(radioButtonEditProgress, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        radioButtonEditRusak = new JRadioButton("Rusak");
        radioButtonEditRusak.setBounds(800,265,100,30);
        radioButtonEditRusak.setBackground(Color.WHITE);
        layeredPane.add(radioButtonEditRusak, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        ButtonGroup statusGroupRight = new ButtonGroup();
        statusGroupRight.add(radioButtonEditBaik);
        statusGroupRight.add(radioButtonEditProgress);
        statusGroupRight.add(radioButtonEditRusak);
        radioButtonEditBaik.setSelected(true);

        button_edit = new JButton("Edit");
        button_edit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        button_edit.setForeground(Color.WHITE);
        button_edit.setBackground(new Color(49, 51, 51));
        button_edit.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1), BorderFactory.createEmptyBorder(10, 25, 10, 25)));
        button_edit.setLayout(new FlowLayout(FlowLayout.CENTER));
        button_edit.setOpaque(true);
        button_edit.setFocusPainted(false);
        button_edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_edit.setBounds(510,320,420,30);
        layeredPane.add(button_edit, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        button_hapus = new JButton("Hapus");
        button_hapus.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        button_hapus.setForeground(Color.WHITE);
        button_hapus.setBackground(new Color(49, 51, 51));
        button_hapus.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1), BorderFactory.createEmptyBorder(10, 25, 10, 25)));
        button_hapus.setLayout(new FlowLayout(FlowLayout.CENTER));
        button_hapus.setOpaque(true);
        button_hapus.setFocusPainted(false);
        button_hapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_hapus.setBounds(510,570,420,30);
        layeredPane.add(button_hapus, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // --- Tabel Data ---
        JPanel tabel_panel = new JPanel(new BorderLayout());
        tabel_panel.setBackground(Color.WHITE);
        tabel_panel.setBounds(510,360,420,200);
        tabel_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(tabel_panel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        table = new JTable();
        refreshTable();

        JScrollPane scrollPaneTable = new JScrollPane(table);
        tabel_panel.add(scrollPaneTable, BorderLayout.CENTER);

        // Pasang listener pada JScrollPane, bukan pada JTable secara langsung.
        scrollPaneTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Periksa apakah tabel sedang tidak dalam mode edit
                if (!table.isEditing()) {
                    // Dapatkan titik di mana mouse diklik
                    Point point = e.getPoint();
                    // Cek apakah ada baris di titik tersebut
                    int rowAtPoint = table.rowAtPoint(point);
                    if (rowAtPoint == -1) {
                        // Panggil metode untuk membersihkan pilihan dan form
                        bersihkanForm();
                    }
                }
            }
        });

        // Listener untuk mengisi form HANYA saat sebuah baris di tabel diklik
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pastikan yang diklik adalah sebuah baris
                if (table.getSelectedRow() != -1) {
                    isiFormDariTabel();
                }
            }
        });

        // --- HUBUNGKAN ACTION LISTENERS ---
        tambah.addActionListener(e -> tambahData());
        button_edit.addActionListener(e -> editData());
        button_hapus.addActionListener(e -> hapusData());
    }

    // --- METODE LOGIKA ---

    private void refreshTable() {
        DefaultTableModel model = admin.getTableModel();
        table.setModel(model);

        // Menyembunyikan kolom pertama (kolom "ID" asli) dari tampilan
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // Mengatur lebar kolom yang terlihat agar rapi
        // Indeks sekarang bergeser karena "ID" disembunyikan
        table.getColumnModel().getColumn(1).setPreferredWidth(40);   // Kolom "No."
        table.getColumnModel().getColumn(2).setPreferredWidth(90);   // Kolom "Tanggal"
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Kolom "Status"
        table.getColumnModel().getColumn(4).setPreferredWidth(120);  // Kolom "Deskripsi"
        table.getColumnModel().getColumn(5).setPreferredWidth(90);   // Kolom "Tempat"
    }

    /**
     * PERUBAHAN: Disesuaikan dengan model admin baru.
     */
    private void tambahData() {
        try {
            if (tanggal_text.getText().trim().isEmpty() || tempat_text.getText().trim().isEmpty() || deskripsi_text.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Ambil status dalam bentuk String, bukan boolean.
            String status;
            if (radioButtonSelesai.isSelected()) {
                status = "Baik";
            } else {
                status = "Rusak";
            }

            // Ambil data lain dari form
            LocalDate tanggal = LocalDate.parse(tanggal_text.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int newId = admin.getLatestId() + 1;

            // 2. Buat objek admin menggunakan konstruktor yang benar (dengan String status)
            admin dataBaru = new admin(this.adminId, null, newId, tanggal, status, deskripsi_text.getText(), tempat_text.getText());

            // 3. Panggil metode Save_data()
            dataBaru.Save_data();

            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            refreshTable();
            bersihkanForm();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Format Tanggal salah! Gunakan format yyyy-MM-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * PERUBAHAN: Disesuaikan dengan model admin baru.
     */
    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            // 1. Ambil status dalam bentuk String dari radio button yang benar
            String status;
            if (radioButtonEditBaik.isSelected()) {
                status = "Baik";
            } else if (radioButtonEditProgress.isSelected()) {
                status = "Perbaikan";
            } else {
                status = "Rusak";
            }

            // Ambil data lain dari form
            LocalDate tanggal = LocalDate.parse(tanggal_text.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int modelRow = table.convertRowIndexToModel(selectedRow);
            int id = (int) table.getModel().getValueAt(modelRow, 0);

            // 2. Buat objek admin menggunakan konstruktor baru yang menerima String status
            admin dataDiedit = new admin(this.adminId, null, id, tanggal, status, deskripsi_text.getText(), tempat_text.getText());

            // 3. Panggil metode Save_data()
            dataDiedit.Save_data();

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            refreshTable();
            bersihkanForm();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Format Tanggal salah! Gunakan format yyyy-MM-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusData() {
        // Metode ini tidak berinteraksi langsung dengan objek, jadi tidak perlu diubah
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) table.getValueAt(selectedRow, 0);
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data dengan ID " + id + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (admin.hapusData(id)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                refreshTable();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * PERUBAHAN: Menyesuaikan status String dari tabel ke pilihan radio button.
     */
    private void isiFormDariTabel() {
        int selectedRowInView = table.getSelectedRow();

        if (selectedRowInView != -1) {
            // Konversi indeks baris dari tampilan ke model untuk keamanan
            int modelRow = table.convertRowIndexToModel(selectedRowInView);

            // Ambil data dari model menggunakan indeks kolom yang sudah benar
            String tanggalDariTabel = table.getModel().getValueAt(modelRow, 2).toString();
            String statusDariTabel = table.getModel().getValueAt(modelRow, 3).toString();
            String deskripsiDariTabel = table.getModel().getValueAt(modelRow, 4).toString();
            String tempatDariTabel = table.getModel().getValueAt(modelRow, 5).toString();

            // Masukkan data ke dalam form
            tanggal_text.setText(tanggalDariTabel);
            tempat_text.setText(tempatDariTabel);
            deskripsi_text.setText(deskripsiDariTabel);

            // Sesuaikan pilihan radio button berdasarkan status dari tabel
            if (statusDariTabel.equalsIgnoreCase("Baik")) {
                radioButtonSelesai.setSelected(true);
                radioButtonEditBaik.setSelected(true);
            } else if (statusDariTabel.equalsIgnoreCase("Perbaikan")) {
                radioButtonProgress.setSelected(true); // Untuk form "Tambah"
                radioButtonEditProgress.setSelected(true); // Untuk form "Edit"
            } else { // Rusak
                radioButtonProgress.setSelected(true); // Untuk form "Tambah"
                radioButtonEditRusak.setSelected(true); // Untuk form "Edit"
            }

            tambah.setEnabled(false); // Nonaktifkan tombol tambah saat mengedit
        }
    }

    private void bersihkanForm() {
        tanggal_text.setText("");
        tempat_text.setText("");
        deskripsi_text.setText("");
        radioButtonSelesai.setSelected(true);
        radioButtonEditBaik.setSelected(true);
        table.clearSelection();

        if (!tambah.isEnabled()) {
            tambah.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login.initDatabase();
            new adminmenu("GUI-Admin Menu", "admin-test").setVisible(true);
        });
    }
}