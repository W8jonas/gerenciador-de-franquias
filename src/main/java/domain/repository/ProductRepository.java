package domain.repository;

import domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Interface para gerenciamento de produtos no sistema.
 * Define os métodos básicos de CRUD para entidade Product.
 */
public interface ProductRepository {
    
    /**
     * Salva ou atualiza um produto no repositório.
     * Se o produto já existir (mesmo ID), será atualizado.
     * Se não existir, será criado um novo registro.
     * 
     * @param product O produto a ser salvo ou atualizado
     */
    void save(Product product);
    
    /**
     * Busca um produto pelo seu ID.
     * 
     * @param id O ID do produto a ser buscado
     * @return Optional contendo o produto se encontrado, ou Optional.empty() se não encontrado
     */
    Optional<Product> findById(String id);
    
    /**
     * Busca todos os produtos cadastrados no sistema.
     * 
     * @return Lista com todos os produtos cadastrados
     */
    List<Product> findAll();
    
    /**
     * Remove um produto pelo seu ID.
     * 
     * @param id O ID do produto a ser removido
     */
    void delete(String id);
    
    /**
     * Verifica se existe um produto com o ID especificado.
     * 
     * @param id O ID do produto a ser verificado
     * @return true se o produto existe, false caso contrário
     */
    boolean existsById(String id);
} 