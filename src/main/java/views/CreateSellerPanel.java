package views;

import domain.model.Order;
import repository.JsonOrderRepository;
import repository.JsonSellerRepository;
import useCases.order.OrderUseCase;
import useCases.seller.SellerUseCase;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CreateSellerPanel {

    private static final OrderUseCase useCase = new OrderUseCase(new JsonOrderRepository());

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

        List<Order> orders = useCase.findAll();
        int count = 0;
        double total = 0;

        for (Order item : orders) {
            if (item.getIdSeller().equals(email)) {
                count += 1;
                total += item.getTotalAmount();
            }
        }

        String t = String.format("%.2f", total);
        String i = String.valueOf(count);

        JTextField qtdField = new JTextField(i);
        qtdField.setEnabled(false);
        JTextField amountField = new JTextField(t);
        amountField.setEnabled(false);

        JPanel p = new JPanel(new GridLayout(0, 2));
        p.add(new JLabel("Nome:")); p.add(nameField);
        p.add(new JLabel("Email:")); p.add(emailField);
        p.add(new JLabel("Senha:")); p.add(passField);
        p.add(new JLabel("N° Vendas")); p.add(qtdField);
        p.add(new JLabel("Total (R$) ")); p.add(amountField);

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
