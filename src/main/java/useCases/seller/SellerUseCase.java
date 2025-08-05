package useCases.seller;

import domain.model.Seller;
import repository.SellerRepository;

import java.util.List;

public class SellerUseCase {

    private final SellerRepository sellerRepository;

    public SellerUseCase(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public void save(Seller seller) {
        if (seller == null) {
            throw new IllegalArgumentException("Vendedor não pode ser nulo");
        }

        List<Seller> sellers = findAll();

        for (Seller s : sellers) {
            if (s.getEmail().equalsIgnoreCase(seller.getEmail())) {
                throw new IllegalArgumentException("Já existe um vendedor com este e-mail.");
            }
        }

        sellers.add(seller);
        sellerRepository.saveAll(sellers);
    }

    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    public Seller findByEmail(String email) {
        return sellerRepository.findByEmail(email);
    }

    public void update(Seller updatedSeller) {
        List<Seller> sellers = findAll();
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getEmail().equalsIgnoreCase(updatedSeller.getEmail())) {
                sellers.set(i, updatedSeller);
                break;
            }
        }
        sellerRepository.saveAll(sellers);
    }

    public void deleteByEmail(String email) {
        List<Seller> sellers = findAll();
        sellers.removeIf(s -> s.getEmail().equalsIgnoreCase(email));
        sellerRepository.saveAll(sellers);
    }
}
