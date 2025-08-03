package views;

import interfaces.CallbackInterface;

import javax.swing.*;
import java.awt.*;


public class LoginPanel {
    private final JPanel panel;
    private CallbackInterface callback;

    public LoginPanel() {
        panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String email = emailField.getText();

            if (email.equals("admin@admin")) {
                callback.run(1);
            }
        });
        panel.add(loginButton);
    }
    public void setCallback(CallbackInterface callback) {
        this.callback = callback;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setVisible() {
        panel.setVisible(true);
    }

    public void setHidden() {
        panel.setVisible(false);
    }
}
