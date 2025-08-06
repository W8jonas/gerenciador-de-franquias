package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.util.Objects;

/**
 * Classe que representa um item de pedido no sistema.
 * Contém informações sobre o produto, quantidade e preço unitário.
 */
public class OrderItem implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String orderId;
    private Product product;
    private int quantity;
    private double unitPrice;

    /**
     * Construtor padrão.
     */
    public OrderItem() {
    }

    /**
     * Construtor que inicializa todos os atributos do item de pedido.
     * 
     * @param id Identificador único do item
     * @param orderId Identificador do pedido
     * @param product Produto do item
     * @param quantity Quantidade do item
     * @param unitPrice Preço unitário do item
     * @throws IllegalArgumentException se quantidade ou preço forem inválidos
     */
    public OrderItem(String id, String orderId, Product product, int quantity, double unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        if (unitPrice < 0.0) throw new IllegalArgumentException("Preço unitário deve ser maior que 0");
        this.id = id;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Calcula e retorna o preço total do item (quantidade * preço unitário).
     * 
     * @return O preço total do item
     */
    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    // Getters e Setters

    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }

    public String getOrderId() { 
        return orderId; 
    }
    
    public void setOrderId(String orderId) { 
        this.orderId = orderId; 
    }

    public Product getProduct() { 
        return product; 
    }
    
    public void setProduct(Product product) { 
        this.product = product; 
    }

    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        this.quantity = quantity;
    }

    public double getUnitPrice() { 
        return unitPrice; 
    }
    
    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0.0) throw new IllegalArgumentException("Preço unitário deve ser maior que 0");
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
