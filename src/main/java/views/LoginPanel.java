package views;

import controller.LoginController;
import interfaces.CallbackInterface;
import interfaces.LoginView;
import session.SessionManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel implements LoginView {
    private final JPanel panel = new JPanel(new BorderLayout());
    private CallbackInterface callback;

    private final LoginController controller = new LoginController(this);

    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    public LoginPanel() {
        // Container central com os campos
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        formPanel.add(labelWithField("Email:", emailField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labelWithField("Password:", passwordField));
        formPanel.add(Box.createVerticalStrut(20));

        // Botão Login
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            controller.login(email, pass);
        });

        panel.add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Helper para criar linha com JLabel e campo (em horizontal)
     */
    private JPanel labelWithField(String label, JTextField field) {
        JPanel line = new JPanel(new BorderLayout(5, 5));
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(70, 20));
        line.add(jLabel, BorderLayout.WEST);
        line.add(field, BorderLayout.CENTER);
        return line;
    }

    public void setCallback(CallbackInterface callback) { this.callback = callback; }
    public JPanel getPanel() { return panel; }
    public void setVisible() { panel.setVisible(true); }
    public void setHidden() { panel.setVisible(false); }

    @Override
    public void onLoginSuccess(int roleCode) {
        String email = emailField.getText();
        SessionManager.setLoggedManagerEmail(email);
        if (callback != null) callback.run(roleCode);
    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
