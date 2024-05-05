public class User {
    public int id;
    public String username;
    public String password;
    public String email;
    public boolean isAdmin;

    // Constructors, getters, setters (omitted for brevity)

    public User(int id, String username, String password, String email, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
