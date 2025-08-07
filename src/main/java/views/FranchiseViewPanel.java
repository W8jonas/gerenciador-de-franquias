package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FranchiseViewPanel {
    private final JPanel panel = new JPanel(new BorderLayout());

    private final JButton sellersButton = new JButton("Vendedores");
    private final JButton ordersButton = new JButton("Pedidos");
    private final JButton customersButton = new JButton("Clientes");

    public FranchiseViewPanel(String franchiseId, int roleCode) {
        JLabel label = new JLabel("Franquia ID: " + franchiseId);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel;

        if (roleCode == 3) {
            buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            buttonPanel.add(ordersButton);
            buttonPanel.add(customersButton);
        } else {
            buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            buttonPanel.add(sellersButton);
            buttonPanel.add(ordersButton);
        }

        topPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);
    }

    // === Métodos públicos ===

    public JPanel getPanel() {
        return panel;
    }

    public void setVisible() {
        panel.setVisible(true);
    }

    public void setHidden() {
        panel.setVisible(false);
    }

    public void onSellersClick(ActionListener listener) {
        sellersButton.addActionListener(listener);
    }

    public void onOrdersClick(ActionListener listener) {
        ordersButton.addActionListener(listener);
    }

    public void onCustomersClick(ActionListener listener) {
        customersButton.addActionListener(listener);
    }
}
