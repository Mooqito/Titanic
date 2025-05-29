package model.Authneticate;

public class User {

    private String user_name;
    private String password;
    private String gmail;

    public User(String user_name, String password, String gmail) {
        this.user_name = user_name;
        this.password = password;
        this.gmail = gmail;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }
}
