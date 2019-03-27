import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.controller.BillController;
import ca.concordia.comp5541.model.*;
import ca.concordia.comp5541.presentation.viewmodel.BillViewModel;
import ca.concordia.comp5541.utils.EnumHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;


public class BillControllerTest {

    BillController controller;
    ExpenseBusiness expenseBusiness;

    Bill bill1;
    Purchase purchase1;
    ArrayList<SubExpense> subExpenses;

    @Before
    public void before() throws Exception {
        controller = new BillController();
        expenseBusiness = ExpenseBusiness.getInstance();

        bill1 = new Bill();
        bill1.setInterval(RepeatInterval.ANNUALLY);
        bill1.setAmount(100);
        subExpenses = bill1.getSubExpenses();
        subExpenses.add(new SubExpense());
        subExpenses.get(0).setAmount(24.44);


        purchase1 = new Purchase();
        purchase1.setPaymentMethod(PaymentMethod.CREDIT);
        purchase1.setAmount(50);
        subExpenses = purchase1.getSubExpenses();
        subExpenses.add(new SubExpense());
        subExpenses.get(0).setAmount(24.44);


        expenseBusiness.save(bill1);
        expenseBusiness.save(purchase1);
    }

    @After
    public void after() throws Exception {
        expenseBusiness.delete(purchase1);

        List<Bill> bills = expenseBusiness.fetchBills();
        bills.forEach(b -> expenseBusiness.delete(b));
    }

    @Test
    public void testGetColumnsMetadata() throws Exception {
        assert controller.getColumnsMetadata().get("Interval") == RepeatInterval.class;
    }

    @Test
    public void testGetSubExpenses() throws Exception{
        assert controller.getList().get(0).getSubExpenses().size() == 1;
    }

    @Test
    public void testGetList() throws Exception {
        assert controller.getList().size() == 1;
    }

    @Test
    public void testSave() throws Exception {
        BillViewModel billVm = new BillViewModel();
        billVm.setInterval(EnumHelper.intervalFromValue(1));
        controller.save(billVm);

        assert expenseBusiness.fetchBills().size() == 2;
    }
} 
