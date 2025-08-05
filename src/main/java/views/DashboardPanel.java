package views;

import controller.FranchiseDashboardController;
import interfaces.DashboardView;
import session.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel implements DashboardView {

    private final JPanel panel = new JPanel(new BorderLayout());

    private final JButton newBtn = new JButton("New");
    private final JButton editBtn = new JButton("Edit");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton viewBtn = new JButton("View");

    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final JLabel status = new JLabel("Total: 0");

    private final FranchiseDashboardController controller = new FranchiseDashboardController(this);
    private final String managerEmail = SessionManager.getLoggedManagerEmail();

    public DashboardPanel() {
        buildToolbar();
        buildTable();
        buildStatusBar();

        newBtn.addActionListener(e -> onNew());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());
        viewBtn.addActionListener(e -> onView());

        controller.loadFranchises();
    }

    private void buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(newBtn);
        toolbar.add(editBtn);
        toolbar.add(deleteBtn);
        toolbar.add(viewBtn);
        panel.add(toolbar, BorderLayout.NORTH);
    }

    private void buildTable() {
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void buildStatusBar() {
        JPanel south = new JPanel(new BorderLayout());
        south.add(status, BorderLayout.WEST);
        panel.add(south, BorderLayout.SOUTH);
    }

    public JPanel getPanel() { return panel; }
    public void setVisible() { panel.setVisible(true); }
    public void setHidden() { panel.setVisible(false); }

    private void onNew() {
        FranchiseForm form = FranchiseForm.showNewDialog(panel);
        if (form != null) {
            controller.createFranchise(form.name, form.street, form.city, form.state, form.managerEmail);
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Selecione uma franquia para editar."); return; }

        String id = (String) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        String city = (String) model.getValueAt(row, 2);
        String state = (String) model.getValueAt(row, 3);
        String mgr = (String) model.getValueAt(row, 4);

        FranchiseForm form = FranchiseForm.showEditDialog(panel, id, name, "", city, state, mgr);
        if (form != null) {
            controller.updateFranchise(id, form.name, form.street, form.city, form.state, form.managerEmail);
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Selecione uma franquia para remover."); return; }

        String id = (String) model.getValueAt(row, 0);

        int opt = JOptionPane.showConfirmDialog(
                panel,
                "Tem certeza que deseja deletar a franquia selecionada?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );
        if (opt == JOptionPane.YES_OPTION) {
            controller.deleteFranchise(id);
        }
    }

    private void onView() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Selecione uma Franquia para visualizar.");
            return;
        }

        String id = (String) model.getValueAt(row, 0);

        JFrame frame = new JFrame("Franquia");
        FranchiseViewPanel viewPanel = new FranchiseViewPanel(id);

        viewPanel.onSellersClick(e -> {
            JFrame sellersFrame = new JFrame("Vendedores");
            SellersPanel sellersPanel = new SellersPanel(id, managerEmail);
            sellersFrame.setContentPane(sellersPanel.getPanel());
            sellersFrame.setSize(600, 400);
            sellersFrame.setLocationRelativeTo(null);
            sellersFrame.setVisible(true);
        });

        frame.setContentPane(viewPanel.getPanel());
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void onFranchisesLoaded(String[] columns, String[][] rows) {
        model.setDataVector(rows, columns);
        if (table.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(180);
        }
    }

    @Override
    public void updateTotalCount(int total) {
        status.setText("Total: " + total);
    }

    @Override
    public void showInfo(String message) {
        JOptionPane.showMessageDialog(panel, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void goToManagerPanel(String franchiseId) {
        JOptionPane.showMessageDialog(panel,
                "Open Manager Panel for franchise: " + franchiseId,
                "Navigate",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ===== Dialog interno (String-only) =====
    private static class FranchiseForm {
        String id;
        String name;
        String street;
        String city;
        String state;
        String managerEmail;

        static FranchiseForm showNewDialog(Component parent) {
            return showDialog(parent, null, "", "", "", "", "");
        }

        static FranchiseForm showEditDialog(Component parent, String id, String name, String street, String city, String state, String managerEmail) {
            return showDialog(parent, id, name, street, city, state, managerEmail);
        }

        private static FranchiseForm showDialog(Component parent, String id, String name, String street, String city, String state, String managerEmail) {
            JTextField nameField = new JTextField(name);
            JTextField streetField = new JTextField(street);
            JTextField cityField = new JTextField(city);
            JTextField stateField = new JTextField(state);
            JTextField mgrField = new JTextField(managerEmail);

            JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
            p.add(new JLabel("Name:"));         p.add(nameField);
            p.add(new JLabel("Street:"));       p.add(streetField);
            p.add(new JLabel("City:"));         p.add(cityField);
            p.add(new JLabel("State:"));        p.add(stateField);
            p.add(new JLabel("Manager Email:"));p.add(mgrField);

            int res = JOptionPane.showConfirmDialog(parent, p,
                    (id == null ? "New Franchise" : "Edit Franchise"),
                    JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                FranchiseForm f = new FranchiseForm();
                f.id = id;
                f.name = nameField.getText();
                f.street = streetField.getText();
                f.city = cityField.getText();
                f.state = stateField.getText();
                f.managerEmail = mgrField.getText();
                return f;
            }
            return null;
        }
    }
}
