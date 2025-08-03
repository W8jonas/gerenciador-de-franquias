package controller;

import interfaces.DashboardView;
import repository.FranchiseRepository;
import repository.InMemoryFranchiseRepository;
import useCases.franchise.CreateFranchiseUseCase;
import useCases.franchise.DeleteFranchiseUseCase;
import useCases.franchise.ListFranchisesUseCase;
import useCases.franchise.UpdateFranchiseUseCase;

public class FranchiseDashboardController {

    private final DashboardView view;
    private final FranchiseRepository repo;

    private final ListFranchisesUseCase listUC;
    private final CreateFranchiseUseCase createUC;
    private final UpdateFranchiseUseCase updateUC;
    private final DeleteFranchiseUseCase deleteUC;

    public FranchiseDashboardController(DashboardView view) {
        this.view = view;
        this.repo = new InMemoryFranchiseRepository(); // simples p/ trabalho escolar

        this.listUC = new ListFranchisesUseCase(repo);
        this.createUC = new CreateFranchiseUseCase(repo);
        this.updateUC = new UpdateFranchiseUseCase(repo);
        this.deleteUC = new DeleteFranchiseUseCase(repo);
    }

    // ==== Carregamento ====
    public void loadFranchises() {
        String[] columns = {"ID", "Name", "City", "State", "Manager"};
        String[][] rows = listUC.execute(); // String-only matriz
        view.onFranchisesLoaded(columns, rows);
        view.updateTotalCount(rows.length);
    }

    // ==== CRUD ====
    public void createFranchise(String name, String street, String city, String state, String managerEmail) {
        try {
            createUC.execute(name, street, city, state, managerEmail);
            view.showInfo("Franquia criada.");
            loadFranchises();
        } catch (RuntimeException ex) {
            view.showError(ex.getMessage());
        }
    }

    public void updateFranchise(String id, String name, String street, String city, String state, String managerEmail) {
        try {
            updateUC.execute(id, name, street, city, state, managerEmail);
            view.showInfo("Franquia atualizada.");
            loadFranchises();
        } catch (RuntimeException ex) {
            view.showError(ex.getMessage());
        }
    }

    public void deleteFranchise(String id) {
        try {
            deleteUC.execute(id);
            view.showInfo("Franquia removida.");
            loadFranchises();
        } catch (RuntimeException ex) {
            view.showError(ex.getMessage());
        }
    }

    // ==== Navegação ====
    public void openFranchiseAsManager(String id) {
        view.goToManagerPanel(id);
    }
}
