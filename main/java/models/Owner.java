package models;

public class Owner extends User {
    public Owner(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public Owner(String id, String name, String email, String password, boolean active) {
        super(id, name, email, password, active);
    }

    @Override
    public boolean login(String email, String password) {
        // Verifica se o usuário está ativo
        if (!this.active) {
            return false;
        }
        
        // Verifica se o email corresponde e a senha está correta
        return this.email.equals(email) && validatePassword(password);
    }
}
