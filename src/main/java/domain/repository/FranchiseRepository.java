package domain.repository;

import domain.model.Franchise;
import java.util.List;
import java.util.Optional;

/**
 * Interface que define o contrato para persistência de dados de Franchise.
 * Segue o padrão Repository para abstrair a lógica de acesso a dados.
 */
public interface FranchiseRepository {
    
    /**
     * Salva ou atualiza uma franquia no repositório.
     * Se a franquia já existir (mesmo ID), atualiza os dados.
     * Se não existir, cria uma nova franquia.
     * 
     * @param franchise A franquia a ser salva/atualizada
     */
    void save(Franchise franchise);
    
    /**
     * Busca uma franquia pelo seu ID.
     * 
     * @param id O ID da franquia a ser buscada
     * @return Um Optional contendo a franquia se encontrada, ou Optional.empty() se não encontrada
     */
    Optional<Franchise> findById(String id);
    
    /**
     * Busca todas as franquias cadastradas no sistema.
     * 
     * @return Uma lista com todas as franquias, ou lista vazia se não houver franquias
     */
    List<Franchise> findAll();
    
    /**
     * Remove uma franquia do repositório pelo seu ID.
     * 
     * @param id O ID da franquia a ser removida
     */
    void delete(String id);
    
    /**
     * Verifica se uma franquia existe no repositório pelo seu ID.
     * 
     * @param id O ID da franquia a ser verificada
     * @return true se a franquia existe, false caso contrário
     */
    boolean existsById(String id);
} 