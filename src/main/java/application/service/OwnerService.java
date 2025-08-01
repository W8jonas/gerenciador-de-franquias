package application.service;

import domain.model.Owner;
import domain.repository.OwnerRepository;

import java.util.Optional;

public class OwnerService {
    
    private final OwnerRepository ownerRepository;
    
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    
    /**
     * Cria o primeiro e único Owner do sistema
     * @param id ID do Owner
     * @param name Nome do Owner
     * @param email Email do Owner
     * @param password Senha do Owner
     * @return O Owner criado
     * @throws IllegalStateException se já existir um Owner no sistema
     */
    public Owner createFirstOwner(String id, String name, String email, String password) {
        // Verificar se já existe um Owner
        if (ownerRepository.exists()) {
            throw new IllegalStateException("Já existe um Owner cadastrado no sistema. Apenas um Owner é permitido.");
        }
        
        // Criar novo Owner
        Owner owner = new Owner(name, email, password);
        
        // Salvar no repositório
        ownerRepository.save(owner);
        
        return owner;
    }
    
    /**
     * Atualiza as informações do Owner existente
     * @param owner O Owner com as informações atualizadas
     * @return O Owner atualizado
     * @throws IllegalStateException se não existir um Owner para atualizar
     */
    public Owner updateOwner(Owner owner) {
        // Verificar se existe um Owner para atualizar
        if (!ownerRepository.exists()) {
            throw new IllegalStateException("Não existe um Owner cadastrado para ser atualizado.");
        }
        
        // Salvar as atualizações no repositório
        ownerRepository.save(owner);
        
        return owner;
    }
    
    /**
     * Busca o Owner do sistema
     * @return O Owner encontrado
     * @throws IllegalStateException se não existir um Owner cadastrado
     */
    public Owner getOwner() {
        Optional<Owner> ownerOptional = ownerRepository.find();
        
        if (ownerOptional.isEmpty()) {
            throw new IllegalStateException("Não existe um Owner cadastrado no sistema.");
        }
        
        return ownerOptional.get();
    }
    
    /**
     * Verifica se existe um Owner cadastrado no sistema
     * @return true se existe um Owner, false caso contrário
     */
    public boolean ownerExists() {
        return ownerRepository.exists();
    }
} 