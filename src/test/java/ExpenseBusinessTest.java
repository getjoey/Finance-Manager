import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.Purchase;
import ca.concordia.comp5541.model.SubExpense;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

public class ExpenseBusinessTest {
    ExpenseBusiness expenseBusiness;
    Bill bill1;
    Bill bill2;
    Purchase purchase1;
    Purchase purchase2;
    ArrayList<SubExpense> subExpenses;

    @Before
    public void before() throws Exception {
        expenseBusiness = ExpenseBusiness.getInstance();

        bill1 = new Bill();
        bill1.setAmount(100);// will be overwritten by subexpenses
        subExpenses = bill1.getSubExpenses();
        subExpenses.add(new SubExpense());
        subExpenses.get(0).setAmount(24.44);
        subExpenses.get(0).setPaid(false); // will force parent paid to be false

        bill2 = new Bill();
        bill2.setAmount(200);
        bill2.setPaid(true);

        purchase1 = new Purchase();
        purchase1.setAmount(50);

        purchase2 = new Purchase();// will be overwritten by subexpenses
        purchase2.setAmount(55.55);
        purchase2.setPaid(true);
        subExpenses = purchase2.getSubExpenses();
        subExpenses.add(new SubExpense());
        subExpenses.get(0).setAmount(24.44);
        subExpenses.get(0).setPaid(false); // will be overwritten by parent (since parent is paid)

        expenseBusiness.save(bill1);
        expenseBusiness.save(bill2);
        expenseBusiness.save(purchase1);
        expenseBusiness.save(purchase2);
    }

    @After
    public void after() throws Exception {
        expenseBusiness.delete(bill1);
        expenseBusiness.delete(bill2);
        expenseBusiness.delete(purchase1);
        expenseBusiness.delete(purchase2);
    }


    @Test
    public void testFetchBills() throws Exception {
        List<Bill> bills = expenseBusiness.fetchBills();
        assert bills.size() == 2;
    }

    @Test
    public void testFetchBill() throws Exception {
        //test overwriting amount
        Bill bill = expenseBusiness.fetchBill(bill1.getId());
        assert bill.getAmount() == 24.44;

        //test overwriting paid of parent
        assert bill.getPaid() == false;

        //regular test
        bill = expenseBusiness.fetchBill(bill2.getId());
        assert bill.getAmount() == 200;
        assert bill.getPaid() == true;

        //test overwriting paid of child
        Purchase purchase = expenseBusiness.fetchPurchase(purchase2.getId());
        assert purchase.getSubExpenses().get(0).getPaid() == true;



    }

    @Test
    public void testFetchPurchases() throws Exception {
        List<Purchase> purchases = expenseBusiness.fetchPurchases();
        assert purchases.size() == 2;
    }

    @Test
    public void testFetchPurchase() throws Exception {
        Purchase purchase = expenseBusiness.fetchPurchase(purchase1.getId());
        assert purchase.getAmount() == 50;

         purchase = expenseBusiness.fetchPurchase(purchase2.getId());
        assert purchase.getAmount() == 24.44;
    }

    @Test
    public void testDelete() throws Exception {
        expenseBusiness.delete(bill1);
        expenseBusiness.delete(bill2);
        expenseBusiness.delete(purchase1);
    }

} 
