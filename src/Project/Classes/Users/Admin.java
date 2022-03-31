package Project.Classes.Users;

public class Admin {
    private static Admin instance;
    private final  String id;
    private final String password;

    private Admin() {
        id = "f49eef0b-d4fa-42bc-8960-247052382ae5";
        password = "sn00pdoggyd0G";
    }

    public static Admin createAdmin() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
