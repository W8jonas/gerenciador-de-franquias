package repository;

import domain.model.Seller;

import java.util.List;

public interface SellerRepository {
    boolean isValidCredentials(String email, String password);
    void saveAll(List<Seller> sellers); // <-- Adicionado para salvar lista
    List<Seller> findAll();
    Seller findByEmail(String email);
}
