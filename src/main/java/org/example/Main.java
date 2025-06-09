package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Selalu siapkan tabel di awal
        admin.buat_tabel();

        Scanner scanner = new Scanner(System.in);
        String pilihan = "";

        // 2. Loop menu utama
        while (!pilihan.equals("4")) {
            // Tampilkan tabel data terbaru di setiap awal menu
            admin.tampilkan_semua_data();

            System.out.println("\n--- MENU APLIKASI ITACK-ITK ---");
            System.out.println("1. Tambah Data");
            System.out.println("2. Edit Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");

            pilihan = scanner.nextLine();

            // 3. Jalankan fungsi sesuai pilihan
            switch (pilihan) {
                case "1":
                    admin.Tbh_data();
                    break;
                case "2":
                    // KOREKSI: Menggunakan nama method yang baru
                    admin.edit_data();
                    break;
                case "3":
                    // KOREKSI: Mengaktifkan fitur hapus data
                    admin.hapus_data();
                    break;
                case "4":
                    System.out.println("--- Aplikasi ditutup. Sampai jumpa! ---");
                    break;
                default:
                    System.err.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }
        scanner.close();
    }
}
