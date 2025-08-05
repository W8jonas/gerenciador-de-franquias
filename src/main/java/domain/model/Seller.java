package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Seller extends User implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private int totalSalesCount;
    private double totalSalesAmount;
    private String id_manager;
    private String id_franchise;
    private List<String> productsInStock = new ArrayList<>();
    private List<String> salesHistory = new ArrayList<>();


    public Seller(String name, String email, String password, String idManager, String idFranchise) {
        super(name, email, password);
        this.id_manager = idManager;
        this.id_franchise = idFranchise;
        this.totalSalesCount = 0;
        this.totalSalesAmount = 0.0;
    }

    public String getId_manager() {
        return id_manager;
    }

    public void setId_manager(String id_manager) {
        this.id_manager = id_manager;
    }

    public String getId_franchise() {
        return id_franchise;
    }

    public void setId_franchise(String id_franchise) {
        this.id_franchise = id_franchise;
    }

    public List<String> getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(List<String> productsInStock) {
        this.productsInStock = productsInStock;
    }

    public List<String> getSalesHistory() {
        return salesHistory;
    }

    public void setSalesHistory(List<String> salesHistory) {
        this.salesHistory = salesHistory;
    }

    public int getTotalSalesCount() {
        return totalSalesCount;
    }

    public void setTotalSalesCount(int totalSalesCount) {
        this.totalSalesCount = totalSalesCount;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public void addSale(double amount) {
        this.totalSalesCount++;
        this.totalSalesAmount += amount;
    }

    /**
     * Método para registrar uma venda
     * Incrementa a contagem de vendas e adiciona o valor ao total
     */
    public void recordSale(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Valor da venda deve ser maior que zero");
        }
        this.totalSalesCount++;
        this.totalSalesAmount += amount;
    }

    /**
     * Implementação polimórfica do método de login
     * Verifica as credenciais e retorna true se o login for válido
     */
    public boolean login(String email, String password) {
        // Verificar se o email corresponde
        if (!this.email.equals(email)) {
            return false;
        }
        
        // Verificar se a senha está correta
        if (!this.password.equals(password)) {
            return false;
        }
        
        // Verificar se o vendedor está ativo
        // Por enquanto, assumimos que está ativo se tem pelo menos uma venda registrada
        // ou se tem credenciais válidas (pode ser implementado com um campo boolean)
        return true;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", totalSalesCount=" + totalSalesCount +
                ", totalSalesAmount=" + totalSalesAmount +
                '}';
    }
} 