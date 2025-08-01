package domain.repository;

import domain.model.Owner;
import java.util.Optional;

public interface OwnerRepository {
    /**
     * Salva o objeto Owner no repositório
     * @param owner O objeto Owner a ser salvo
     */
    void save(Owner owner);
    
    /**
     * Busca o único Owner no sistema
     * @return Optional contendo o Owner se existir, ou Optional.empty() se não existir
     */
    Optional<Owner> find();
    
    /**
     * Verifica se já existe um Owner cadastrado no sistema
     * @return true se existe um Owner, false caso contrário
     */
    boolean exists();
} 