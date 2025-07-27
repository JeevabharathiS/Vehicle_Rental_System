package VRPack;

public abstract class User {
    protected String email;
    protected String password;
    protected String role;
    public abstract void showMenu();

    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean authenticate(String email, String password){
        return this.email.equals(email) && this.password.equals(password);
    }

    public String getEmail(){
        return email;
    }

    public String getRole(){
        return role;
    }
}
