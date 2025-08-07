package useCases.manager;

import repository.ManagerRepository;

import java.util.UUID;

public class CreateManagerUseCase {
    private final ManagerRepository repo;

    public CreateManagerUseCase(ManagerRepository repo) {
        this.repo = repo;
    }

    public String execute(String name, String email, String password, boolean active) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório.");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Senha é obrigatório.");
        if (repo.existsByEmail(email)) throw new IllegalStateException("Email já em uso.");

        String id = UUID.randomUUID().toString();
        repo.save(id, name, email, password, active);
        return id;
    }
}
