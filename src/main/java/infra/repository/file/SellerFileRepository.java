package infra.repository.file;

import domain.repository.SellerRepository;
import domain.model.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação de SellerRepository que utiliza arquivo para persistência.
 * Usa serialização de objetos para armazenar e recuperar dados dos vendedores.
 */
public class SellerFileRepository implements SellerRepository {
    
    private static final String FILE_PATH = "data/sellers.dat";
    
    /**
     * Salva ou atualiza um vendedor no arquivo.
     * Se o vendedor já existir (mesmo id), será atualizado.
     * Se não existir, será adicionado à lista.
     * 
     * @param seller O vendedor a ser salvo ou atualizado
     */
    @Override
    public void save(Seller seller) {
        try {
            List<Seller> sellers = loadSellers();
            
            // Procura por um vendedor existente com o mesmo id
            boolean found = false;
            for (int i = 0; i < sellers.size(); i++) {
                if (sellers.get(i).getId().equals(seller.getId())) {
                    sellers.set(i, seller);
                    found = true;
                    break;
                }
            }
            
            // Se não encontrou, adiciona novo
            if (!found) {
                sellers.add(seller);
            }
            
            saveSellers(sellers);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar vendedor: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um vendedor pelo seu ID.
     * 
     * @param id O ID do vendedor a ser buscado
     * @return Optional contendo o vendedor se encontrado, ou Optional.empty() se não encontrado
     */
    @Override
    public Optional<Seller> findById(String id) {
        try {
            List<Seller> sellers = loadSellers();
            
            return sellers.stream()
                    .filter(seller -> seller.getId().equals(id))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar vendedor: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca todos os vendedores cadastrados no arquivo.
     * 
     * @return Lista com todos os vendedores cadastrados
     */
    @Override
    public List<Seller> findAll() {
        try {
            return loadSellers();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar todos os vendedores: " + e.getMessage(), e);
        }
    }
    
    /**
     * Remove um vendedor pelo seu ID.
     * 
     * @param id O ID do vendedor a ser removido
     */
    @Override
    public void delete(String id) {
        try {
            List<Seller> sellers = loadSellers();
            
            sellers.removeIf(seller -> seller.getId().equals(id));
            
            saveSellers(sellers);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover vendedor: " + e.getMessage(), e);
        }
    }
    
    /**
     * Verifica se existe um vendedor com o ID especificado.
     * 
     * @param id O ID do vendedor a ser verificado
     * @return true se o vendedor existe, false caso contrário
     */
    @Override
    public boolean existsById(String id) {
        try {
            List<Seller> sellers = loadSellers();
            
            return sellers.stream()
                    .anyMatch(seller -> seller.getId().equals(id));
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar existência do vendedor: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um vendedor pelo seu email.
     * Método adicional para facilitar a busca por email.
     * 
     * @param email O email do vendedor a ser buscado
     * @return Optional contendo o vendedor se encontrado, ou Optional.empty() se não encontrado
     */
    public Optional<Seller> findByEmail(String email) {
        try {
            List<Seller> sellers = loadSellers();
            
            return sellers.stream()
                    .filter(seller -> seller.getEmail().equals(email))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar vendedor por email: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carrega a lista de vendedores do arquivo.
     * Se o arquivo não existir, retorna uma lista vazia.
     * 
     * @return Lista de vendedores carregada do arquivo
     * @throws IOException Se houver erro na leitura do arquivo
     */
    @SuppressWarnings("unchecked")
    private List<Seller> loadSellers() throws IOException {
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Seller>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao desserializar dados dos vendedores", e);
        }
    }
    
    /**
     * Salva a lista de vendedores no arquivo.
     * 
     * @param sellers Lista de vendedores a ser salva
     * @throws IOException Se houver erro na escrita do arquivo
     */
    private void saveSellers(List<Seller> sellers) throws IOException {
        // Garante que o diretório existe
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(sellers);
        }
    }
} 