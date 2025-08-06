package infra.repository.file;

import domain.repository.ProductRepository;
import domain.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação de ProductRepository que utiliza arquivo para persistência.
 * Usa serialização de objetos para armazenar e recuperar dados dos produtos.
 */
public class ProductFileRepository implements ProductRepository {
    
    private static final String FILE_PATH = "data/products.dat";
    
    /**
     * Salva ou atualiza um produto no arquivo.
     * Se o produto já existir (mesmo id), será atualizado.
     * Se não existir, será adicionado à lista.
     * 
     * @param product O produto a ser salvo ou atualizado
     */
    @Override
    public void save(Product product) {
        try {
            List<Product> products = loadProducts();
            
            // Procura por um produto existente com o mesmo id
            boolean found = false;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(product.getId())) {
                    products.set(i, product);
                    found = true;
                    break;
                }
            }
            
            // Se não encontrou, adiciona novo
            if (!found) {
                products.add(product);
            }
            
            saveProducts(products);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um produto pelo seu ID.
     * 
     * @param id O ID do produto a ser buscado
     * @return Optional contendo o produto se encontrado, ou Optional.empty() se não encontrado
     */
    @Override
    public Optional<Product> findById(String id) {
        try {
            List<Product> products = loadProducts();
            
            return products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca todos os produtos cadastrados no arquivo.
     * 
     * @return Lista com todos os produtos cadastrados
     */
    @Override
    public List<Product> findAll() {
        try {
            return loadProducts();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Remove um produto pelo seu ID.
     * 
     * @param id O ID do produto a ser removido
     */
    @Override
    public void delete(String id) {
        try {
            List<Product> products = loadProducts();
            
            products.removeIf(product -> product.getId().equals(id));
            
            saveProducts(products);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover produto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Verifica se existe um produto com o ID especificado.
     * 
     * @param id O ID do produto a ser verificado
     * @return true se o produto existe, false caso contrário
     */
    @Override
    public boolean existsById(String id) {
        try {
            List<Product> products = loadProducts();
            
            return products.stream()
                    .anyMatch(product -> product.getId().equals(id));
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar existência do produto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um produto pelo seu SKU.
     * Método adicional para facilitar a busca por SKU.
     * 
     * @param sku O SKU do produto a ser buscado
     * @return Optional contendo o produto se encontrado, ou Optional.empty() se não encontrado
     */
    public Optional<Product> findBySku(String sku) {
        try {
            List<Product> products = loadProducts();
            
            return products.stream()
                    .filter(product -> product.getSku().equals(sku))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar produto por SKU: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca produtos com estoque baixo.
     * Método adicional para facilitar a busca de produtos com estoque abaixo do limite.
     * 
     * @param threshold Limite mínimo de estoque
     * @return Lista de produtos com estoque abaixo do limite
     */
    public List<Product> findLowStockProducts(int threshold) {
        try {
            List<Product> products = loadProducts();
            
            return products.stream()
                    .filter(product -> product.validateStock(threshold))
                    .toList();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar produtos com estoque baixo: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carrega a lista de produtos do arquivo.
     * Se o arquivo não existir, retorna uma lista vazia.
     * 
     * @return Lista de produtos carregada do arquivo
     * @throws IOException Se houver erro na leitura do arquivo
     */
    @SuppressWarnings("unchecked")
    private List<Product> loadProducts() throws IOException {
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Product>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao desserializar dados dos produtos", e);
        }
    }
    
    /**
     * Salva a lista de produtos no arquivo.
     * 
     * @param products Lista de produtos a ser salva
     * @throws IOException Se houver erro na escrita do arquivo
     */
    private void saveProducts(List<Product> products) throws IOException {
        // Garante que o diretório existe
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(products);
        }
    }
} 