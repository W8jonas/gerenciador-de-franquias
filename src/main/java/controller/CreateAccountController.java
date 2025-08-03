package controller;

import interfaces.CreateAccountView;
import repository.InMemoryOwnerRepository;
import useCases.CreateNewAccountUseCase;

public class CreateAccountController {

    private final CreateAccountView view;
    private final CreateNewAccountUseCase useCase;

    public CreateAccountController(CreateAccountView view) {
        this.view = view;
        this.useCase = new CreateNewAccountUseCase(new InMemoryOwnerRepository());
    }

    public void createOwner(String name, String email, String password) {
        if (name == null || name.isBlank()) {
            view.onError("Name is required.");
            return;
        }
        if (email == null || email.isBlank()) {
            view.onError("Email is required.");
            return;
        }
        if (password == null || password.isBlank()) {
            view.onError("Password is required.");
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
