package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FranchiseViewPanel {
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JButton sellersButton = new JButton("Vendedores");

    public FranchiseViewPanel(String franchiseId) {
        JLabel label = new JLabel("Franquia ID: " + franchiseId);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(label, BorderLayout.CENTER);
        topPanel.add(sellersButton, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
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

    public void onSellersClick(ActionListener listener) {
        sellersButton.addActionListener(listener);
    }
}
