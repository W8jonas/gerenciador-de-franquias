package app;

import application.service.OwnerService;
import domain.repository.OwnerRepository;
import infra.repository.file.OwnerFileRepository;
import views.LoginView;
import views.MainView;

public class Main {
    public static void main(String[] args) {
        // Inicializar o sistema de Owner
        initializeOwnerSystem();
        
        // Iniciar a interface gráfica
        MainView mainView = new MainView();
        mainView.start();
    }
    
    /**
     * Inicializa o sistema de Owner criando o primeiro Owner se não existir
     */
    private static void initializeOwnerSystem() {
        try {
            // Instanciar o repositório
            OwnerRepository ownerRepository = new OwnerFileRepository();
            
            // Instanciar o serviço
            OwnerService ownerService = new OwnerService(ownerRepository);
            
            // Criar o primeiro Owner se não existir
            if (!ownerService.ownerExists()) {
                ownerService.createFirstOwner(
                    "OWNER001", 
                    "Administrador", 
                    "admin@empresa.com", 
                    "123456"
                );
                System.out.println("✅ Owner padrão criado com sucesso!");
            } else {
                System.out.println("ℹ️ Owner já existe no sistema.");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar o sistema de Owner: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
