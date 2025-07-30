package domain.model;

import java.util.ArrayList;
import java.util.List;

public class Owner extends Manager {
    // TODO: Implementar quando a classe Franchise for criada
    // private List<Franchise> franchises;

    public Owner(String name, String email, String password) {
        super(name, email, password);
        // this.franchises = new ArrayList<>();
    }

    public void createFranchise(String name, Address address, Manager manager) {
        // TODO: Implementar quando a classe Franchise for criada
        // Franchise franchise = new Franchise(name, address);
        // franchises.add(franchise);
    }

    public void removeFranchise(String franchiseId) {
        // TODO: Implementar quando a classe Franchise for criada
        // franchises.remove(franchise);
    }

    public void reassignManager(String franchiseId, Manager manager) {
        // TODO: Implementar quando a classe Franchise for criada
    }

    public List<String> getFranchises() {
        // TODO: Implementar quando a classe Franchise for criada
        return new ArrayList<>();
    }
}
