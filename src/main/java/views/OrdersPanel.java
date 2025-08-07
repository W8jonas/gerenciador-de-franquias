package views;

import domain.model.Customer;
import domain.model.Order;
import domain.model.Seller;
import repository.JsonCustomerRepository;
import repository.JsonOrderRepository;
import repository.JsonSellerRepository;
import session.SessionManager;
import useCases.customer.CustomerUseCase;
import useCases.order.OrderUseCase;
import useCases.seller.SellerUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrdersPanel {
    private final JPanel panel = new JPanel(new BorderLayout());

    private final JButton createBtn = new JButton("Criar");
    private final JButton editBtn = new JButton("Editar");
    private final JButton deleteBtn = new JButton("Excluir");

    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();

    private final OrderUseCase useCase = new OrderUseCase(new JsonOrderRepository());
    private final CustomerUseCase customerUseCase = new CustomerUseCase(new JsonCustomerRepository());
    private final SellerUseCase sellerUseCase = new SellerUseCase(new JsonSellerRepository());

    private final String _franchiseId;

    public OrdersPanel(String franchiseId) {
        this._franchiseId = franchiseId;

        buildToolbar();
        buildTable();
        loadOrders();

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
        model.setColumnIdentifiers(new String[]{"ID Pedido", "Cliente", "Vendedor", "Valor Total"});
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadOrders() {
        model.setRowCount(0);
        List<Order> orders = useCase.findAll();
        List<Customer> customers = customerUseCase.findAll();

        for (Order o : orders) {
            if (_franchiseId.equals(o.getIdFranchise())) {
                Seller seller = sellerUseCase.findByEmail(o.getIdSeller());
                Customer customer = customers.stream()
                        .filter(c -> c.getId().equals(o.getIdCustomer()))
                        .findFirst()
                        .orElse(null);

                model.addRow(new Object[]{
                        o.getId(),
                        customer != null ? customer.getName() : "Desconhecido",
                        seller != null ? seller.getName() : "Desconhecido",
                        String.format("R$ %.2f", o.getTotalAmount())
                });
            }
        }
    }

    private void onCreate() {
        JFrame frame = new JFrame("Novo Pedido");
        CreateOrderPanel createPanel = new CreateOrderPanel(() -> {
            frame.dispose();
            loadOrders();
        }, _franchiseId);

        frame.setContentPane(createPanel.getPanel());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void onEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(panel, "Selecione um pedido para editar.");
            return;
        }

        String orderId = (String) model.getValueAt(selectedRow, 0);
        Order order = useCase.findById(orderId);
        if (order == null) {
            JOptionPane.showMessageDialog(panel, "Pedido não encontrado.");
            return;
        }
        JFrame frame = new JFrame("Editar Pedido");
        CreateOrderPanel createPanel = new CreateOrderPanel(() -> {
            frame.dispose();
            loadOrders();
        }, _franchiseId, order);
        frame.setContentPane(createPanel.getPanel());
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void onDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(panel, "Selecione um pedido para excluir.");
            return;
        }

        int opt = JOptionPane.showConfirmDialog(panel, "Deseja excluir o pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            String orderId = (String) model.getValueAt(selectedRow, 0);
            useCase.delete(orderId);
            JOptionPane.showMessageDialog(panel, "Pedido excluído com sucesso.");
            loadOrders();
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
