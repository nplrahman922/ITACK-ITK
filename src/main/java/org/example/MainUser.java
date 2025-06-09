package org.example;

import java.util.Scanner;

public class MainUser {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Buat tabel saat aplikasi pertama kali dijalankan
        User.buat_tabel();

        System.out.println("=== Aplikasi Manajemen Laporan Inventaris ===");

        while (running) {
            System.out.println("\nPilih menu:");
            System.out.println("1. Tambah Data Laporan");
            System.out.println("2. Tampilkan Semua Laporan");
            System.out.println("3. Keluar");
            System.out.print("Pilihan Anda: ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    User.Tbh_data();
                    break;
                case "2":
                    User.tampilkan_semua_data();
                    break;
                case "3":
                    System.out.println("Terima kasih. Program selesai.");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        scanner.close();
    }
}
