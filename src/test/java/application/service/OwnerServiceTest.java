package application.service;

import domain.model.Owner;
import domain.repository.OwnerRepository;
import infra.repository.file.OwnerFileRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Optional;

public class OwnerServiceTest {
    
    private OwnerService ownerService;
    private OwnerRepository repository;
    private static final String TEST_FILE_PATH = "data/owner.dat";
    
    @Before
    public void setUp() {
        repository = new OwnerFileRepository();
        ownerService = new OwnerService(repository);
        
        // Limpar arquivo de teste se existir
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testCreateFirstOwnerSuccess() {
        // Act
        Owner owner = ownerService.createFirstOwner("OWNER001", "João Silva", "joao@empresa.com", "123456");
        
        // Assert
        assertNotNull(owner);
        assertEquals("João Silva", owner.getName());
        assertEquals("joao@empresa.com", owner.getEmail());
        assertTrue(ownerService.ownerExists());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCreateFirstOwnerWhenOwnerAlreadyExists() {
        // Arrange - Criar primeiro Owner
        ownerService.createFirstOwner("OWNER001", "João Silva", "joao@empresa.com", "123456");
        
        // Act & Assert - Tentar criar segundo Owner (deve falhar)
        ownerService.createFirstOwner("OWNER002", "Maria Silva", "maria@empresa.com", "654321");
    }
    
    @Test
    public void testUpdateOwnerSuccess() {
        // Arrange - Criar Owner primeiro
        Owner originalOwner = ownerService.createFirstOwner("OWNER001", "João Silva", "joao@empresa.com", "123456");
        
        // Act - Atualizar Owner
        originalOwner.setName("João Silva Atualizado");
        originalOwner.setEmail("joao.novo@empresa.com");
        Owner updatedOwner = ownerService.updateOwner(originalOwner);
        
        // Assert
        assertNotNull(updatedOwner);
        assertEquals("João Silva Atualizado", updatedOwner.getName());
        assertEquals("joao.novo@empresa.com", updatedOwner.getEmail());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testUpdateOwnerWhenNoOwnerExists() {
        // Arrange
        Owner owner = new Owner("João Silva", "joao@empresa.com", "123456");
        
        // Act & Assert
        ownerService.updateOwner(owner);
    }
    
    @Test
    public void testGetOwnerSuccess() {
        // Arrange - Criar Owner primeiro
        Owner createdOwner = ownerService.createFirstOwner("OWNER001", "João Silva", "joao@empresa.com", "123456");
        
        // Act
        Owner foundOwner = ownerService.getOwner();
        
        // Assert
        assertNotNull(foundOwner);
        assertEquals("João Silva", foundOwner.getName());
        assertEquals("joao@empresa.com", foundOwner.getEmail());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testGetOwnerWhenNoOwnerExists() {
        // Act & Assert
        ownerService.getOwner();
    }
    
    @Test
    public void testOwnerExists() {
        // Arrange - Criar Owner
        ownerService.createFirstOwner("OWNER001", "João Silva", "joao@empresa.com", "123456");
        
        // Act
        boolean exists = ownerService.ownerExists();
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    public void testOwnerNotExists() {
        // Act
        boolean exists = ownerService.ownerExists();
        
        // Assert
        assertFalse(exists);
    }
} 