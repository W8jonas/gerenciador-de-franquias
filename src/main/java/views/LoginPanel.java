package views;

import controller.LoginController;
import domain.model.Seller;
import interfaces.CallbackInterface;
import interfaces.LoginView;
import repository.JsonSellerRepository;
import repository.SellerRepository;
import session.SessionManager;
import useCases.seller.SellerUseCase;

import javax.swing.*;
import java.awt.*;

public class LoginPanel implements LoginView {
    private final JPanel panel = new JPanel(new BorderLayout());
    private CallbackInterface callback;

    private final LoginController controller = new LoginController(this);

    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    private final SellerUseCase useCase = new SellerUseCase(new JsonSellerRepository());

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
        SessionManager.setLoggedUserEmail(email);

        if (roleCode == 3) {
            String email_seller = emailField.getText();
            Seller seller = useCase.findByEmail(email);
            String franchiseId = seller.getId_franchise();

            // Seller → abrir painel da franquia direto
            JFrame frame = new JFrame("Painel da Franquia");

            // ID fixo "1" apenas como exemplo — ideal seria obter da associação real do vendedor
            FranchiseViewPanel viewPanel = new FranchiseViewPanel(franchiseId, roleCode);

            frame.setContentPane(viewPanel.getPanel());
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            viewPanel.onCustomersClick(e -> {
                JFrame frameb = new JFrame("Clientes");
                CustomersPanel customersPanel = new CustomersPanel(franchiseId);
                frameb.setContentPane(customersPanel.getPanel());
                frameb.setSize(600, 400);
                frameb.setLocationRelativeTo(null);
                frameb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameb.setVisible(true);
            });

            viewPanel.onOrdersClick(e -> {
                JFrame frameb = new JFrame("Pedidos");
                OrdersPanel customersPanel = new OrdersPanel(franchiseId);
                frameb.setContentPane(customersPanel.getPanel());
                frameb.setSize(600, 400);
                frameb.setLocationRelativeTo(null);
                frameb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameb.setVisible(true);
            });

        } else {
            // Dono ou gerente → segue fluxo normal
            if (callback != null) {
                callback.run(roleCode);
            }
        }
    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
