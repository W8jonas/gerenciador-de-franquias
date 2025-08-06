package domain.repository;

import domain.model.Manager;
import java.util.List;
import java.util.Optional;

/**
 * Interface para gerenciamento de gerentes no sistema.
 * Define os métodos básicos de CRUD para entidade Manager.
 */
public interface ManagerRepository {
    
    /**
     * Salva ou atualiza um gerente no repositório.
     * Se o gerente já existir (mesmo ID), será atualizado.
     * Se não existir, será criado um novo registro.
     * 
     * @param manager O gerente a ser salvo ou atualizado
     */
    void save(Manager manager);
    
    /**
     * Busca um gerente pelo seu ID.
     * 
     * @param id O ID do gerente a ser buscado
     * @return Optional contendo o gerente se encontrado, ou Optional.empty() se não encontrado
     */
    Optional<Manager> findById(String id);
    
    /**
     * Busca todos os gerentes cadastrados no sistema.
     * 
     * @return Lista com todos os gerentes cadastrados
     */
    List<Manager> findAll();
    
    /**
     * Remove um gerente pelo seu ID.
     * 
     * @param id O ID do gerente a ser removido
     */
    void delete(String id);
    
    /**
     * Verifica se existe um gerente com o ID especificado.
     * 
     * @param id O ID do gerente a ser verificado
     * @return true se o gerente existe, false caso contrário
     */
    boolean existsById(String id);
} 