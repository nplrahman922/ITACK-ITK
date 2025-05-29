package org;

import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Menu Login =====");
        System.out.println("1. Login sebagai Admin");
        System.out.println("2. Login sebagai User");
        System.out.print("Pilih opsi (1/2): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); 

        if (pilihan == 1) {
            System.out.print("Masukkan ID Admin: ");
            String idAdmin = scanner.nextLine();

            System.out.print("Masukkan Password Admin: ");
            String passAdmin = scanner.nextLine();

            System.out.println("Login berhasil sebagai Admin dengan ID: " + idAdmin);

        } else if (pilihan == 2) {
            System.out.print("Masukkan ID User: ");
            int idUser = scanner.nextInt();
            scanner.nextLine(); 

            // Apapun ID-nya, tetap login berhasil
            System.out.println("Login berhasil sebagai User dengan ID: " + idUser);

        } else {
            System.out.println("Pilihan tidak valid.");
        }

        scanner.close();
    }
}
