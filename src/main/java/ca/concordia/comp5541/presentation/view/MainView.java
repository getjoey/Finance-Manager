package ca.concordia.comp5541.presentation.view;

import ca.concordia.comp5541.controller.*;;
import ca.concordia.comp5541.presentation.formatting.*;
import ca.concordia.comp5541.presentation.viewmodel.*;
import com.intellij.uiDesigner.core.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView {
    public enum FilterType {
        ALL, BILLS, PURCHASES
    }

    private ExpenseController viewController;
    private ExpenseTableViewFormatter columnFormatter;
    private ExpenseTableViewModel tableViewModel;

    public MainView() {
        buildTableController(FilterType.ALL);
        refreshExpenseTable();
        createUIComponents();
    }

    private void changeFilter(FilterType filterType) {
        buildTableController(filterType);

        refreshExpenseTable();
    }

    private void buildTableController(FilterType filterType) {
        if (filterType == FilterType.BILLS) {
            viewController = new BillController();
            columnFormatter = new BillTableViewViewFormatter(tblExpenses);
        } else if (filterType == FilterType.PURCHASES) {
            viewController = new PurchaseController();
            columnFormatter = new PurchaseTableViewFormatter(tblExpenses);
        } else {
            viewController = new ExpenseController();
            columnFormatter = new ExpenseTableViewFormatter(tblExpenses);
        }
    }

    private void refreshExpenseTable() {
        tableViewModel = new ExpenseTableViewModel(viewController.getList(), viewController.getColumnsMetadata());

        tblExpenses.setModel(tableViewModel);
        tblExpenses.createDefaultColumnsFromModel();
        columnFormatter.format();

        lblTotal.setText(viewController.getTotal());
    }

    private void onTableSelectionChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            boolean hasSelection = !tblExpenses.getSelectionModel().isSelectionEmpty();

            btnEdit.setEnabled(hasSelection);
            btnDelete.setEnabled(hasSelection);

            int selectedIndex = tblExpenses.getSelectedRow();
            if (selectedIndex >= 0) {
                boolean isPaid =
                        tableViewModel.getDataAtRow(tblExpenses.convertRowIndexToModel(selectedIndex)).getPaid();
                btnMarkAsPaid.setEnabled(hasSelection && !isPaid);
            }
        } else {
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
            btnMarkAsPaid.setEnabled(false);
        }
    }

    private void newBill() {
        BillViewModel newBill = new BillViewModel();
        openBillDialog(newBill);
    }

    private void newPurchase() {
        PurchaseViewModel newPurchase = new PurchaseViewModel();
        openPurchaseDialog(newPurchase);
    }

    private void editExpense() {
        int selectedIndex = tblExpenses.convertRowIndexToModel(tblExpenses.getSelectedRow());
        ExpenseViewModel current = tableViewModel.getDataAtRow(selectedIndex);

        if (current instanceof BillViewModel) {
            openBillDialog((BillViewModel) current);
        } else {
            openPurchaseDialog((PurchaseViewModel) current);
        }
    }

    private void deleteExpense() {
        int selectedIndex = tblExpenses.convertRowIndexToModel(tblExpenses.getSelectedRow());
        ExpenseViewModel current = tableViewModel.getDataAtRow(selectedIndex);

        if (viewController.delete(current)) {
            refreshExpenseTable();
        }
    }

    private void markAsPaid() {
        int selectedIndex = tblExpenses.convertRowIndexToModel(tblExpenses.getSelectedRow());
        ExpenseViewModel current = tableViewModel.getDataAtRow(selectedIndex);
        viewController.pay(current);

        refreshExpenseTable();
    }

    private void openBillDialog(BillViewModel current) {
        BillEditorView dialog = new BillEditorView(current, e -> refreshExpenseTable());

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void openPurchaseDialog(PurchaseViewModel current) {
        PurchaseEditorView dialog = new PurchaseEditorView(current, e -> refreshExpenseTable());

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        tblExpenses.setAutoCreateRowSorter(true);
        tblExpenses.getRowSorter().toggleSortOrder(0);
        tblExpenses.getSelectionModel().addListSelectionListener(e -> onTableSelectionChanged(e));

        btnNewBill.addActionListener(e -> newBill());
        btnNewPurchase.addActionListener(e -> newPurchase());
        btnEdit.addActionListener(e -> editExpense());
        btnDelete.addActionListener(e -> deleteExpense());
        btnMarkAsPaid.addActionListener(e -> markAsPaid());

        optExpenses.addActionListener(e -> changeFilter(FilterType.ALL));
        optBills.addActionListener(e -> changeFilter(FilterType.BILLS));
        optPurchases.addActionListener(e -> changeFilter(FilterType.PURCHASES));

        tblExpenses.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();

                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    editExpense();
                }
            }
        });
    }

    private JRadioButton optExpenses;
    private JRadioButton optBills;
    private JRadioButton optPurchases;
    private JTable tblExpenses;
    private JPanel contentPane;
    private JPanel footerPanel;
    private JPanel filterPanel;
    private JButton btnNewBill;
    private JButton btnNewPurchase;
    private JButton btnEdit;
    private JButton btnDelete;
    private JToolBar mainToolbar;
    private JLabel lblTotal;
    private JButton btnMarkAsPaid;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tblExpenses = new JTable();
        scrollPane1.setViewportView(tblExpenses);
        footerPanel = new JPanel();
        footerPanel.setLayout(new GridLayoutManager(1, 3, new Insets(4, 10, 0, 0), -1, -1));
        contentPane.add(footerPanel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 28), null, new Dimension(-1, 28), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Total:");
        footerPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTotal = new JLabel();
        Font lblTotalFont = this.$$$getFont$$$(null, Font.BOLD, -1, lblTotal.getFont());
        if (lblTotalFont != null) lblTotal.setFont(lblTotalFont);
        lblTotal.setText("0");
        footerPanel.add(lblTotal, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        footerPanel.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayoutManager(1, 4, new Insets(5, 10, 5, 10), -1, -1));
        contentPane.add(filterPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        optExpenses = new JRadioButton();
        optExpenses.setSelected(true);
        optExpenses.setText("All Expenses");
        filterPanel.add(optExpenses, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        filterPanel.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        optBills = new JRadioButton();
        optBills.setText("Bills");
        filterPanel.add(optBills, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        optPurchases = new JRadioButton();
        optPurchases.setText("Purchases");
        filterPanel.add(optPurchases, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainToolbar = new JToolBar();
        mainToolbar.setFloatable(false);
        mainToolbar.setMargin(new Insets(20, 20, 20, 20));
        contentPane.add(mainToolbar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 40), null, null, 0, false));
        mainToolbar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setMaximumSize(new Dimension(5, 2147483647));
        panel1.setOpaque(false);
        mainToolbar.add(panel1);
        btnNewBill = new JButton();
        btnNewBill.setMargin(new Insets(5, 5, 5, 5));
        btnNewBill.setText("New Bill...");
        mainToolbar.add(btnNewBill);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setMaximumSize(new Dimension(5, 2147483647));
        panel2.setOpaque(false);
        mainToolbar.add(panel2);
        btnNewPurchase = new JButton();
        btnNewPurchase.setMargin(new Insets(5, 5, 5, 5));
        btnNewPurchase.setText("New Purchase...");
        mainToolbar.add(btnNewPurchase);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setMaximumSize(new Dimension(10, 2147483647));
        panel3.setOpaque(false);
        mainToolbar.add(panel3);
        btnEdit = new JButton();
        btnEdit.setEnabled(false);
        btnEdit.setMargin(new Insets(5, 5, 5, 5));
        btnEdit.setText("Edit");
        mainToolbar.add(btnEdit);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setMaximumSize(new Dimension(5, 2147483647));
        panel4.setOpaque(false);
        mainToolbar.add(panel4);
        btnDelete = new JButton();
        btnDelete.setEnabled(false);
        btnDelete.setMargin(new Insets(5, 5, 5, 5));
        btnDelete.setText("Delete");
        mainToolbar.add(btnDelete);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setMaximumSize(new Dimension(10, 2147483647));
        panel5.setOpaque(false);
        mainToolbar.add(panel5);
        btnMarkAsPaid = new JButton();
        btnMarkAsPaid.setEnabled(false);
        btnMarkAsPaid.setLabel("Mark As Paid");
        btnMarkAsPaid.setMargin(new Insets(5, 5, 5, 5));
        btnMarkAsPaid.setText("Mark As Paid");
        mainToolbar.add(btnMarkAsPaid);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(optPurchases);
        buttonGroup.add(optBills);
        buttonGroup.add(optExpenses);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
