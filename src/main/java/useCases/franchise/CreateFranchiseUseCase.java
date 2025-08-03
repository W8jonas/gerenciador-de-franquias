package useCases.franchise;

import repository.FranchiseRepository;

import java.util.UUID;

public class CreateFranchiseUseCase {
    private final FranchiseRepository repo;

    public CreateFranchiseUseCase(FranchiseRepository repo) {
        this.repo = repo;
    }

    public String execute(String name, String street, String city, String state, String managerEmail) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("Cidade é obrigatória.");
        if (state == null || state.isBlank()) throw new IllegalArgumentException("Estado é obrigatório.");

        String id = UUID.randomUUID().toString();
        repo.save(id, name, street, city, state, managerEmail);
        return id;
    }
}
