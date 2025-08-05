package views;

import controller.LoginController;
import interfaces.CallbackInterface;
import interfaces.LoginView;

import javax.swing.*;
import java.awt.*;


public class LoginPanel implements LoginView {
    private final JPanel panel = new JPanel(new GridLayout(3, 2));
    private CallbackInterface callback;

    private final LoginController controller = new LoginController(this);

    public LoginPanel() {
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passwordField.getPassword());
            controller.login(email, pass);
        });
        panel.add(loginButton);
    }

    public void setCallback(CallbackInterface callback) { this.callback = callback; }
    public JPanel getPanel() { return panel; }
    public void setVisible() { panel.setVisible(true); }
    public void setHidden() { panel.setVisible(false); }

    public void onLoginSuccess(int roleCode) {
        if (callback != null) callback.run(roleCode);
    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
