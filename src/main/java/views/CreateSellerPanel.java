package views;

import javax.swing.*;
import java.awt.*;

public class CreateSellerPanel {
    public static class SellerForm {
        public String name;
        public String email;
        public String password;
    }

    public static SellerForm show(Component parent) {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nome:"));    panel.add(nameField);
        panel.add(new JLabel("Email:"));   panel.add(emailField);
        panel.add(new JLabel("Senha:"));   panel.add(passwordField);

        int res = JOptionPane.showConfirmDialog(parent, panel, "Novo Vendedor", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            SellerForm form = new SellerForm();
            form.name = nameField.getText();
            form.email = emailField.getText();
            form.password = new String(passwordField.getPassword());
            return form;
        }
        return null;
    }

    public static SellerForm show(Component parent, String name, String email, String password) {
        JTextField nameField = new JTextField(name);
        JTextField emailField = new JTextField(email);
        emailField.setEnabled(false); // não pode editar o ID (email)
        JPasswordField passField = new JPasswordField(password);

        JPanel p = new JPanel(new GridLayout(0, 2));
        p.add(new JLabel("Nome:")); p.add(nameField);
        p.add(new JLabel("Email:")); p.add(emailField);
        p.add(new JLabel("Senha:")); p.add(passField);

        int res = JOptionPane.showConfirmDialog(parent, p, "Editar Vendedor", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            SellerForm form = new SellerForm();
            form.name = nameField.getText();
            form.email = emailField.getText();
            form.password = new String(passField.getPassword());
            return form;
        }
        return null;
    }
}
