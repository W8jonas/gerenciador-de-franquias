package infra.repository.file;

import domain.repository.SellerRepository;
import domain.model.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação de SellerRepository que utiliza arquivo JSON para persistência.
 * Usa a biblioteca Gson para serializar e desserializar dados dos vendedores.
 */
public class JsonSellerRepository implements SellerRepository {
    
    private static final String FILE_PATH = "data/seller.json";
    private final Gson gson = new Gson();
    
    /**
     * Salva ou atualiza um vendedor no arquivo JSON.
     * Se o vendedor já existir (mesmo id), será atualizado.
     * Se não existir, será adicionado à lista.
     * 
     * @param seller O vendedor a ser salvo ou atualizado
     */
    @Override
    public void save(Seller seller) {
        try {
            if (seller == null) {
                throw new IllegalArgumentException("Vendedor não pode ser nulo");
            }
            
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
            if (id == null || id.trim().isEmpty()) {
                return Optional.empty();
            }
            
            List<Seller> sellers = loadSellers();
            
            return sellers.stream()
                    .filter(seller -> seller.getId().equals(id))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar vendedor: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca todos os vendedores cadastrados no arquivo JSON.
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
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
            }
            
            List<Seller> sellers = loadSellers();
            
            boolean removed = sellers.removeIf(seller -> seller.getId().equals(id));
            
            if (removed) {
                saveSellers(sellers);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar vendedor: " + e.getMessage(), e);
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
            if (id == null || id.trim().isEmpty()) {
                return false;
            }
            
            List<Seller> sellers = loadSellers();
            
            return sellers.stream()
                    .anyMatch(seller -> seller.getId().equals(id));
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar existência do vendedor: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carrega a lista de vendedores do arquivo JSON.
     * Se o arquivo não existir, retorna uma lista vazia.
     * 
     * @return Lista de vendedores carregada do arquivo
     * @throws IOException Se houver erro na leitura do arquivo
     */
    private List<Seller> loadSellers() throws IOException {
        java.io.File file = new java.io.File(FILE_PATH);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            
            String jsonContent = content.toString().trim();
            if (jsonContent.isEmpty()) {
                return new ArrayList<>();
            }
            
            Type listType = new TypeToken<List<Seller>>() {}.getType();
            return gson.fromJson(jsonContent, listType);
            
        } catch (Exception e) {
            throw new IOException("Erro ao desserializar dados dos vendedores", e);
        }
    }
    
    /**
     * Salva a lista de vendedores no arquivo JSON.
     * 
     * @param sellers Lista de vendedores a ser salva
     * @throws IOException Se houver erro na escrita do arquivo
     */
    private void saveSellers(List<Seller> sellers) throws IOException {
        // Garante que o diretório existe
        java.io.File file = new java.io.File(FILE_PATH);
        java.io.File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        String json = gson.toJson(sellers);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
        }
    }
} 