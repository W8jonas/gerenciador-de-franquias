package application.service;

import domain.model.Franchise;
import domain.model.Address;
import domain.model.Manager;
import domain.repository.FranchiseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço que contém a lógica de negócio para operações com Franchise.
 * Utiliza o padrão de injeção de dependência para receber o repositório.
 */
public class FranchiseService {
    
    private final FranchiseRepository franchiseRepository;
    
    /**
     * Construtor que recebe a dependência do repositório via injeção.
     * 
     * @param franchiseRepository O repositório de franquias
     */
    public FranchiseService(FranchiseRepository franchiseRepository) {
        if (franchiseRepository == null) {
            throw new IllegalArgumentException("FranchiseRepository não pode ser null");
        }
        this.franchiseRepository = franchiseRepository;
    }
    
    /**
     * Cria uma nova franquia com dados validados.
     * 
     * @param name Nome da franquia (não pode ser vazio)
     * @param address Endereço da franquia (não pode ser null)
     * @param manager Gerente responsável pela franquia (pode ser null)
     * @return A franquia criada
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public Franchise createFranchise(String name, Address address, Manager manager) {
        // Validação dos dados de entrada
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da franquia não pode ser vazio");
        }
        
        if (address == null) {
            throw new IllegalArgumentException("Endereço da franquia não pode ser null");
        }
        
        // Gerar ID único para a nova franquia
        String franchiseId = generateUniqueId();
        
        // Criar a nova franquia
        Franchise franchise = new Franchise(franchiseId, name.trim(), address);
        
        // Definir o gerente se fornecido
        if (manager != null) {
            franchise.setManager(manager);
        }
        
        // Salvar no repositório
        franchiseRepository.save(franchise);
        
        return franchise;
    }
    
    /**
     * Atualiza uma franquia existente.
     * 
     * @param franchise A franquia a ser atualizada
     * @throws IllegalArgumentException se a franquia for null ou não existir
     */
    public void updateFranchise(Franchise franchise) {
        if (franchise == null) {
            throw new IllegalArgumentException("Franchise não pode ser null");
        }
        
        if (franchise.getId() == null || franchise.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("ID da franquia não pode ser vazio");
        }
        
        // Verificar se a franquia existe antes de atualizar
        if (!franchiseRepository.existsById(franchise.getId())) {
            throw new IllegalArgumentException("Franquia com ID " + franchise.getId() + " não encontrada");
        }
        
        // Salvar a atualização
        franchiseRepository.save(franchise);
    }
    
    /**
     * Remove uma franquia pelo seu ID.
     * 
     * @param id ID da franquia a ser removida
     * @throws IllegalArgumentException se o ID for inválido ou a franquia não existir
     */
    public void deleteFranchise(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da franquia não pode ser vazio");
        }
        
        // Verificar se a franquia existe antes de deletar
        if (!franchiseRepository.existsById(id)) {
            throw new IllegalArgumentException("Franquia com ID " + id + " não encontrada");
        }
        
        // Remover a franquia
        franchiseRepository.delete(id);
    }
    
    /**
     * Busca uma franquia pelo seu ID.
     * 
     * @param id ID da franquia a ser buscada
     * @return A franquia encontrada
     * @throws IllegalArgumentException se o ID for inválido
     * @throws IllegalStateException se a franquia não for encontrada
     */
    public Franchise getFranchiseById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da franquia não pode ser vazio");
        }
        
        Optional<Franchise> franchise = franchiseRepository.findById(id);
        
        if (franchise.isEmpty()) {
            throw new IllegalStateException("Franquia com ID " + id + " não encontrada");
        }
        
        return franchise.get();
    }
    
    /**
     * Busca todas as franquias cadastradas.
     * 
     * @return Lista com todas as franquias
     */
    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }
    
    /**
     * Verifica se uma franquia existe pelo seu ID.
     * 
     * @param id ID da franquia a ser verificada
     * @return true se a franquia existe, false caso contrário
     */
    public boolean franchiseExists(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return franchiseRepository.existsById(id);
    }
    
    /**
     * Gera um ID único para novas franquias.
     * 
     * @return ID único no formato "FRANCHISE_" + UUID
     */
    private String generateUniqueId() {
        return "FRANCHISE_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 