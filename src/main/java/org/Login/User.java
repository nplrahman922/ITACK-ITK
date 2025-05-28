public class User extends MainModel implements SettingData {
    private int id_User; // FK ke Login
    private int id_data;

    public User(Date Tanggal, String Status, String Deskripsi, String Tempat, int id_data) {
        super(Tanggal, Boolean.parseBoolean(Status), Deskripsi, Tempat, id_data);
    }

    public void Tbl_data() {}
    public void setId_user(int id) {}
    public void Tmp_data() {}

    public int getTanggal() { return 0; }
    public String getDeskripsi() { return null; }
    public String getStatus() { return null; }
    public String getTempat() { return null; }

    public int getId_User() { return 0; }
    public void setStatus(boolean Status) {}

    public void buat_tabel() {}
    public void updt_tabel() {}
    public void Save_data() {}
}
