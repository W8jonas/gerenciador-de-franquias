package interfaces;

public interface CreateAccountView {
    void onAccountCreated(String ownerId, String name, String email);
    void onError(String message);
}
