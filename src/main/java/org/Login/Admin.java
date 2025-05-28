public class Admin extends MainModel implements SettingData {
    private int id_Admin; // FK ke Login
    private String PWD;
    private int id_data;

    public Admin(Date Tanggal, String Status, String Deskripsi, String Tempat, int id_Admin, String PWD, int id_data) {
        super(Tanggal, Boolean.parseBoolean(Status), Deskripsi, Tempat, id_data);
    }

    public void Tbl_data() {}
    public void setPWD(String PWD) {}
    public void Edit_status(boolean Status) {}
    public void Tmp_data() {}

    public String getPWD() { return null; }
    public int getId_Admin() { return 0; }
    public Date getTanggal() { return null; }
    public String getStatus() { return null; }
    public String getDeskripsi() { return null; }
    public String getTempat() { return null; }

    public void setStatus(boolean Status) {}

    public void buat_tabel() {}
    public void updt_tabel() {}
    public void Save_data() {}
}
