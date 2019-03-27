import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.Purchase;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.List;

public class ExpenseBusinessTest {
    ExpenseBusiness expenseBusiness;
    Bill bill1;
    Bill bill2;
    Purchase purchase1;

    @Before
    public void before() throws Exception {
        expenseBusiness = ExpenseBusiness.getInstance();

        bill1 = new Bill();
        bill1.setAmount(100);

        bill2 = new Bill();
        bill2.setAmount(200);

        purchase1 = new Purchase();
        purchase1.setAmount(50);

        expenseBusiness.save(bill1);
        expenseBusiness.save(bill2);
        expenseBusiness.save(purchase1);
    }

    @After
    public void after() throws Exception {
        expenseBusiness.delete(bill1);
        expenseBusiness.delete(bill2);
        expenseBusiness.delete(purchase1);
    }


    @Test
    public void testFetchBills() throws Exception {
        List<Bill> bills = expenseBusiness.fetchBills();
        assert bills.size() == 2;
    }

    @Test
    public void testFetchBill() throws Exception {
        Bill bill = expenseBusiness.fetchBill(bill1.getId());
        assert bill.getAmount() == 100;
    }

    @Test
    public void testFetchPurchases() throws Exception {
        List<Purchase> purchases = expenseBusiness.fetchPurchases();
        assert purchases.size() == 1;
    }

    @Test
    public void testFetchPurchase() throws Exception {
        Purchase purchase = expenseBusiness.fetchPurchase(purchase1.getId());
        assert purchase.getAmount() == 50;
    }

    @Test
    public void testDelete() throws Exception {
        expenseBusiness.delete(bill1);
        expenseBusiness.delete(bill2);
        expenseBusiness.delete(purchase1);
    }
} 
