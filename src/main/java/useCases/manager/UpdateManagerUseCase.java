package useCases.manager;

import repository.ManagerRepository;

public class UpdateManagerUseCase {
    private final ManagerRepository repo;

    public UpdateManagerUseCase(ManagerRepository repo) {
        this.repo = repo;
    }

    public void execute(String id, String name, String email, String password, boolean active) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID é obrigatório.");
        if (!repo.exists(id)) throw new IllegalStateException("Gerente não encontrado.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório.");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Senha é obrigatório.");

        repo.update(id, name, email, password, active);
    }
}
