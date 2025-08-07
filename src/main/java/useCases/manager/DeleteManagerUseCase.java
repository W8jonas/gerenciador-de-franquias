package useCases.manager;

import repository.ManagerRepository;

public class DeleteManagerUseCase {
    private final ManagerRepository repo;

    public DeleteManagerUseCase(ManagerRepository repo) {
        this.repo = repo;
    }

    public void execute(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID é obrigatório.");
        if (!repo.exists(id)) throw new IllegalStateException("gerente não encontrado.");
        repo.delete(id);
    }
}
