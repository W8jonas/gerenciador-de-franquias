package domain.model;

public class Seller extends User {
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
} 