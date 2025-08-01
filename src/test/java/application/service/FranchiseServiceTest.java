package application.service;

import domain.model.Franchise;
import domain.model.Address;
import domain.model.Manager;
import domain.repository.FranchiseRepository;
import infra.repository.file.FranchiseFileRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

public class FranchiseServiceTest {
    
    private FranchiseService franchiseService;
    private FranchiseRepository repository;
    private static final String TEST_FILE_PATH = "data/franchises.dat";
    
    @Before
    public void setUp() {
        repository = new FranchiseFileRepository();
        franchiseService = new FranchiseService(repository);
        
        // Limpar arquivo de teste antes de cada teste
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testCreateFranchiseSuccess() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Manager manager = new Manager("João Gerente", "joao@empresa.com", "123456");
        
        // Act
        Franchise franchise = franchiseService.createFranchise("Franquia Centro", address, manager);
        
        // Assert
        assertNotNull(franchise);
        assertTrue(franchise.getId().startsWith("FRANCHISE_"));
        assertEquals("Franquia Centro", franchise.getName());
        assertEquals(address, franchise.getAddress());
        assertEquals(manager, franchise.getManager());
        assertTrue(franchiseService.franchiseExists(franchise.getId()));
    }
    
    @Test
    public void testCreateFranchiseWithoutManager() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        
        // Act
        Franchise franchise = franchiseService.createFranchise("Franquia Centro", address, null);
        
        // Assert
        assertNotNull(franchise);
        assertEquals("Franquia Centro", franchise.getName());
        assertEquals(address, franchise.getAddress());
        assertNull(franchise.getManager());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateFranchiseWithNullName() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        
        // Act & Assert
        franchiseService.createFranchise(null, address, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateFranchiseWithEmptyName() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        
        // Act & Assert
        franchiseService.createFranchise("", address, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateFranchiseWithNullAddress() {
        // Act & Assert
        franchiseService.createFranchise("Franquia Centro", null, null);
    }
    
    @Test
    public void testUpdateFranchiseSuccess() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = franchiseService.createFranchise("Franquia Centro", address, null);
        
        // Act
        franchise.setName("Franquia Atualizada");
        franchiseService.updateFranchise(franchise);
        
        // Assert
        Franchise updatedFranchise = franchiseService.getFranchiseById(franchise.getId());
        assertEquals("Franquia Atualizada", updatedFranchise.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateFranchiseNull() {
        // Act & Assert
        franchiseService.updateFranchise(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateFranchiseWithNullId() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = new Franchise(null, "Franquia", address);
        
        // Act & Assert
        franchiseService.updateFranchise(franchise);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateFranchiseNonExistent() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = new Franchise("NONEXISTENT", "Franquia", address);
        
        // Act & Assert
        franchiseService.updateFranchise(franchise);
    }
    
    @Test
    public void testDeleteFranchiseSuccess() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = franchiseService.createFranchise("Franquia Centro", address, null);
        String franchiseId = franchise.getId();
        
        // Act
        franchiseService.deleteFranchise(franchiseId);
        
        // Assert
        assertFalse(franchiseService.franchiseExists(franchiseId));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteFranchiseNullId() {
        // Act & Assert
        franchiseService.deleteFranchise(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteFranchiseEmptyId() {
        // Act & Assert
        franchiseService.deleteFranchise("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteFranchiseNonExistent() {
        // Act & Assert
        franchiseService.deleteFranchise("NONEXISTENT");
    }
    
    @Test
    public void testGetFranchiseByIdSuccess() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise createdFranchise = franchiseService.createFranchise("Franquia Centro", address, null);
        
        // Act
        Franchise foundFranchise = franchiseService.getFranchiseById(createdFranchise.getId());
        
        // Assert
        assertNotNull(foundFranchise);
        assertEquals(createdFranchise.getId(), foundFranchise.getId());
        assertEquals("Franquia Centro", foundFranchise.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetFranchiseByIdNull() {
        // Act & Assert
        franchiseService.getFranchiseById(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetFranchiseByIdEmpty() {
        // Act & Assert
        franchiseService.getFranchiseById("");
    }
    
    @Test(expected = IllegalStateException.class)
    public void testGetFranchiseByIdNonExistent() {
        // Act & Assert
        franchiseService.getFranchiseById("NONEXISTENT");
    }
    
    @Test
    public void testGetAllFranchises() {
        // Arrange
        Address address1 = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Address address2 = new Address("ADDR002", "Rua das Palmeiras", "456", "Jardins", "São Paulo", "SP", "01234-890", "Brasil");
        
        franchiseService.createFranchise("Franquia Centro", address1, null);
        franchiseService.createFranchise("Franquia Jardins", address2, null);
        
        // Act
        List<Franchise> allFranchises = franchiseService.getAllFranchises();
        
        // Assert
        assertEquals(2, allFranchises.size());
        assertTrue(allFranchises.stream().anyMatch(f -> f.getName().equals("Franquia Centro")));
        assertTrue(allFranchises.stream().anyMatch(f -> f.getName().equals("Franquia Jardins")));
    }
    
    @Test
    public void testGetAllFranchisesEmpty() {
        // Act
        List<Franchise> allFranchises = franchiseService.getAllFranchises();
        
        // Assert
        assertNotNull(allFranchises);
        assertTrue(allFranchises.isEmpty());
    }
    
    @Test
    public void testFranchiseExists() {
        // Arrange
        Address address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Franchise franchise = franchiseService.createFranchise("Franquia Centro", address, null);
        
        // Act & Assert
        assertTrue(franchiseService.franchiseExists(franchise.getId()));
        assertFalse(franchiseService.franchiseExists("NONEXISTENT"));
        assertFalse(franchiseService.franchiseExists(null));
        assertFalse(franchiseService.franchiseExists(""));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullRepository() {
        // Act & Assert
        new FranchiseService(null);
    }
    
    @Test
    public void testMultipleOperations() {
        // Arrange
        Address address1 = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        Address address2 = new Address("ADDR002", "Rua das Palmeiras", "456", "Jardins", "São Paulo", "SP", "01234-890", "Brasil");
        
        // Act - Criar duas franquias
        Franchise franchise1 = franchiseService.createFranchise("Franquia Centro", address1, null);
        Franchise franchise2 = franchiseService.createFranchise("Franquia Jardins", address2, null);
        
        // Verificar que foram criadas
        assertEquals(2, franchiseService.getAllFranchises().size());
        
        // Atualizar uma franquia
        franchise1.setName("Franquia Centro Atualizada");
        franchiseService.updateFranchise(franchise1);
        
        // Verificar atualização
        Franchise updatedFranchise = franchiseService.getFranchiseById(franchise1.getId());
        assertEquals("Franquia Centro Atualizada", updatedFranchise.getName());
        
        // Deletar uma franquia
        franchiseService.deleteFranchise(franchise2.getId());
        
        // Verificar estado final
        assertEquals(1, franchiseService.getAllFranchises().size());
        assertTrue(franchiseService.franchiseExists(franchise1.getId()));
        assertFalse(franchiseService.franchiseExists(franchise2.getId()));
    }
} 