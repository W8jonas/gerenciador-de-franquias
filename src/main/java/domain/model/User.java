package domain.model;

import java.io.Serializable;
import java.io.Serial;

public abstract class User implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}
