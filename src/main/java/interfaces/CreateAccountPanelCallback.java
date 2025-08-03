package interfaces;

public interface CreateAccountPanelCallback {
    void goToOwnerDashboard(String ownerId, String name, String email);
    void goToLogin();
}
