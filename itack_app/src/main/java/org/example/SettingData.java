package org.example;

/**
 * Interface ini mendefinisikan operasi-operasi dasar terkait
 * penyiapan dan penyimpanan data yang harus diimplementasikan oleh kelas model.
 */
public interface SettingData {

    /**
     * Menyiapkan atau membuat tabel di database jika belum ada.
     */
    void buat_tabel();

    /**
     * Memperbarui atau me-refresh data pada tabel.
     * Dalam konteks aplikasi ini, logika refresh ada di GUI,
     * namun metode ini tetap didefinisikan untuk kesesuaian dengan UML.
     */
    void updt_tabel();

    /**
     * Menyimpan data objek saat ini ke database.
     * Metode ini menangani operasi INSERT atau UPDATE.
     */
    void Save_data();
}