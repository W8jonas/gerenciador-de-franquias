package views;

import javax.swing.*;
import java.awt.*;

public class MainView {

    private JFrame app;
    private final int WIDTH = 500;
    private final int HEIGHT = 200;

    private LoginPanel loginPanel = new LoginPanel();

    public void start() {
        app = new JFrame("Agenda");
        app.setSize(WIDTH, HEIGHT);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new BorderLayout());

        showLogin();

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
}
