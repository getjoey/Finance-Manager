import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.controller.BillController;
import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.PaymentMethod;
import ca.concordia.comp5541.model.Purchase;
import ca.concordia.comp5541.model.RepeatInterval;
import ca.concordia.comp5541.presentation.viewmodel.BillViewModel;
import ca.concordia.comp5541.utils.EnumHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.List;


public class BillControllerTest {

    BillController controller;
    ExpenseBusiness expenseBusiness;

    Bill bill1;
    Purchase purchase1;

    @Before
    public void before() throws Exception {
        controller = new BillController();
        expenseBusiness = ExpenseBusiness.getInstance();

        bill1 = new Bill();
        bill1.setInterval(RepeatInterval.ANNUALLY);
        bill1.setAmount(100);

        purchase1 = new Purchase();
        purchase1.setPaymentMethod(PaymentMethod.CREDIT);
        purchase1.setAmount(50);

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
