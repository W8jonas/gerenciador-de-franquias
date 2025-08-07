package repository;

public interface ManagerRepository {
    String[][] findAllAsTable(); // {id, name, email, active}
    boolean exists(String id);
    boolean existsByEmail(String email);
    void save(String id, String name, String email, String password, boolean active);
    void update(String id, String name, String email, String password, boolean active);
    void delete(String id);

    // suporte ao login
    boolean isValidCredentials(String email, String password);
}
