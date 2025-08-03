package views;

import controller.CreateAccountController;
import interfaces.CreateAccountView;

import javax.swing.*;
import java.awt.*;

public class CreateAccountPanel implements CreateAccountView {

    private final JPanel panel = new JPanel(new GridBagLayout());
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton createButton = new JButton("Create Owner Account");

    private final CreateAccountController controller = new CreateAccountController(this);

    public CreateAccountPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        c.gridx = 0; c.gridy = row; panel.add(new JLabel("Name:"), c);
        c.gridx = 1; c.gridy = row++; panel.add(nameField, c);

        c.gridx = 0; c.gridy = row; panel.add(new JLabel("Email:"), c);
        c.gridx = 1; c.gridy = row++; panel.add(emailField, c);

        c.gridx = 0; c.gridy = row; panel.add(new JLabel("Password:"), c);
        c.gridx = 1; c.gridy = row++; panel.add(passwordField, c);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        panel.add(createButton, c);

        // Apenas repassa Strings para o controller
        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            controller.createOwner(name, email, password);
        });
    }

    public JPanel getPanel() { return panel; }
    public void setVisible() { panel.setVisible(true); }
    public void setHidden() { panel.setVisible(false); }

    @Override
    public void onAccountCreated(String ownerId, String name, String email) {
        JOptionPane.showMessageDialog(panel,
                "Owner created!\nID: " + ownerId + "\n" + name + " <" + email + ">",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
