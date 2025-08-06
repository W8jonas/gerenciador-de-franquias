package views;

import domain.model.Customer;
import session.SessionManager;

import javax.swing.*;
import java.awt.*;

public class CreateCustomerPanel {

    public static class CustomerForm {
        public String name;
        public String phone;
        public String cpf;
        public String city;
        public String state;
        public String neighborhood;
        public String street;
        public String number;
        public String idUserParent; // Email do usuário logado
        public String franchiseId;
    }

    public static CustomerForm show(Component parent) {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField cityField = new JTextField();
        JTextField stateField = new JTextField();
        JTextField neighborhoodField = new JTextField();
        JTextField streetField = new JTextField();
        JTextField numberField = new JTextField();
        JTextField userField = new JTextField(SessionManager.getLoggedUserEmail());
        userField.setEnabled(false); // não editável

        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Telefone:"));
        panel.add(phoneField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Cidade:"));
        panel.add(cityField);
        panel.add(new JLabel("Estado:"));
        panel.add(stateField);
        panel.add(new JLabel("Bairro:"));
        panel.add(neighborhoodField);
        panel.add(new JLabel("Rua:"));
        panel.add(streetField);
        panel.add(new JLabel("Número:"));
        panel.add(numberField);
        panel.add(new JLabel("Usuário responsável:"));
        panel.add(userField);

        int res = JOptionPane.showConfirmDialog(parent, panel, "Novo Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            CustomerForm form = new CustomerForm();
            form.name = nameField.getText();
            form.phone = phoneField.getText();
            form.cpf = cpfField.getText();
            form.city = cityField.getText();
            form.state = stateField.getText();
            form.neighborhood = neighborhoodField.getText();
            form.street = streetField.getText();
            form.number = numberField.getText();
            form.idUserParent = SessionManager.getLoggedUserEmail();
            return form;
        }
        return null;
    }

    public static CustomerForm show(Component parent, Customer customer) {
        JTextField nameField = new JTextField(customer.getName());
        JTextField phoneField = new JTextField(customer.getPhone());
        JTextField cpfField = new JTextField(customer.getCpf());
        JTextField cityField = new JTextField(customer.getCity());
        JTextField stateField = new JTextField(customer.getState());
        JTextField neighborhoodField = new JTextField(customer.getNeighborhood());
        JTextField streetField = new JTextField(customer.getStreet());
        JTextField numberField = new JTextField(customer.getNumber());
        JTextField idUserParentField = new JTextField(customer.getIdUserParent());
        idUserParentField.setEnabled(false); // não editável

        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Telefone:"));
        panel.add(phoneField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Cidade:"));
        panel.add(cityField);
        panel.add(new JLabel("Estado:"));
        panel.add(stateField);
        panel.add(new JLabel("Bairro:"));
        panel.add(neighborhoodField);
        panel.add(new JLabel("Rua:"));
        panel.add(streetField);
        panel.add(new JLabel("Número:"));
        panel.add(numberField);
        panel.add(new JLabel("Responsável:"));
        panel.add(idUserParentField);

        int res = JOptionPane.showConfirmDialog(parent, panel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            CustomerForm form = new CustomerForm();
            form.name = nameField.getText();
            form.phone = phoneField.getText();
            form.cpf = cpfField.getText();
            form.city = cityField.getText();
            form.state = stateField.getText();
            form.neighborhood = neighborhoodField.getText();
            form.street = streetField.getText();
            form.number = numberField.getText();
            form.idUserParent = idUserParentField.getText();
            return form;
        }
        return null;
    }
}
