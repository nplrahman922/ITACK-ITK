package org.example.logic;

public interface SettingData {

    //Membuat tabel untuk menyimpan data objek jika belum ada.
    void buat_tabel();

    //Refresh Data Tabel
    void updt_tabel();

    // Menyimpan data objek ke dalam database.
    void Save_data();
}
