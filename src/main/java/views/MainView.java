package views;
import interfaces.CreateAccountPanelCallback;

import javax.swing.*;
import java.awt.*;

public class MainView {

    private JFrame app;
    private final int WIDTH = 500;
    private final int HEIGHT = 200;

    private final LoginPanel loginPanel = new LoginPanel();
    private final CreateAccountPanel createPanel = new CreateAccountPanel();

    public void start() {
        app = new JFrame("Agenda");
        app.setSize(WIDTH, HEIGHT);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new BorderLayout());

        showCreateAccountPanel();

        app.setVisible(true);
    }

    private void showLogin() {
        app.getContentPane().add(loginPanel.getPanel(), BorderLayout.WEST);
        loginPanel.setCallback((_i) -> {
            hiddenLogin();
        });
        loginPanel.setVisible();
    }
    private void hiddenLogin() {
        loginPanel.setHidden();
    }

    private void showCreateAccountPanel() {
        app.getContentPane().add(createPanel.getPanel(), BorderLayout.CENTER);
        createPanel.setCallback(new CreateAccountPanelCallback() {
            @Override
            public void goToOwnerDashboard(String ownerId, String name, String email) {
                hiddenCreateAccountPanel();
                showLogin();
            }

            @Override
            public void goToLogin() {
                hiddenCreateAccountPanel();
            }
        });
        createPanel.setVisible();
    }
    private void hiddenCreateAccountPanel() {
        createPanel.setHidden();
    }
}
