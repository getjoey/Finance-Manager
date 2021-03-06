package ca.concordia.comp5541.presentation.view;

import ca.concordia.comp5541.controller.BillController;
import ca.concordia.comp5541.model.SubExpense;
import ca.concordia.comp5541.model.RepeatInterval;
import ca.concordia.comp5541.presentation.formatting.SubExpenseTableViewFormatter;
import ca.concordia.comp5541.presentation.viewmodel.BillViewModel;
import ca.concordia.comp5541.utils.EnumHelper;
import ca.concordia.comp5541.utils.FormatHelper;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import ca.concordia.comp5541.presentation.viewmodel.SubExpenseTableViewModel;

public class BillEditorView extends JDialog {
    private final ActionListener listener;
    private final BillViewModel viewModel;

    public BillEditorView(BillViewModel viewModel, ActionListener listener) {
        this.viewModel = viewModel;
        this.listener = listener;

        createUIComponents();
        bindForm();

        //new inc 2 code
        updateFrameValues();
        buildTable();
        refreshTable();
    }

    private void bindForm() {
        dpkDate.setDate(viewModel.getDate());
        if (txtDescription.getText().equals("")) {
            txtDescription.setText(viewModel.getDescription());
        }
        txtAmount.setValue(viewModel.getAmount());
        chkPaid.setSelected(viewModel.getPaid());
        cboRepeats.setSelectedIndex(viewModel.getInterval().getValue());
    }


    private void onOK(ActionEvent e) {
        viewModel.setDate(dpkDate.getDate());
        viewModel.setDescription(txtDescription.getText());
        viewModel.setPaid(chkPaid.isSelected());
        viewModel.setAmount(((Number) txtAmount.getValue()).doubleValue());
        viewModel.setInterval(EnumHelper.intervalFromValue(cboRepeats.getSelectedIndex()));

        updateFrameValues();

        new BillController().save(viewModel);

        listener.actionPerformed(e);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtDescription;
    private JXDatePicker dpkDate;
    private JFormattedTextField txtAmount;
    private JComboBox cboRepeats;
    private JCheckBox chkPaid;
    private JButton addCompositeButton;
    private JTable table1;
    private JButton editComposite;
    private JButton removeComposite;

    private void createUIComponents() {
        setResizable(false);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK(e));
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        dpkDate.setFormats(new SimpleDateFormat("MMMM dd, yyyy"));
        dpkDate.getEditor().setEditable(false);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                txtDescription.requestFocus();
            }
        });

        DefaultComboBoxModel<RepeatInterval> intervalModel =
                new DefaultComboBoxModel<>(RepeatInterval.values());
        cboRepeats.setModel(intervalModel);

        txtAmount.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                return FormatHelper.getCurrencyFormatter();
            }
        });


        //new code inc 2
        addCompositeButton.addActionListener(e -> onaddCompositeButton());
        editComposite.addActionListener(e -> onEditComposite());
        removeComposite.addActionListener(e -> onRemoveComposite());
        chkPaid.addActionListener(e -> markAllAsPaid());

        table1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();

                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    onEditComposite();
                }
            }
        });

        this.setTitle("Bill Editor");

        refreshTable();
    }

    //new code inc 2
    //----------alllllll this below
    //----------------------
    private void onaddCompositeButton() {
        openCompositeDialog();
        updateFrameValues();
    }

    private void openCompositeDialog() {
        SubExpense current = new SubExpense();
        if (chkPaid.isSelected()) {
            current.setPaid(true);
        }
        SubExpenseEditorView dialog = new SubExpenseEditorView(this.viewModel, current);
        dialog.setTitle("Sub-Item");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);


        refreshTable();

        //to update total field in bill editor
        updateFrameValues();
    }

    private void markAllAsPaid() {
        //if theres composites mark them all as paid
        for (int i = 0; i < viewModel.getSubExpenses().size(); i++) {
            viewModel.getSubExpenses().get(i).setPaid(true);
            updateFrameValues();
            refreshTable();
        }

    }

    private void onEditComposite() {
        int selectedIndex = table1.convertRowIndexToModel(table1.getSelectedRow());

        if (selectedIndex != -1) {
            SubExpense current = tableViewModel.getDataAtRow(selectedIndex);
            SubExpenseEditorView dialog = new SubExpenseEditorView(this.viewModel, current);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            refreshTable();
            //to update total field in bill editor
            updateFrameValues();
        }
    }

    private void onRemoveComposite() {
        int selectedIndex = table1.convertRowIndexToModel(table1.getSelectedRow());

        if (selectedIndex != -1) {
            viewModel.getSubExpenses().remove(selectedIndex);
            refreshTable();
            updateFrameValues();
        }
    }

    private void updateFrameValues() {
        //handles the paid option... ie, if theres composites and all are paid, then parent must be paid. and disables it.
        chkPaid.setEnabled(true);
        if (viewModel.getSubExpenses().size() > 0) {
            //chkPaid.setEnabled(false);
            boolean paid = true;
            for (int i = 0; i < viewModel.getSubExpenses().size(); i++) {
                if (!viewModel.getSubExpenses().get(i).getPaid()) {
                    paid = false;
                    viewModel.setPaid(false);
                    chkPaid.setSelected(viewModel.getPaid());
                }
            }
            if (paid) {
                viewModel.setPaid(true);
                chkPaid.setSelected(viewModel.getPaid());
                chkPaid.setEnabled(false); //disable it
            }
        }

        //update amount
        viewModel.setAmount(((Number) txtAmount.getValue()).doubleValue()); //if there are composites this will update to the sum of all composites instead!
        bindForm();
    }


    private SubExpenseTableViewModel tableViewModel = new SubExpenseTableViewModel();

    private void buildTable() {
        SubExpenseTableViewFormatter columnFormatter;
        columnFormatter = new SubExpenseTableViewFormatter(table1);
        table1.setModel(tableViewModel);
        table1.createDefaultColumnsFromModel();
        columnFormatter.format();
    }

    private void refreshTable() {
        tableViewModel.bindTableData(this.viewModel.getSubExpenses());
    }
    //----------------


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
        contentPane.setLayout(new GridLayoutManager(10, 4, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setMaximumSize(new Dimension(2147483647, 250));
        contentPane.setMinimumSize(new Dimension(474, 230));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, new Dimension(450, -1), new Dimension(450, 10), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        txtDescription = new JTextField();
        panel3.add(txtDescription, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Description");
        panel3.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Date");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dpkDate = new JXDatePicker();
        dpkDate.setEditable(true);
        panel3.add(dpkDate, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(120, -1), null, null, 0, false));
        txtAmount = new JFormattedTextField();
        panel3.add(txtAmount, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Amount");
        panel3.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cboRepeats = new JComboBox();
        panel3.add(cboRepeats, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Repeats");
        panel3.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chkPaid = new JCheckBox();
        chkPaid.setText("");
        panel3.add(chkPaid, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Paid");
        panel3.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Sub-Items List");
        contentPane.add(label6, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, new GridConstraints(3, 3, 7, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 350), new Dimension(-1, 350), 0, false));
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
        addCompositeButton = new JButton();
        addCompositeButton.setText("Add");
        contentPane.add(addCompositeButton, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        contentPane.add(spacer3, new GridConstraints(2, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        contentPane.add(spacer4, new GridConstraints(7, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        removeComposite = new JButton();
        removeComposite.setText("Remove");
        contentPane.add(removeComposite, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editComposite = new JButton();
        editComposite.setText("Edit");
        contentPane.add(editComposite, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
