import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.controller.ExpenseController;
import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.PaymentMethod;
import ca.concordia.comp5541.model.Purchase;
import ca.concordia.comp5541.model.RepeatInterval;
import ca.concordia.comp5541.presentation.viewmodel.PurchaseViewModel;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

public class ExpenseControllerTest {
    ExpenseController controller;
    ExpenseBusiness expenseBusiness;

    Bill bill1;
    Purchase purchase1;

    @Before
    public void before() throws Exception {
        controller = new ExpenseController();
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
        expenseBusiness.delete(bill1);
        expenseBusiness.delete(purchase1);
    }

    @Test
    public void testGetColumnsMetadata() throws Exception {
        assert controller.getColumnsMetadata().get("Paid") == Boolean.class;
    }

    @Test
    public void testGetList() throws Exception {
        assert controller.getList().size() == 2;
    }

    @Test
    public void testGetTotal() throws Exception {
        assert controller.getTotal().equals("$150.00");
    }

    @Test
    public void testPay() throws Exception {
        PurchaseViewModel purchaseVm = new PurchaseViewModel();
        purchaseVm.setId(purchase1.getId());
        purchaseVm.setAmount(purchase1.getAmount());
        purchaseVm.setPaymentMethod(purchase1.getPaymentMethod());

        controller.pay(purchaseVm);

        purchase1 = expenseBusiness.fetchPurchase(purchase1.getId());
        assert purchase1.getPaid();
    }

    @Test
    public void testDelete() throws Exception {
        PurchaseViewModel purchaseVm = new PurchaseViewModel();
        purchaseVm.setId(purchase1.getId());

        controller.delete(purchaseVm);

        assert expenseBusiness.fetchPurchases().size() == 0;
    }
} 
