package useCases.franchise;

import repository.FranchiseRepository;

public class DeleteFranchiseUseCase {
    private final FranchiseRepository repo;

    public DeleteFranchiseUseCase(FranchiseRepository repo) {
        this.repo = repo;
    }

    public void execute(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID é obrigatório.");
        if (!repo.exists(id)) throw new IllegalStateException("Franquia não encontrada.");
        repo.delete(id);
    }
}
