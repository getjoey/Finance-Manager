package ca.concordia.comp5541;

import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.model.*;
import ca.concordia.comp5541.presentation.view.MainView;
import ca.concordia.comp5541.utils.EnumHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class Launcher {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {

        //load in some starting values for display
        ExpenseBusiness data = ExpenseBusiness.getInstance();

        Expense item1 = new Bill();
        item1.setPaid(false);
        item1.setDescription("Cell Phone");
        item1.setAmount(55.00);
        ((Bill) item1).setInterval(EnumHelper.intervalFromValue(3));

        Expense item2 = new Purchase();
        item2.setPaid(true);
        item2.setDescription("Dinner At Resto");
        item2.setAmount(55.00);
        ((Purchase) item2).setPaymentMethod(EnumHelper.paymentMethodFromValue(1));

        Expense item3 = new Bill();
        item3.setDescription("Videotron");
        item3.setPaid(true);
        ((Bill) item3).setInterval(EnumHelper.intervalFromValue(3));
            ArrayList<SubExpense> list = new ArrayList<SubExpense>();
            list.add(new SubExpense());
            list.get(0).setDescription("Internet");
            list.get(0).setPaid(false);
            list.get(0).setAmount(45.00);
            list.add(new SubExpense());
            list.get(1).setDescription("Home Phone");
            list.get(1).setPaid(false);
            list.get(1).setAmount(35.00);
            item3.setSubExpenses(list);

        Expense item4 = new Purchase();
        item4.setDescription("Groceries");
        item4.setPaid(false);
        ((Purchase) item4).setDueDate(new Date());
            ArrayList<SubExpense> list2 = new ArrayList<SubExpense>();
            list2.add(new SubExpense());
            list2.get(0).setDescription("Vegetables");
            list2.get(0).setPaid(true);
            list2.get(0).setAmount(45.00);
            list2.add(new SubExpense());
            list2.get(1).setDescription("Meat");
            list2.get(1).setPaid(false);
            list2.get(1).setAmount(35.00);
            list2.add(new SubExpense());
            list2.get(2).setDescription("Misc");
            list2.get(2).setPaid(false);
            list2.get(2).setAmount(25.00);
            item4.setSubExpenses(list2);

        data.save(item1);
        data.save(item2);
        data.save(item3);
        data.save(item4);



        //Main Code to start program...
        JFrame frame = new JFrame("Personal Finance Manager");
        frame.setContentPane(new MainView().getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(656, 400);
        frame.setMinimumSize(new java.awt.Dimension(656,600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);




}
}
