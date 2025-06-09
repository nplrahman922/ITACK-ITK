package org.example;

public class MainLogin {
    public static void main(String[] args) {

        // === Test Login Admin ===
        System.out.println("=== Test Login Admin ===");
        Login loginAdmin = new Login("wicak", "member", "admin");
        loginAdmin.validLogin();

        // === Test Login User (user existing) ===
        System.out.println("\n=== Test Login User ===");
        Login loginUser = new Login("1121", "", "user"); // password tetap tidak dipakai
        loginUser.validLogin();

    }
}
