package infra.repository.file;

import domain.repository.ManagerRepository;
import domain.model.Manager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação de ManagerRepository que utiliza arquivo JSON para persistência.
 * Usa a biblioteca Gson para serializar e desserializar dados dos gerentes.
 */
public class JsonManagerRepository implements ManagerRepository {
    
    private static final String FILE_PATH = "data/manager.json";
    private final Gson gson = new Gson();
    
    /**
     * Salva ou atualiza um gerente no arquivo JSON.
     * Se o gerente já existir (mesmo id), será atualizado.
     * Se não existir, será adicionado à lista.
     * 
     * @param manager O gerente a ser salvo ou atualizado
     */
    @Override
    public void save(Manager manager) {
        try {
            if (manager == null) {
                throw new IllegalArgumentException("Gerente não pode ser nulo");
            }
            
            List<Manager> managers = loadManagers();
            
            // Procura por um gerente existente com o mesmo id
            boolean found = false;
            for (int i = 0; i < managers.size(); i++) {
                if (managers.get(i).getId().equals(manager.getId())) {
                    managers.set(i, manager);
                    found = true;
                    break;
                }
            }
            
            // Se não encontrou, adiciona novo
            if (!found) {
                managers.add(manager);
            }
            
            saveManagers(managers);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar gerente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca um gerente pelo seu ID.
     * 
     * @param id O ID do gerente a ser buscado
     * @return Optional contendo o gerente se encontrado, ou Optional.empty() se não encontrado
     */
    @Override
    public Optional<Manager> findById(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return Optional.empty();
            }
            
            List<Manager> managers = loadManagers();
            
            return managers.stream()
                    .filter(manager -> manager.getId().equals(id))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar gerente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca todos os gerentes cadastrados no arquivo JSON.
     * 
     * @return Lista com todos os gerentes cadastrados
     */
    @Override
    public List<Manager> findAll() {
        try {
            return loadManagers();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar todos os gerentes: " + e.getMessage(), e);
        }
    }
    
    /**
     * Remove um gerente pelo seu ID.
     * 
     * @param id O ID do gerente a ser removido
     */
    @Override
    public void delete(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
            }
            
            List<Manager> managers = loadManagers();
            
            boolean removed = managers.removeIf(manager -> manager.getId().equals(id));
            
            if (removed) {
                saveManagers(managers);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar gerente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Verifica se existe um gerente com o ID especificado.
     * 
     * @param id O ID do gerente a ser verificado
     * @return true se o gerente existe, false caso contrário
     */
    @Override
    public boolean existsById(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return false;
            }
            
            List<Manager> managers = loadManagers();
            
            return managers.stream()
                    .anyMatch(manager -> manager.getId().equals(id));
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar existência do gerente: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carrega a lista de gerentes do arquivo JSON.
     * Se o arquivo não existir, retorna uma lista vazia.
     * 
     * @return Lista de gerentes carregada do arquivo
     * @throws IOException Se houver erro na leitura do arquivo
     */
    private List<Manager> loadManagers() throws IOException {
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
            
            Type listType = new TypeToken<List<Manager>>() {}.getType();
            return gson.fromJson(jsonContent, listType);
            
        } catch (Exception e) {
            throw new IOException("Erro ao desserializar dados dos gerentes", e);
        }
    }
    
    /**
     * Salva a lista de gerentes no arquivo JSON.
     * 
     * @param managers Lista de gerentes a ser salva
     * @throws IOException Se houver erro na escrita do arquivo
     */
    private void saveManagers(List<Manager> managers) throws IOException {
        // Garante que o diretório existe
        java.io.File file = new java.io.File(FILE_PATH);
        java.io.File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        String json = gson.toJson(managers);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
        }
    }
} 