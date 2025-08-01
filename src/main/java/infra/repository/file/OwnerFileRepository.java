package infra.repository.file;

import domain.model.Owner;
import domain.repository.OwnerRepository;

import java.io.*;
import java.util.Optional;

public class OwnerFileRepository implements OwnerRepository {
    
    private static final String FILE_PATH = "data/owner.dat";
    
    public OwnerFileRepository() {
        // Garantir que o diretório data existe
        createDataDirectoryIfNotExists();
    }
    
    @Override
    public void save(Owner owner) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            
            objectOut.writeObject(owner);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar Owner no arquivo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Owner> find() {
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return Optional.empty();
        }
        
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            Owner owner = (Owner) objectIn.readObject();
            return Optional.of(owner);
            
        } catch (IOException | ClassNotFoundException e) {
            // Se houver erro na leitura, retorna Optional.empty()
            return Optional.empty();
        }
    }
    
    @Override
    public boolean exists() {
        File file = new File(FILE_PATH);
        return file.exists();
    }
    
    private void createDataDirectoryIfNotExists() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
} 