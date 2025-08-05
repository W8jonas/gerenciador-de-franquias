package domain.repository;

import domain.model.Seller;
import java.util.List;
import java.util.Optional;

/**
 * Interface para gerenciamento de vendedores no sistema.
 * Define os métodos básicos de CRUD para entidade Seller.
 */
public interface SellerRepository {
    
    /**
     * Salva ou atualiza um vendedor no repositório.
     * Se o vendedor já existir (mesmo ID), será atualizado.
     * Se não existir, será criado um novo registro.
     * 
     * @param seller O vendedor a ser salvo ou atualizado
     */
    void save(Seller seller);
    
    /**
     * Busca um vendedor pelo seu ID.
     * 
     * @param id O ID do vendedor a ser buscado
     * @return Optional contendo o vendedor se encontrado, ou Optional.empty() se não encontrado
     */
    Optional<Seller> findById(String id);
    
    /**
     * Busca todos os vendedores cadastrados no sistema.
     * 
     * @return Lista com todos os vendedores cadastrados
     */
    List<Seller> findAll();
    
    /**
     * Remove um vendedor pelo seu ID.
     * 
     * @param id O ID do vendedor a ser removido
     */
    void delete(String id);
    
    /**
     * Verifica se existe um vendedor com o ID especificado.
     * 
     * @param id O ID do vendedor a ser verificado
     * @return true se o vendedor existe, false caso contrário
     */
    boolean existsById(String id);
} 