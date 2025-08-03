package controller;

import interfaces.CreateAccountView;
import repository.InMemoryOwnerRepository;
import repository.JsonOwnerRepository;
import useCases.CreateNewAccountUseCase;

public class CreateAccountController {

    private final CreateAccountView view;
    private final CreateNewAccountUseCase useCase;

    public CreateAccountController(CreateAccountView view) {
        this.view = view;
        this.useCase = new CreateNewAccountUseCase(new JsonOwnerRepository());
    }

    public void createOwner(String name, String email, String password) {
        if (name == null || name.isBlank()) {
            view.onError("Nome é obrigatório.");
            return;
        }
        if (email == null || email.isBlank()) {
            view.onError("Email é obrigatório.");
            return;
        }
        if (password == null || password.isBlank()) {
            view.onError("Senha é obrigatória.");
            return;
        }

        try {
            String ownerId = useCase.execute(name, email, password);
            view.onAccountCreated(ownerId, name, email);
        } catch (RuntimeException ex) {
            view.onError(ex.getMessage());
        }
    }
}
