package views;

import controller.ManagerDashboardController;
import interfaces.ManagerDashboardView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManagerDashboardPanel implements ManagerDashboardView {

    private final JPanel panel = new JPanel(new BorderLayout());

    private final JButton newBtn = new JButton("New");
    private final JButton editBtn = new JButton("Edit");
    private final JButton deleteBtn = new JButton("Delete");

    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final JLabel status = new JLabel("Total: 0");

    // controller hardcoded
    private final ManagerDashboardController controller = new ManagerDashboardController(this);

    public ManagerDashboardPanel() {
        buildToolbar();
        buildTable();
        buildStatusBar();

        newBtn.addActionListener(e -> onNew());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());

        controller.loadManagers();
    }

    private void buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(newBtn);
        toolbar.add(editBtn);
        toolbar.add(deleteBtn);
        panel.add(toolbar, BorderLayout.NORTH);
    }

    private void buildTable() {
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);
    }

    private void buildStatusBar() {
        JPanel south = new JPanel(new BorderLayout());
        south.add(status, BorderLayout.WEST);
        panel.add(south, BorderLayout.SOUTH);
    }

    // ==== ações da view ====

    private void onNew() {
        ManagerForm f = ManagerForm.showNewDialog(panel);
        if (f != null) {
            controller.createManager(f.name, f.email, f.password, f.active);
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Selecione um gerente para editar ."); return; }

        String id = (String) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        String email = (String) model.getValueAt(row, 2);
        boolean active = Boolean.parseBoolean((String) model.getValueAt(row, 3));

        ManagerForm f = ManagerForm.showEditDialog(panel, id, name, email, "", active);
        if (f != null) {
            controller.updateManager(id, f.name, f.email, f.password, f.active);
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Selecione um gerente para remover."); return; }

        String id = (String) model.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(panel, "Remover gerente?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            controller.deleteManager(id);
        }
    }

    @Override
    public void onManagersLoaded(String[] columns, String[][] rows) {
        model.setDataVector(rows, columns);
        if (table.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(180); // id
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
        JOptionPane.showMessageDialog(panel, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // ==== API pública (padrão dos seus panels) ====
    public JPanel getPanel() { return panel; }
    public void setVisible() { panel.setVisible(true); }
    public void setHidden() { panel.setVisible(false); }

    // ==== dialog interno simples ====
    private static class ManagerForm {
        String id;
        String name;
        String email;
        String password;
        boolean active;

        static ManagerForm showNewDialog(Component parent) {
            return showDialog(parent, null, "", "", "", true);
        }
        static ManagerForm showEditDialog(Component parent, String id, String name, String email, String password, boolean active) {
            return showDialog(parent, id, name, email, password, active);
        }

        private static ManagerForm showDialog(Component parent, String id, String name, String email, String password, boolean active) {
            JTextField nameField = new JTextField(name);
            JTextField emailField = new JTextField(email);
            JPasswordField passField = new JPasswordField(password);
            JCheckBox activeBox = new JCheckBox("Active", active);

            JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
            p.add(new JLabel("Nome:"));        p.add(nameField);
            p.add(new JLabel("Email:"));       p.add(emailField);
            p.add(new JLabel("Senha:"));    p.add(passField);
            p.add(new JLabel(" "));            p.add(activeBox);

            int res = JOptionPane.showConfirmDialog(parent, p,
                    (id == null ? "Novo gerente" : "Editar gerente"),
                    JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                ManagerForm f = new ManagerForm();
                f.id = id;
                f.name = nameField.getText();
                f.email = emailField.getText();
                f.password = new String(passField.getPassword());
                f.active = activeBox.isSelected();
                return f;
            }
            return null;
        }
    }
}
