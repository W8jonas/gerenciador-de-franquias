package views;

import domain.model.Customer;
import repository.JsonCustomerRepository;
import session.SessionManager;
import useCases.customer.CustomerUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class CustomersPanel {
    private final JPanel panel = new JPanel(new BorderLayout());

    private final JButton createBtn = new JButton("Criar");
    private final JButton editBtn = new JButton("Editar");
    private final JButton deleteBtn = new JButton("Excluir");

    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();

    private final CustomerUseCase useCase = new CustomerUseCase(new JsonCustomerRepository());
    private final String loggedUserEmail = SessionManager.getLoggedUserEmail();
    private String _franchiseId;

    public CustomersPanel(String franchiseId) {
        this._franchiseId = franchiseId;
        buildToolbar();
        buildTable();
        loadCustomers();

        createBtn.addActionListener(e -> onCreate());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());
    }

    private void buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(createBtn);
        toolbar.add(editBtn);
        toolbar.add(deleteBtn);
        panel.add(toolbar, BorderLayout.NORTH);
    }

    private void buildTable() {
        model.setColumnIdentifiers(new String[]{"ID", "Nome", "Telefone", "CPF", "Cidade", "Estado", "Bairro", "Rua", "Número"});
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadCustomers() {
        model.setRowCount(0);
        List<Customer> customers = useCase.findAll();
        for (Customer c : customers) {
            if (loggedUserEmail.equals(c.getIdUserParent())) {
                model.addRow(new Object[]{
                        c.getId(),
                        c.getName(),
                        c.getPhone(),
                        c.getCpf(),
                        c.getCity(),
                        c.getState(),
                        c.getNeighborhood(),
                        c.getStreet(),
                        c.getNumber()
                });
            }
        }
    }

    private void onCreate() {
        CreateCustomerPanel.CustomerForm form = CreateCustomerPanel.show(panel);
        if (form != null) {
            try {
                Customer customer = new Customer(
                        form.name, form.phone, form.cpf,
                        form.city, form.state, form.neighborhood,
                        form.street, form.number,
                        loggedUserEmail
                );
                customer.setFranchiseId(_franchiseId);
                useCase.save(customer);
                JOptionPane.showMessageDialog(panel, "Cliente criado com sucesso.");
                loadCustomers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(panel, "Selecione um cliente para editar.");
            return;
        }

        String id = (String) model.getValueAt(selected, 0);
        Customer customer = useCase.findById(id);
        if (customer == null) {
            JOptionPane.showMessageDialog(panel, "Cliente não encontrado.");
            return;
        }

        CreateCustomerPanel.CustomerForm form = CreateCustomerPanel.show(panel, customer);
        if (form != null) {
            try {
                customer.setName(form.name);
                customer.setPhone(form.phone);
                customer.setCpf(form.cpf);
                customer.setCity(form.city);
                customer.setState(form.state);
                customer.setNeighborhood(form.neighborhood);
                customer.setStreet(form.street);
                customer.setNumber(form.number);

                useCase.update(customer);
                JOptionPane.showMessageDialog(panel, "Cliente atualizado com sucesso.");
                loadCustomers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDelete() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(panel, "Selecione um cliente para excluir.");
            return;
        }

        String id = (String) model.getValueAt(selected, 0);
        int opt = JOptionPane.showConfirmDialog(panel, "Deseja realmente excluir o cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            try {
                useCase.deleteById(id);
                JOptionPane.showMessageDialog(panel, "Cliente excluído com sucesso.");
                loadCustomers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
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
