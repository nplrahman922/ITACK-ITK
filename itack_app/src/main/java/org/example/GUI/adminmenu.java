package org.example.GUI;
// karna susah atur layout , layout dimatikan diatur menggunakan jarak dari frame ( minus nya tidak responsive ) T_T
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.border.Border;

public class adminmenu extends JFrame {
    public adminmenu(String judul) {
        super(judul);

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
        int panelBgWidth = frameWidth - 40 - (1 * margin);
        int panelBgHeight = frameHeight - 40 - (2 * margin);
        panelBackground.setBounds(margin, margin, panelBgWidth, panelBgHeight);
        layeredPane.add(panelBackground, JLayeredPane.DEFAULT_LAYER);

        // Kolom konten sebelah kiri pov Gweh T_T
        // saran foto kalo ada yang keren ganti aja di URL nya
        JPanel fotoPanel = new JPanel();
        fotoPanel.setBackground(Color.WHITE);
        Border outerBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border innerBorder = BorderFactory.createEmptyBorder(5, 10, 5, 10);
        fotoPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        int fotoWidth = 200;
        int fotoHeight = 150;
        fotoPanel.setBounds(50, 50, fotoWidth, fotoHeight);
        fotoPanel.setLayout(new BorderLayout());


        URL imageUrl = getClass().getResource("/profile/user_profile.png");
        JLabel imageLabel;

        if (imageUrl != null) {

            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalIcon.getImage().getScaledInstance(fotoWidth, fotoHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel = new JLabel(scaledIcon);

        } else {

            imageLabel = new JLabel("Gambar tidak ditemukan!");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setForeground(Color.RED);
        }

        fotoPanel.add(imageLabel, BorderLayout.CENTER);
        layeredPane.add(fotoPanel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 10));


        JPanel tampil_ID = new JPanel();
        String labelIDText = "ID :" + "implementasi logic ID";
        JLabel labelID = new JLabel(labelIDText);
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
        labelQuotes.setOpaque(false);
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

        //konten Date
        JLabel tanggal = new JLabel("D a t e . . =");
        tanggal.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tanggal.setOpaque(false);
        tanggal.setBounds(60,400,200,30);
        layeredPane.add(tanggal, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));



        // jtf konten date
        JTextField tanggal_text = new JTextField(20);
        tanggal_text.setBackground(Color.WHITE);
        tanggal_text.setOpaque(true);
        tanggal_text.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1), // Border garis DARK_GRAY (sesuai permintaan Anda)
                BorderFactory.createEmptyBorder(5, 8, 5, 8)      // Padding internal: atas, kiri, bawah, kanan
        ));
        tanggal_text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        tanggal_text.setBounds(65, 430 ,310, 30 );
        layeredPane.add(tanggal_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // tambahin action listener sendiri disini buat ambil text di JTF (aku malas) T_T

        //Konten Status
        JLabel status = new JLabel("S t a t u s . . = ");
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        status.setOpaque(false);
        status.setBounds(60,460,200,30);
        layeredPane.add(status, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // rad button status
        JRadioButton radioButtonSelesai = new JRadioButton("Baik");
        radioButtonSelesai.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16)); // Mengubah font
        radioButtonSelesai.setForeground(new Color(49, 51, 51));      // Warna abu gelap
        radioButtonSelesai.setBackground(Color.WHITE);  // Warna latar belakang abu terang
        radioButtonSelesai.setOpaque(true);
        radioButtonSelesai.setFocusPainted(false);
        radioButtonSelesai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButtonSelesai.setBounds(60,490,150,30);
        layeredPane.add(radioButtonSelesai, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        JRadioButton radioButtonProgress = new JRadioButton("Rusak");
        radioButtonProgress.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16)); // Mengubah font
        radioButtonProgress.setForeground(new Color(49, 51, 51));      // Warna abu gelap
        radioButtonProgress.setBackground(Color.WHITE);  // Warna latar belakang abu terang
        radioButtonProgress.setOpaque(true);
        radioButtonProgress.setFocusPainted(false);
        radioButtonProgress.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButtonProgress.setBounds(210,490,150,30);
        layeredPane.add(radioButtonProgress, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));


        // membuat pilihan hanya menjadi 1

        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(radioButtonSelesai);
        statusGroup.add(radioButtonProgress);

        // radio buttonbaik always True :V

        radioButtonSelesai.setSelected(true);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton source = (JRadioButton) e.getSource();
                if (source.isSelected()) {
                    String pilihan = source.getText(); //memuat data pilihan radio box menjadi text str
                    System.out.println("pilihan anda = " + pilihan); // hapus ketika sudah ada logic
                }
            }
        };
        radioButtonSelesai.addActionListener(listener);
        radioButtonProgress.addActionListener(listener);

        JLabel tempat = new JLabel("T e m p a t . . = ");
        tempat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tempat.setBounds(60,520,200,30);
        layeredPane.add(tempat, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // konten JTF dari tempat

        JTextField tempat_text = new JTextField();
        tempat_text.setBackground(Color.WHITE);
        tempat_text.setOpaque(true);
        tempat_text.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1), // Border garis DARK_GRAY (sesuai permintaan Anda)
                BorderFactory.createEmptyBorder(5, 8, 5, 8)      // Padding internal: atas, kiri, bawah, kanan
        ));
        tempat_text.setBounds(65,550,310,30);
        layeredPane.add(tempat_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // action listener buat ambil JTA buat sendiri ye aku malas :|

        // kolom konten sebelah kanan pov gweh T_T

        JPanel konten_kanan = new JPanel();
        konten_kanan.setBackground(Color.WHITE);
        konten_kanan.setBounds(510,50,420,130);
        konten_kanan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        konten_kanan.setOpaque(true);
        layeredPane.add(konten_kanan, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // Konten deskripsi

        JLabel deskripsi = new JLabel("D e s k r i p s i . . = ");
        deskripsi.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        deskripsi.setBounds(520,60,200,40);
        layeredPane.add(deskripsi, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));
        deskripsi.setOpaque(false);

        JTextArea deskripsi_text = new JTextArea();
        deskripsi_text.setBackground(Color.WHITE);
        deskripsi_text.setOpaque(true);
        deskripsi_text.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1), // Border garis DARK_GRAY (sesuai permintaan Anda)
                BorderFactory.createEmptyBorder(5, 8, 5, 8)      // Padding internal: atas, kiri, bawah, kanan
        ));
        deskripsi_text.setBounds(525,100,390,60);
        layeredPane.add(deskripsi_text, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // konten deskripsi_text

        // Button tambah

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
        tambah.setBounds(510,190,420,30);
        layeredPane.add(tambah, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // action listener buat fungsi tombol kau atur lah ketua

        // konten edit
        JPanel panel_edit = new JPanel();
        panel_edit.setOpaque(false);
        panel_edit.setBounds(510,230,420,80);
        panel_edit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(panel_edit, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        JLabel status_edit = new JLabel("S t a t u s . . = ");
        status_edit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        status_edit.setBounds(520,235,200,30);
        status_edit.setOpaque(false);
        layeredPane.add(status_edit, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // radio button

        JRadioButton radioButtonEditBaik = new JRadioButton("Baik");
        radioButtonEditBaik.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16)); // Mengubah font
        radioButtonEditBaik.setForeground(new Color(49, 51, 51));      // Warna abu gelap
        radioButtonEditBaik.setBackground(Color.WHITE);  // Warna latar belakang abu terang
        radioButtonEditBaik.setOpaque(true);
        radioButtonEditBaik.setFocusPainted(false);
        radioButtonEditBaik.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButtonEditBaik.setBounds(525,265,80,30);
        layeredPane.add(radioButtonEditBaik, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        JRadioButton radioButtonEditProgress = new JRadioButton("Perbaikan");
        radioButtonEditProgress.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,16));
        radioButtonEditProgress.setForeground(new Color(49, 51, 51));      // Warna abu gelap
        radioButtonEditProgress.setBackground(Color.WHITE);  // Warna latar belakang abu terang
        radioButtonEditProgress.setOpaque(true);
        radioButtonEditProgress.setFocusPainted(false);
        radioButtonEditProgress.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButtonEditProgress.setBounds(640,265,100,30);
        layeredPane.add(radioButtonEditProgress, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        JRadioButton radioButtonEditRusak = new JRadioButton("Rusak");
        radioButtonEditRusak.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,16));
        radioButtonEditRusak.setForeground(new Color(49, 51, 51));      // Warna abu gelap
        radioButtonEditRusak.setBackground(Color.WHITE);  // Warna latar belakang abu terang
        radioButtonEditRusak.setOpaque(true);
        radioButtonEditRusak.setFocusPainted(false);
        radioButtonEditRusak.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButtonEditRusak.setBounds(800,265,100,30);
        layeredPane.add(radioButtonEditRusak, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));

        // action listener bikin sendiri mlass

        JButton button_edit = new JButton("Edit");
        button_edit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        button_edit.setForeground(Color.WHITE);
        button_edit.setBackground(new Color(49, 51, 51));
        button_edit.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1), // Border abu-abu
                BorderFactory.createEmptyBorder(10, 25, 10, 25) // Padding
        ));
        button_edit.setLayout(new FlowLayout(FlowLayout.CENTER));
        button_edit.setOpaque(true);
        button_edit.setFocusPainted(false);
        button_edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_edit.setBounds(510,320,420,30);
        layeredPane.add(button_edit, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // konten tabel

        JPanel tabel_panel = new JPanel();
        tabel_panel.setBackground(Color.WHITE);
        tabel_panel.setBounds(510,360,420,200);
        tabel_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layeredPane.add(tabel_panel, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

        // tabel

        String[] kolom_nama = {"ID" , "Tanggal" , "Status" , "Tempat","Deskripsi"};

        Object[][] data_tabel = {
                {Integer.valueOf(1), "2025-06-05", "Baik", "Pengecekan rutin berkala", "Gudang A - Rak 01"}
                ,{Integer.valueOf(2), "2025-06-04", "Rusak", "Layar monitor retak, perlu penggantian", "Ruang Server Lt. 2"},
                {Integer.valueOf(3), "2025-06-03", "Dalam Perbaikan", "Penggantian Hard Disk", "Lab Komputer B"},
                {Integer.valueOf(4), "2025-06-02", "Baik", "Software update selesai", "Kantor Pemasaran"},
                {Integer.valueOf(5), "2025-06-01", "Perlu Perhatian", "Baterai lemah, sering drop", "Ruang Rapat Utama"},
                {Integer.valueOf(6), "2025-05-31", "Baik", "Instalasi perangkat baru", "Meja Staf #12"},
                {Integer.valueOf(7), "2025-05-30", "Rusak", "Tidak bisa terhubung ke jaringan", "Gudang C - Sektor 3"},
                {Integer.valueOf(8), "2025-06-02", "Baik", "Software update selesai", "Kantor Pemasaran"},
                {Integer.valueOf(9), "2025-06-01", "Perlu Perhatian", "Baterai lemah, sering drop", "Ruang Rapat Utama"},
                {Integer.valueOf(10), "2025-05-31", "Baik", "Instalasi perangkat baru", "Meja Staf #12"},
                {Integer.valueOf(11), "2025-05-30", "Rusak", "Tidak bisa terhubung ke jaringan", "Gudang C - Sektor 3"} // dummy data
        };
        // tes dummy implemen sendiri logic tabel nya logic nya pikir maneh T_T

        DefaultTableModel tableModel = new DefaultTableModel(data_tabel, kolom_nama) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Misalnya, buat kolom 'id' dan 'Tanggal' tidak bisa diedit
                return !(column == 0 );
            }

        };

        // 4. Membuat JTable dengan model yang sudah dibuat
        JTable table = new JTable(tableModel);

        // 5. Mengatur beberapa properti tabel (opsional tapi direkomendasikan)
        table.setFillsViewportHeight(true); // Membuat tabel mengisi tinggi viewport JScrollPane
        table.setRowHeight(25); // Mengatur tinggi setiap baris
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Mengatur font tabel
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Mengatur font header tabel
        table.getTableHeader().setBackground(Color.LIGHT_GRAY); // Warna latar header

        // Mengaktifkan sorting pada kolom tabel saat header diklik
        table.setAutoCreateRowSorter(true);

        // Mengatur lebar kolom (opsional)
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // id
        table.getColumnModel().getColumn(1).setPreferredWidth(100);  // Tanggal
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // status
        table.getColumnModel().getColumn(3).setPreferredWidth(300);  // deskripsi
        table.getColumnModel().getColumn(4).setPreferredWidth(150);  // tempat

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        // 6. Menempatkan JTable di dalam JScrollPane
        // Ini penting agar tabel bisa di-scroll jika datanya melebihi ukuran tampilan
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(520,370,400,180);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        layeredPane.add(scrollPane, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 20));
        // button hapus
        JButton button_hapus = new JButton("Hapus");
        button_hapus.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        button_hapus.setForeground(Color.WHITE);
        button_hapus.setBackground(new Color(49, 51, 51));
        button_hapus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1), // Border abu-abu
                BorderFactory.createEmptyBorder(10, 25, 10, 25) // Padding
        ));
        button_hapus.setLayout(new FlowLayout(FlowLayout.CENTER));
        button_hapus.setOpaque(true);
        button_hapus.setFocusPainted(false);
        button_hapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_hapus.setBounds(510,570,420,30);
        layeredPane.add(button_hapus, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 15));

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                adminmenu frameSaya = new adminmenu("GUI-Admin Menu");
                frameSaya.setVisible(true);
            }
        });
    }


}
