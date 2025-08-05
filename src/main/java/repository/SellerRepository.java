package repository;

public interface SellerRepository {
    boolean isValidCredentials(String email, String password);
}