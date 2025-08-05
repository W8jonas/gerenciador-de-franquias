package controller;

import interfaces.LoginView;
import repository.*;
import useCases.LoginUseCase;

public class LoginController {

    private final LoginView view;
    private final LoginUseCase useCase;

    public LoginController(LoginView view) {
        this.view = view;

        OwnerRepository   ownerRepo = new JsonOwnerRepository();
        ManagerRepository mgrRepo   = new InMemoryManagerRepository();
        SellerRepository  selRepo   = new InMemorySellerRepository();

        this.useCase = new LoginUseCase(ownerRepo, mgrRepo, selRepo);
    }

    public void login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            view.onError("Preencha email e senha.");
            return;
        }
        try {
            int code = useCase.execute(email, password);
            view.onLoginSuccess(code);
        } catch (IllegalArgumentException ex) {
            view.onError(ex.getMessage());
        }
    }
}
