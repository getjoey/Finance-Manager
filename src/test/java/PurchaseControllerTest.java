import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.controller.PurchaseController;
import ca.concordia.comp5541.model.*;
import ca.concordia.comp5541.presentation.viewmodel.PurchaseViewModel;
import ca.concordia.comp5541.utils.EnumHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.List;

public class PurchaseControllerTest {

    PurchaseController controller;
    ExpenseBusiness expenseBusiness;

    Bill bill1;
    Purchase purchase1;

    @Before
    public void before() throws Exception {
        controller = new PurchaseController();
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

        List<Purchase> purchases = expenseBusiness.fetchPurchases();
        purchases.forEach(p -> expenseBusiness.delete(p));
    }

    @Test
    public void testGetColumnsMetadata() throws Exception {
        assert controller.getColumnsMetadata().get("Payment Method") == PaymentMethod.class;
    }

    @Test
    public void testGetList() throws Exception {
        assert controller.getList().size() == 1;
    }

    @Test
    public void testSave() throws Exception {
        PurchaseViewModel purchaseVm = new PurchaseViewModel();
        purchaseVm.setPaymentMethod(EnumHelper.paymentMethodFromValue(1));
        controller.save(purchaseVm);

        assert expenseBusiness.fetchPurchases().size() == 2;
    }
} 
