package infra.repository.file;

import domain.model.Franchise;
import domain.repository.FranchiseRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do FranchiseRepository que utiliza arquivos para persistência.
 * Armazena as franquias em um arquivo serializado.
 */
public class FranchiseFileRepository implements FranchiseRepository {
    
    private static final String FILE_PATH = "data/franchises.dat";
    
    public FranchiseFileRepository() {
        createDataDirectoryIfNotExists();
    }
    
    @Override
    public void save(Franchise franchise) {
        if (franchise == null) {
            throw new IllegalArgumentException("Franchise não pode ser null");
        }
        
        try {
            List<Franchise> franchises = loadFranchisesFromFile();
            
            // Verificar se a franquia já existe para atualizar
            boolean found = false;
            for (int i = 0; i < franchises.size(); i++) {
                if (franchises.get(i).getId().equals(franchise.getId())) {
                    franchises.set(i, franchise); // Atualizar franquia existente
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                franchises.add(franchise); // Adicionar nova franquia
            }
            
            saveFranchisesToFile(franchises);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar franquia: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Franchise> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        
        try {
            List<Franchise> franchises = loadFranchisesFromFile();
            
            return franchises.stream()
                    .filter(franchise -> franchise.getId().equals(id))
                    .findFirst();
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar franquia por ID: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Franchise> findAll() {
        try {
            return loadFranchisesFromFile();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar todas as franquias: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            return; // Não fazer nada se o ID for inválido
        }
        
        try {
            List<Franchise> franchises = loadFranchisesFromFile();
            
            // Remover franquia com o ID especificado
            franchises.removeIf(franchise -> franchise.getId().equals(id));
            
            saveFranchisesToFile(franchises);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar franquia: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean existsById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        try {
            List<Franchise> franchises = loadFranchisesFromFile();
            
            return franchises.stream()
                    .anyMatch(franchise -> franchise.getId().equals(id));
                    
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar existência da franquia: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carrega a lista de franquias do arquivo.
     * Se o arquivo não existir, retorna uma lista vazia.
     */
    @SuppressWarnings("unchecked")
    private List<Franchise> loadFranchisesFromFile() throws IOException {
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<Franchise>) obj;
            } else {
                return new ArrayList<>();
            }
            
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao desserializar franquias: " + e.getMessage(), e);
        }
    }
    
    /**
     * Salva a lista de franquias no arquivo.
     */
    private void saveFranchisesToFile(List<Franchise> franchises) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(franchises);
            
        }
    }
    
    /**
     * Cria o diretório de dados se não existir.
     */
    private void createDataDirectoryIfNotExists() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            if (!dataDir.mkdirs()) {
                throw new RuntimeException("Não foi possível criar o diretório de dados");
            }
        }
    }
} 