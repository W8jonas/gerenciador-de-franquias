package useCases.manager;

import repository.ManagerRepository;

public class ListManagersUseCase {
    private final ManagerRepository repo;

    public ListManagersUseCase(ManagerRepository repo) {
        this.repo = repo;
    }

    public String[][] execute() {
        return repo.findAllAsTable();
    }
}
