import application.service.OwnerService;
import domain.model.Owner;
import domain.repository.OwnerRepository;
import infra.repository.file.OwnerFileRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class CompleteIntegrationTest {
    
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
    public void testCompleteOwnerSystem() {
        // Arrange
        OwnerRepository repository = new OwnerFileRepository();
        OwnerService service = new OwnerService(repository);
        
        // Act & Assert - Teste completo do sistema
        try {
            // 1. Verificar que não existe Owner inicialmente
            assertFalse(service.ownerExists());
            
            // 2. Criar o primeiro Owner
            Owner owner = service.createFirstOwner("OWNER001", "João Silva", "joao@empresa.com", "123456");
            assertNotNull(owner);
            assertEquals("João Silva", owner.getName());
            assertEquals("joao@empresa.com", owner.getEmail());
            
            // 3. Verificar que Owner foi criado
            assertTrue(service.ownerExists());
            
            // 4. Buscar o Owner
            Owner foundOwner = service.getOwner();
            assertNotNull(foundOwner);
            assertEquals("João Silva", foundOwner.getName());
            
            // 5. Atualizar o Owner
            foundOwner.setName("João Silva Atualizado");
            foundOwner.setEmail("joao.novo@empresa.com");
            Owner updatedOwner = service.updateOwner(foundOwner);
            assertNotNull(updatedOwner);
            assertEquals("João Silva Atualizado", updatedOwner.getName());
            
            // 6. Verificar que a atualização foi persistida
            Owner reloadedOwner = service.getOwner();
            assertEquals("João Silva Atualizado", reloadedOwner.getName());
            assertEquals("joao.novo@empresa.com", reloadedOwner.getEmail());
            
            System.out.println("✅ Teste completo do sistema Owner realizado com sucesso!");
            
        } catch (Exception e) {
            fail("Erro no teste completo: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 