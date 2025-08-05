package views;
import interfaces.CreateAccountPanelCallback;

import javax.swing.*;
import java.awt.*;

public class MainView {

    private JFrame app;
    private final int WIDTH = 500;
    private final int HEIGHT = 200;

    private final LoginPanel loginPanel = new LoginPanel();
    private final CreateAccountPanel createAccountPanel = new CreateAccountPanel();
    private final DashboardPanel dashboard = new DashboardPanel();

    public void start(boolean hasOwner) {
        app = new JFrame("Gerenciador de franquias");
        app.setSize(WIDTH, HEIGHT);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new BorderLayout());

        if (hasOwner) {
            showLogin();
        } else {
            showCreateAccountPanel();
        }

        app.setVisible(true);
    }

    private void showLogin() {
        app.getContentPane().add(loginPanel.getPanel(), BorderLayout.WEST);
        loginPanel.setCallback((role) -> {
            hiddenLogin();
            switch (role) {
                case 1 -> showDashboardPanel();
                case 2 -> showDashboardPanel();
                case 3 -> showDashboardPanel();
            }
        });
        loginPanel.setVisible();
    }
    private void hiddenLogin() {
        loginPanel.setHidden();
    }

    private void showCreateAccountPanel() {
        app.getContentPane().add(createAccountPanel.getPanel(), BorderLayout.CENTER);
        createAccountPanel.setCallback(new CreateAccountPanelCallback() {
            @Override
            public void goToOwnerDashboard(String ownerId, String name, String email) {
                hiddenCreateAccountPanel();
                showDashboardPanel();
            }

            @Override
            public void goToLogin() {
                hiddenCreateAccountPanel();
            }
        });
        createAccountPanel.setVisible();
    }
    private void hiddenCreateAccountPanel() {
        createAccountPanel.setHidden();
    }

    private void showDashboardPanel() {
        app.getContentPane().add(dashboard.getPanel(), BorderLayout.CENTER);
        dashboard.setVisible();
    }
    private void hiddenDashboardPanel() {
        dashboard.setHidden();
    }

}
