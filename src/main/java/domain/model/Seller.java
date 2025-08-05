package domain.model;

import java.io.Serializable;
import java.io.Serial;

public class Seller extends User implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private int totalSalesCount;
    private double totalSalesAmount;

    public Seller(String name, String email, String password) {
        super(name, email, password);
        this.totalSalesCount = 0;
        this.totalSalesAmount = 0.0;
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