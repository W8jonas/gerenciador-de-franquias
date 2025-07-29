package models;

public class Owner extends User {
    public Owner(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public Owner(String id, String name, String email, String password, boolean active) {
        super(id, name, email, password, active);
    }
}
