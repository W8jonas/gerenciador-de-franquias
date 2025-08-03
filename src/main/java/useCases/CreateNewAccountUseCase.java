package useCases;

import domain.model.Owner;
import repository.OwnerRepository;

import java.util.UUID;

public class CreateNewAccountUseCase {

    private final OwnerRepository ownerRepository;

    public CreateNewAccountUseCase(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public String execute(String name, String email, String password) {
        if (ownerRepository.existsAnyOwner()) {
            throw new IllegalStateException("There is already an Owner in the system.");
        }

        String id = UUID.randomUUID().toString();
        Owner owner = new Owner(name, email, password);
        ownerRepository.save(owner);
        return id;
    }
}
