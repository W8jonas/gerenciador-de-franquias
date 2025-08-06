package views;

import domain.model.Customer;
import domain.model.Order;
import domain.model.OrderItem;
import repository.JsonCustomerRepository;
import repository.JsonOrderRepository;
import repository.OrderRepository;
import session.SessionManager;
import useCases.customer.CustomerUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateOrderPanel {
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JComboBox<Customer> customerCombo = new JComboBox<>();
    private final JTextField cityField = new JTextField();
    private final JTextField stateField = new JTextField();
    private final JTextField neighborhoodField = new JTextField();
    private final JTextField streetField = new JTextField();
    private final JTextField numberField = new JTextField();
    private final JTextField sellerField = new JTextField();
    private final JComboBox<String> productCombo = new JComboBox<>();
    private final JTextField quantityField = new JTextField();
    private final JButton addItemButton = new JButton("Adicionar Produto");
    private final JButton removeItemButton = new JButton("Remover Produto");
    private final JTable itemTable = new JTable();
    private final DefaultTableModel itemTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JLabel totalLabel = new JLabel("Total: R$ 0,00");
    private final JButton saveButton = new JButton("Salvar Pedido");

    private final List<OrderItem> orderItems = new ArrayList<>();
    private final OrderRepository orderRepo = new JsonOrderRepository();
    private final CustomerUseCase customerUseCase = new CustomerUseCase(new JsonCustomerRepository());

    private String _franchiseId;
    private final Runnable onOrderSaved;
    private Order editingOrder;

    public CreateOrderPanel(Runnable onOrderSaved, String franchiseId) {
        this(onOrderSaved, franchiseId, null);
    }

    public CreateOrderPanel(Runnable onOrderSaved, String franchiseId, Order orderToEdit) {
        _franchiseId = franchiseId;
        this.onOrderSaved = onOrderSaved;
        this.editingOrder = orderToEdit;
        buildForm();
        setupMockProducts();
        setupListeners();
        if (editingOrder != null) {
            fillFormForEdit();
        }
    }

    private void buildForm() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

        form.add(new JLabel("Cliente:"));
        form.add(customerCombo);

        List<Customer> customers = customerUseCase.findAll();
        customerCombo.addItem(null);
        for (Customer c : customers) {
            customerCombo.addItem(c);
        }

        customerCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Customer customer) {
                    value = customer.getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        form.add(new JLabel("Cidade:"));
        cityField.setEnabled(false);
        form.add(cityField);

        form.add(new JLabel("Estado:"));
        stateField.setEnabled(false);
        form.add(stateField);

        form.add(new JLabel("Bairro:"));
        neighborhoodField.setEnabled(false);
        form.add(neighborhoodField);

        form.add(new JLabel("Rua:"));
        streetField.setEnabled(false);
        form.add(streetField);

        form.add(new JLabel("Número:"));
        numberField.setEnabled(false);
        form.add(numberField);

        form.add(new JLabel("Vendedor:"));
        sellerField.setText(SessionManager.getLoggedUserEmail());
        sellerField.setEnabled(false);
        form.add(sellerField);

        form.add(new JLabel("Produto:"));
        form.add(productCombo);

        form.add(new JLabel("Quantidade:"));
        form.add(quantityField);

        form.add(new JLabel(""));
        form.add(addItemButton);

        panel.add(form, BorderLayout.NORTH);

        itemTableModel.setColumnIdentifiers(new String[]{"Produto", "Quantidade", "Valor Unitário", "Subtotal"});
        itemTable.setModel(itemTableModel);
        panel.add(new JScrollPane(itemTable), BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftFooter.add(removeItemButton);
        footer.add(leftFooter, BorderLayout.WEST);

        JPanel rightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightFooter.add(totalLabel);
        rightFooter.add(saveButton);
        footer.add(rightFooter, BorderLayout.EAST);

        panel.add(footer, BorderLayout.SOUTH);
    }

    private void setupMockProducts() {
        productCombo.addItem("Produto A - R$ 10.00");
        productCombo.addItem("Produto B - R$ 25.50");
        productCombo.addItem("Produto C - R$ 7.75");
    }

    private void setupListeners() {
        addItemButton.addActionListener(this::handleAddItem);
        removeItemButton.addActionListener(this::handleRemoveItem);
        saveButton.addActionListener(this::handleSaveOrder);
        customerCombo.addActionListener(e -> fillCustomerAddress());
    }

    private void fillCustomerAddress() {
        Object selected = customerCombo.getSelectedItem();
        if (selected instanceof Customer customer) {
            cityField.setText(customer.getCity());
            stateField.setText(customer.getState());
            neighborhoodField.setText(customer.getNeighborhood());
            streetField.setText(customer.getStreet());
            numberField.setText(customer.getNumber());
        } else {
            cityField.setText("");
            stateField.setText("");
            neighborhoodField.setText("");
            streetField.setText("");
            numberField.setText("");
        }
    }

    private void handleAddItem(ActionEvent e) {
        String selectedProduct = (String) productCombo.getSelectedItem();
        if (selectedProduct == null || quantityField.getText().isBlank()) {
            JOptionPane.showMessageDialog(panel, "Selecione o produto e informe a quantidade.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Quantidade inválida.");
            return;
        }

        String[] parts = selectedProduct.split("-");
        String name = parts[0];
        double price = Double.parseDouble(parts[1].replace(",", ".").replace("R$", "").trim());

        OrderItem item = new OrderItem();
        item.setId(UUID.randomUUID().toString());
        item.setProductId(name);
        item.setName(name);
        item.setQuantity(quantity);
        item.setUnitPrice(price);

        orderItems.add(item);

        itemTableModel.addRow(new Object[]{
                item.getName(),
                item.getQuantity(),
                String.format("R$ %.2f", item.getUnitPrice()),
                String.format("R$ %.2f", item.getLineTotal())
        });

        updateTotal();
        quantityField.setText("");
    }

    private void handleRemoveItem(ActionEvent e) {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow >= 0) {
            orderItems.remove(selectedRow);
            itemTableModel.removeRow(selectedRow);
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(panel, "Selecione um item para remover.");
        }
    }

    private void updateTotal() {
        double total = orderItems.stream().mapToDouble(OrderItem::getLineTotal).sum();
        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }

    private void fillFormForEdit() {
        for (int i = 0; i < customerCombo.getItemCount(); i++) {
            Customer c = customerCombo.getItemAt(i);
            if (c != null && c.getId().equals(editingOrder.getIdCustomer())) {
                customerCombo.setSelectedIndex(i);
                break;
            }
        }

        orderItems.clear();
        itemTableModel.setRowCount(0);
        for (OrderItem item : editingOrder.getItems()) {
            orderItems.add(item);
            itemTableModel.addRow(new Object[]{
                    item.getName(),
                    item.getQuantity(),
                    String.format("R$ %.2f", item.getUnitPrice()),
                    String.format("R$ %.2f", item.getLineTotal())
            });
        }
        updateTotal();
    }

    private void handleSaveOrder(ActionEvent e) {
        if (customerCombo.getSelectedItem() == null || orderItems.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Selecione um cliente e adicione pelo menos um item ao pedido.");
            return;
        }

        Customer customer = (Customer) customerCombo.getSelectedItem();
        Order order = editingOrder != null ? editingOrder : new Order();

        if (editingOrder == null) {
            order.setId(UUID.randomUUID().toString());
        }

        order.setIdCustomer(customer.getId());
        order.setIdSeller(SessionManager.getLoggedUserEmail());
        order.setIdFranchise(_franchiseId);
        order.setItems(orderItems);

        double total = orderItems.stream().mapToDouble(OrderItem::getLineTotal).sum();
        order.setTotalAmount(total);

        orderRepo.save(order);

        JOptionPane.showMessageDialog(panel, "Pedido salvo com sucesso!");
        if (onOrderSaved != null) {
            onOrderSaved.run();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
