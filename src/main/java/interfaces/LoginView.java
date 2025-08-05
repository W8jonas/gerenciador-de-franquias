package interfaces;

public interface LoginView {
    void onLoginSuccess(int roleCode);   // 1-Owner, 2-Manager, 3-Seller
    void onError(String message);
}
