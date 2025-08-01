package infra.repository.file;

import domain.model.Owner;
import domain.repository.OwnerRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Optional;

public class OwnerFileRepositoryTest {
    
    private OwnerRepository repository;
    private static final String TEST_FILE_PATH = "data/owner.dat";
    
    @Before
    public void setUp() {
        repository = new OwnerFileRepository();
        // Limpar arquivo de teste se existir
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testSaveAndFind() {
        // Criar um Owner
        Owner owner = new Owner("João Silva", "joao@empresa.com", "123456");
        
        // Salvar o Owner
        repository.save(owner);
        
        // Verificar se existe
        assertTrue(repository.exists());
        
        // Buscar o Owner
        Optional<Owner> foundOwner = repository.find();
        assertTrue(foundOwner.isPresent());
        assertEquals("João Silva", foundOwner.get().getName());
        assertEquals("joao@empresa.com", foundOwner.get().getEmail());
    }
    
    @Test
    public void testExistsWhenFileDoesNotExist() {
        // Verificar se não existe quando arquivo não foi criado
        assertFalse(repository.exists());
        
        // Buscar deve retornar Optional.empty()
        Optional<Owner> foundOwner = repository.find();
        assertFalse(foundOwner.isPresent());
    }
    
    @Test
    public void testUpdateOwner() {
        // Criar e salvar primeiro Owner
        Owner owner1 = new Owner("João Silva", "joao@empresa.com", "123456");
        repository.save(owner1);
        
        // Criar e salvar segundo Owner (deve sobrescrever o primeiro)
        Owner owner2 = new Owner("Maria Silva", "maria@empresa.com", "654321");
        repository.save(owner2);
        
        // Verificar se foi atualizado
        Optional<Owner> foundOwner = repository.find();
        assertTrue(foundOwner.isPresent());
        assertEquals("Maria Silva", foundOwner.get().getName());
        assertEquals("maria@empresa.com", foundOwner.get().getEmail());
    }
} 