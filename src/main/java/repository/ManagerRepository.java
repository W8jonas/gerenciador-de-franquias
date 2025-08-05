package repository;

public interface ManagerRepository {
    boolean isValidCredentials(String email, String password);
}