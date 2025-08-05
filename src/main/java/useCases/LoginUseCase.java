package useCases;

import domain.model.Owner;
import repository.*;

import java.util.Optional;

public class LoginUseCase {

    private final OwnerRepository ownerRepo;
    private final ManagerRepository mgrRepo;
    private final SellerRepository sellerRepo;

    public LoginUseCase(OwnerRepository ownerRepo, ManagerRepository mgrRepo, SellerRepository sellerRepo) {
        this.ownerRepo = ownerRepo;
        this.mgrRepo   = mgrRepo;
        this.sellerRepo= sellerRepo;
    }

    /**
     * @return 1 = Owner, 2 = Manager, 3 = Seller
     * @throws IllegalArgumentException credenciais inválidas
     */
    public int execute(String email, String password) {
        Optional<Owner> optOwner = ownerRepo.findFirstOwner();
        if (optOwner.isPresent()) {
            Owner owner = optOwner.get();
            if (owner.getEmail().equals(email) && owner.authenticate(password)) {
                return 1;
            }
        }

        if (mgrRepo.isValidCredentials(email, password)) {
            return 2;
        }

        if (sellerRepo.isValidCredentials(email, password)) {
            return 3;
        }

        throw new IllegalArgumentException("Credenciais incorretas");


    }
}
