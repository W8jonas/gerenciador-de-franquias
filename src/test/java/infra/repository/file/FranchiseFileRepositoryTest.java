package infra.repository.file;

import domain.model.Franchise;
import domain.model.Address;
import domain.repository.FranchiseRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class FranchiseFileRepositoryTest {
    
    private FranchiseRepository repository;
    private static final String TEST_FILE_PATH = "data/franchises.dat";
    
    @Before
    public void setUp() {
        repository = new FranchiseFileRepository();
        // Limpar arquivo de teste antes de cada teste
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testSaveAndFindById() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = new Franchise("FRANCHISE001", "Franquia Centro", address);
        
        // Act
        repository.save(franchise);
        Optional<Franchise> found = repository.findById("FRANCHISE001");
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals("FRANCHISE001", found.get().getId());
        assertEquals("Franquia Centro", found.get().getName());
        assertEquals(address, found.get().getAddress());
    }
    
    @Test
    public void testSaveUpdate() {
        // Arrange
        Address address1 = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Address address2 = new Address("ADDR002", "Rua das Palmeiras", "456", "Jardins", "São Paulo", "SP", "01234-890", "Brasil");
        
        Franchise franchise1 = new Franchise("FRANCHISE001", "Franquia Centro", address1);
        Franchise franchise2 = new Franchise("FRANCHISE001", "Franquia Jardins", address2); // Mesmo ID, nome diferente
        
        // Act
        repository.save(franchise1);
        repository.save(franchise2); // Deve atualizar a franquia existente
        
        Optional<Franchise> found = repository.findById("FRANCHISE001");
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals("Franquia Jardins", found.get().getName()); // Nome atualizado
        assertEquals(address2, found.get().getAddress()); // Endereço atualizado
    }
    
    @Test
    public void testFindAll() {
        // Arrange
        Address address1 = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Address address2 = new Address("ADDR002", "Rua das Palmeiras", "456", "Jardins", "São Paulo", "SP", "01234-890", "Brasil");
        
        Franchise franchise1 = new Franchise("FRANCHISE001", "Franquia Centro", address1);
        Franchise franchise2 = new Franchise("FRANCHISE002", "Franquia Jardins", address2);
        
        // Act
        repository.save(franchise1);
        repository.save(franchise2);
        List<Franchise> allFranchises = repository.findAll();
        
        // Assert
        assertEquals(2, allFranchises.size());
        assertTrue(allFranchises.stream().anyMatch(f -> f.getId().equals("FRANCHISE001")));
        assertTrue(allFranchises.stream().anyMatch(f -> f.getId().equals("FRANCHISE002")));
    }
    
    @Test
    public void testFindAllEmpty() {
        // Act
        List<Franchise> allFranchises = repository.findAll();
        
        // Assert
        assertNotNull(allFranchises);
        assertTrue(allFranchises.isEmpty());
    }
    
    @Test
    public void testDelete() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = new Franchise("FRANCHISE001", "Franquia Centro", address);
        
        // Act
        repository.save(franchise);
        assertTrue(repository.existsById("FRANCHISE001"));
        
        repository.delete("FRANCHISE001");
        
        // Assert
        assertFalse(repository.existsById("FRANCHISE001"));
        assertTrue(repository.findById("FRANCHISE001").isEmpty());
    }
    
    @Test
    public void testDeleteNonExistent() {
        // Act & Assert - Não deve lançar exceção
        repository.delete("NONEXISTENT");
        assertFalse(repository.existsById("NONEXISTENT"));
    }
    
    @Test
    public void testExistsById() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = new Franchise("FRANCHISE001", "Franquia Centro", address);
        
        // Act & Assert
        assertFalse(repository.existsById("FRANCHISE001"));
        
        repository.save(franchise);
        assertTrue(repository.existsById("FRANCHISE001"));
    }
    
    @Test
    public void testFindByIdNonExistent() {
        // Act
        Optional<Franchise> found = repository.findById("NONEXISTENT");
        
        // Assert
        assertTrue(found.isEmpty());
    }
    
    @Test
    public void testFindByIdNull() {
        // Act
        Optional<Franchise> found = repository.findById(null);
        
        // Assert
        assertTrue(found.isEmpty());
    }
    
    @Test
    public void testFindByIdEmpty() {
        // Act
        Optional<Franchise> found = repository.findById("");
        
        // Assert
        assertTrue(found.isEmpty());
    }
    
    @Test
    public void testExistsByIdNull() {
        // Act
        boolean exists = repository.existsById(null);
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    public void testExistsByIdEmpty() {
        // Act
        boolean exists = repository.existsById("");
        
        // Assert
        assertFalse(exists);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSaveNull() {
        // Act & Assert
        repository.save(null);
    }
    
    @Test
    public void testMultipleOperations() {
        // Arrange
        Address address1 = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Address address2 = new Address("ADDR002", "Rua das Palmeiras", "456", "Jardins", "São Paulo", "SP", "01234-890", "Brasil");
        Address address3 = new Address("ADDR003", "Rua das Acácias", "789", "Vila Madalena", "São Paulo", "SP", "01234-123", "Brasil");
        
        Franchise franchise1 = new Franchise("FRANCHISE001", "Franquia Centro", address1);
        Franchise franchise2 = new Franchise("FRANCHISE002", "Franquia Jardins", address2);
        Franchise franchise3 = new Franchise("FRANCHISE003", "Franquia Vila Madalena", address3);
        
        // Act - Operações múltiplas
        repository.save(franchise1);
        repository.save(franchise2);
        repository.save(franchise3);
        
        // Verificar que todas existem
        assertTrue(repository.existsById("FRANCHISE001"));
        assertTrue(repository.existsById("FRANCHISE002"));
        assertTrue(repository.existsById("FRANCHISE003"));
        
        // Deletar uma
        repository.delete("FRANCHISE002");
        
        // Verificar estado final
        assertTrue(repository.existsById("FRANCHISE001"));
        assertFalse(repository.existsById("FRANCHISE002"));
        assertTrue(repository.existsById("FRANCHISE003"));
        
        List<Franchise> allFranchises = repository.findAll();
        assertEquals(2, allFranchises.size());
    }
} 