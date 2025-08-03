package repository;

public interface FranchiseRepository {
    String[][] findAllAsTable(); // [row][col] -> {id, name, city, state, manager}
    boolean exists(String id);
    void save(String id, String name, String street, String city, String state, String managerEmail);
    void update(String id, String name, String street, String city, String state, String managerEmail);
    void delete(String id);
}
