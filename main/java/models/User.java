package models;

public abstract class User {
    protected String id;
    protected String name;
    protected String email;
    protected String password;
    protected boolean active;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true; // Por padrão, usuário é ativo
    }

    public User(String id, String name, String email, String password, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    // Getters e Setters para id
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Getters e Setters para name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Getters e Setters para email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getters e Setters para active
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    // Método abstrato login conforme diagrama
    public abstract boolean login(String email, String password);

    // Método auxiliar para validação de senha (pode ser usado pelas subclasses)
    protected boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
