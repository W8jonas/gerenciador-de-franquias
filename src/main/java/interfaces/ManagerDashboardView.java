package interfaces;

public interface ManagerDashboardView {
    void onManagersLoaded(String[] columns, String[][] rows);
    void updateTotalCount(int total);
    void showInfo(String message);
    void showError(String message);
}
