package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Seller implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Seller> assignedSellers;

    public Manager(String name, String email, String password) {
        super(name, email, password);
        this.assignedSellers = new ArrayList<>();
    }

    public void assignSeller(Seller seller) {
        if (seller != null && !assignedSellers.contains(seller)) {
            assignedSellers.add(seller);
        }
    }

    public void removeSeller(Seller seller) {
        assignedSellers.remove(seller);
    }

    public List<Seller> getAssignedSellers() {
        return new ArrayList<>(assignedSellers);
    }

    public void editOrder(String orderId) {
        // Implementação será adicionada quando a classe Order for criada
        // Por enquanto, apenas um placeholder
    }
} 