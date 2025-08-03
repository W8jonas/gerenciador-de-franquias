package useCases.franchise;

import repository.FranchiseRepository;

public class ListFranchisesUseCase {
    private final FranchiseRepository repo;

    public ListFranchisesUseCase(FranchiseRepository repo) {
        this.repo = repo;
    }

    public String[][] execute() {
        return repo.findAllAsTable();
    }
}
