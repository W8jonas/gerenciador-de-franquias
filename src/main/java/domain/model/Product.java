package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.util.Objects;

/**
 * Classe que representa um produto no sistema.
 * Contém informações básicas do produto e métodos para controle de estoque.
 */
public class Product implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private String sku;
    private double price;
    private int stockQty;

    /**
     * Construtor padrão.
     */
    public Product() {
    }

    /**
     * Construtor que inicializa todos os atributos do produto.
     * 
     * @param id Identificador único do produto
     * @param name Nome do produto
     * @param description Descrição do produto
     * @param sku Código SKU do produto
     * @param price Preço do produto
     * @param stockQty Quantidade em estoque
     * @throws IllegalArgumentException se preço ou estoque forem negativos
     */
    public Product(String id, String name, String description, String sku, double price, int stockQty) {
        if (price < 0.0) throw new IllegalArgumentException("Preço não pode ser negativo");
        if (stockQty < 0) throw new IllegalArgumentException("Quantidade não pode ser negativa");
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.stockQty = stockQty;
    }

    /**
     * Diminui a quantidade em estoque do produto.
     * 
     * @param qty Quantidade a ser subtraída do estoque
     * @throws IllegalArgumentException se a quantidade for inválida ou insuficiente
     */
    public void decreaseStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        if (stockQty - qty < 0) throw new IllegalArgumentException("Estoque insuficiente");
        stockQty -= qty;
    }

    /**
     * Aumenta a quantidade em estoque do produto.
     * 
     * @param qty Quantidade a ser adicionada ao estoque
     * @throws IllegalArgumentException se a quantidade for inválida
     */
    public void increaseStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        stockQty += qty;
    }

    /**
     * Verifica se o estoque está abaixo de um limite específico.
     * 
     * @param threshold Limite mínimo de estoque
     * @return true se o estoque estiver abaixo do limite, false caso contrário
     */
    public boolean validateStock(int threshold) {
        return stockQty < threshold;
    }

    // Getters e Setters

    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }

    public String getSku() { 
        return sku; 
    }
    
    public void setSku(String sku) { 
        this.sku = sku; 
    }

    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) {
        if (price < 0.0) throw new IllegalArgumentException("Preço não pode ser negativo");
        this.price = price;
    }

    public int getStockQty() { 
        return stockQty; 
    }
    
    public void setStockQty(int stockQty) {
        if (stockQty < 0) throw new IllegalArgumentException("Quantidade não pode ser negativa");
        this.stockQty = stockQty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", stockQty=" + stockQty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
