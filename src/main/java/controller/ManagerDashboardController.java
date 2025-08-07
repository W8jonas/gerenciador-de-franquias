package controller;

import interfaces.ManagerDashboardView;
import repository.JsonManagerRepository;
import repository.ManagerRepository;
import useCases.manager.CreateManagerUseCase;
import useCases.manager.DeleteManagerUseCase;
import useCases.manager.ListManagersUseCase;
import useCases.manager.UpdateManagerUseCase;

public class ManagerDashboardController {

    private final ManagerDashboardView view;
    private final ManagerRepository repo;

    private final ListManagersUseCase listUC;
    private final CreateManagerUseCase createUC;
    private final UpdateManagerUseCase updateUC;
    private final DeleteManagerUseCase deleteUC;

    public ManagerDashboardController(ManagerDashboardView view) {
        this.view = view;
        this.repo = new JsonManagerRepository();

        this.listUC = new ListManagersUseCase(repo);
        this.createUC = new CreateManagerUseCase(repo);
        this.updateUC = new UpdateManagerUseCase(repo);
        this.deleteUC = new DeleteManagerUseCase(repo);
    }

    public void loadManagers() {
        String[] cols = {"ID", "Name", "Email", "Active"};
        String[][] rows = listUC.execute();
        view.onManagersLoaded(cols, rows);
        view.updateTotalCount(rows.length);
    }

    public void createManager(String name, String email, String password, boolean active) {
        try {
            createUC.execute(name, email, password, active);
            view.showInfo("Gerente criado.");
            loadManagers();
        } catch (RuntimeException ex) {
            view.showError(ex.getMessage());
        }
    }

    public void updateManager(String id, String name, String email, String password, boolean active) {
        try {
            updateUC.execute(id, name, email, password, active);
            view.showInfo("Gerente atualizado.");
            loadManagers();
        } catch (RuntimeException ex) {
            view.showError(ex.getMessage());
        }
    }

    public void deleteManager(String id) {
        try {
            deleteUC.execute(id);
            view.showInfo("Gerente removido.");
            loadManagers();
        } catch (RuntimeException ex) {
            view.showError(ex.getMessage());
        }
    }
}
