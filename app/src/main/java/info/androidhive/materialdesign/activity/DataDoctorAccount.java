package info.androidhive.materialdesign.activity;

/**
 * Created by SaMiR on 4/28/2016.
 */
public class DataDoctorAccount {
 private String username,type,password,name,email;

    public DataDoctorAccount() {
    }

    public DataDoctorAccount(String name, String username, String password, String email, String type) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.type = type;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
