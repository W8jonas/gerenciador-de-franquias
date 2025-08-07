package views;

import domain.model.Order;
import domain.model.Seller;
import repository.JsonOrderRepository;
import repository.JsonSellerRepository;
import useCases.order.OrderUseCase;
import useCases.seller.SellerUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SellersPanel {
    private final JPanel panel = new JPanel(new BorderLayout());

    private final JButton createBtn = new JButton("Criar");
    private final JButton editBtn = new JButton("Editar");
    private final JButton deleteBtn = new JButton("Excluir");
    private final JButton filterBtn = new JButton("Ordenar");

    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();

    private final SellerUseCase useCase = new SellerUseCase(new JsonSellerRepository());
    private static final OrderUseCase useCaseOrder = new OrderUseCase(new JsonOrderRepository());
    private final String franchiseId;
    private final String managerEmail;
    private final TableRowSorter<DefaultTableModel> sorter;
    private boolean isSorted = false;

    public SellersPanel(String franchiseId, String managerEmail) {
        this.franchiseId = franchiseId;
        this.managerEmail = managerEmail;
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        buildToolbar();
        buildTable();
        loadSellers();

        createBtn.addActionListener(e -> onCreate());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());
        filterBtn.addActionListener(e -> onFilter());
    }

    private void buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(createBtn);
        toolbar.add(editBtn);
        toolbar.add(deleteBtn);
        toolbar.add(filterBtn);
        panel.add(toolbar, BorderLayout.NORTH);
    }

    private void buildTable() {
        model.setColumnIdentifiers(new String[]{"Email", "Nome", "Nº Vendas", "Valor Total"});
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadSellers() {
        model.setRowCount(0);
        List<Seller> sellers = useCase.findAll();
        for (Seller s : sellers) {
            if (franchiseId.equals(s.getId_franchise())) {
                List<Order> orders = useCaseOrder.findAll();
                int count = 0;
                double total = 0;

                for (Order item : orders) {
                    if (item.getIdSeller().equals(s.getEmail())) {
                        count += 1;
                        total += item.getTotalAmount();
                    }
                }

                s.setTotalSalesCount(count);
                s.setTotalSalesAmount(total);
                model.addRow(new Object[]{s.getEmail(), s.getName(), s.getTotalSalesCount(), s.getTotalSalesAmount()});
            }
        }
    }

    private void onCreate() {
        CreateSellerPanel.SellerForm form = CreateSellerPanel.show(panel);
        if (form != null) {
            try {
                Seller seller = new Seller(form.name, form.email, form.password, managerEmail, franchiseId);
                useCase.save(seller);
                JOptionPane.showMessageDialog(panel, "Vendedor criado com sucesso.");
                loadSellers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(panel, "Selecione um vendedor para editar.");
            return;
        }

        String email = (String) model.getValueAt(selected, 0);
        Seller seller = useCase.findByEmail(email);
        if (seller == null) {
            JOptionPane.showMessageDialog(panel, "Vendedor não encontrado.");
            return;
        }

        CreateSellerPanel.SellerForm form = CreateSellerPanel.show(panel, seller.getName(), seller.getEmail(), seller.getPassword());
        if (form != null) {
            try {
                seller.setName(form.name);
                seller.setPassword(form.password);
                useCase.update(seller);
                JOptionPane.showMessageDialog(panel, "Vendedor atualizado com sucesso.");
                loadSellers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDelete() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(panel, "Selecione um vendedor para excluir.");
            return;
        }

        String email = (String) model.getValueAt(selected, 0);
        int opt = JOptionPane.showConfirmDialog(panel, "Deseja realmente excluir o vendedor?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            try {
                useCase.deleteByEmail(email);
                JOptionPane.showMessageDialog(panel, "Vendedor excluído com sucesso.");
                loadSellers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onFilter() {
        if (!isSorted) {
            sorter.setComparator(3, Comparator.comparingDouble(o -> {
                if (o instanceof Number) return ((Number) o).doubleValue();
                return Double.parseDouble(o.toString());
            }));
            sorter.setSortKeys(List.of(new RowSorter.SortKey(3, SortOrder.DESCENDING)));
            isSorted = true;
        } else {
            sorter.setSortKeys(null);
            isSorted = false;
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
