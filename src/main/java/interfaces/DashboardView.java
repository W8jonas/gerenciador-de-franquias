package interfaces;

public interface DashboardView {
    // Atualiza a tabela
    void onFranchisesLoaded(String[] columns, String[][] rows);

    // Atualiza o total (status bar)
    void updateTotalCount(int total);

    // Mensagens
    void showInfo(String message);
    void showError(String message);

    // Navegação: abrir visão do gerente para a franquia selecionada
    void goToManagerPanel(String franchiseId);
}
