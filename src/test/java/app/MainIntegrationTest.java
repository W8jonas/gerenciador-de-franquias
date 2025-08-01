package app;

import application.service.OwnerService;
import domain.model.Owner;
import domain.repository.OwnerRepository;
import infra.repository.file.OwnerFileRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class MainIntegrationTest {
    
    private static final String TEST_FILE_PATH = "data/owner.dat";
    
    @Before
    public void setUp() {
        // Limpar arquivo de teste se existir
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testOwnerSystemInitialization() {
        // Arrange
        OwnerRepository ownerRepository = new OwnerFileRepository();
        OwnerService ownerService = new OwnerService(ownerRepository);
        
        // Act - Simular a inicialização do sistema
        try {
            if (!ownerService.ownerExists()) {
                Owner owner = ownerService.createFirstOwner(
                    "OWNER001", 
                    "Administrador", 
                    "admin@empresa.com", 
                    "123456"
                );
                
                // Assert
                assertNotNull(owner);
                assertEquals("Administrador", owner.getName());
                assertEquals("admin@empresa.com", owner.getEmail());
                assertTrue(ownerService.ownerExists());
            }
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }
    
    @Test
    public void testOwnerSystemInitializationWhenOwnerAlreadyExists() {
        // Arrange
        OwnerRepository ownerRepository = new OwnerFileRepository();
        OwnerService ownerService = new OwnerService(ownerRepository);
        
        // Criar primeiro Owner
        try {
            ownerService.createFirstOwner("OWNER001", "Administrador", "admin@empresa.com", "123456");
        } catch (Exception e) {
            fail("Erro ao criar primeiro Owner: " + e.getMessage());
        }
        
        // Act - Tentar criar novamente (deve apenas verificar que existe)
        try {
            if (!ownerService.ownerExists()) {
                ownerService.createFirstOwner("OWNER002", "Outro Admin", "outro@empresa.com", "654321");
            }
            
            // Assert - Deve existir apenas o primeiro Owner
            assertTrue(ownerService.ownerExists());
            Owner foundOwner = ownerService.getOwner();
            assertEquals("Administrador", foundOwner.getName());
            assertEquals("admin@empresa.com", foundOwner.getEmail());
            
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }
} 