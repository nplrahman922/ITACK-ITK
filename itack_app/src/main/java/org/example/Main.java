package org.example;
// objek akan dibuat dalam main !! logic maupun GUI
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // test logic Login
        login baru = new login("admin@dummy","admin123","admin");
        baru.validlogin();
        login kece = new login("11241089","user123","user");
        kece.validlogin();
        user tes = new user("1122","aman","g2",true ,2021,1,1,12212);
        tes.Tbh_data();
        tes.Tmpl_data();
        tes.updt_tabel();

        admin tes1 = new admin("12212","beklah","warkop babeh",false,2200,3,4,"admin123","admin@dummy");
        tes1.Tbh_data();
        tes1.Tmpl_data();
        tes1.updt_tabel();
        tes1.Edit_data();
        tes1.Hapus_data("12212");

    }
}