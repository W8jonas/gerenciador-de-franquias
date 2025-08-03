package useCases.franchise;

import repository.FranchiseRepository;

public class UpdateFranchiseUseCase {
    private final FranchiseRepository repo;

    public UpdateFranchiseUseCase(FranchiseRepository repo) {
        this.repo = repo;
    }

    public void execute(String id, String name, String street, String city, String state, String managerEmail) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID é obrigatório.");
        if (!repo.exists(id)) throw new IllegalStateException("Franquia não encontrada.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("Cidade é obrigatório.");
        if (state == null || state.isBlank()) throw new IllegalArgumentException("Estado é obrigatório.");

        repo.update(id, name, street, city, state, managerEmail);
    }
}
