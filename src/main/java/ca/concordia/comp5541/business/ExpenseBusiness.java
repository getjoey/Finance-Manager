package ca.concordia.comp5541.business;

import ca.concordia.comp5541.dal.Repository;
import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.Expense;
import ca.concordia.comp5541.model.Purchase;

import java.util.List;
import java.util.UUID;

public class ExpenseBusiness {
    private static ExpenseBusiness instance;

    private Repository<Bill> billRepository;
    private Repository<Purchase> purchaseRepository;

    // To make sure the class is sealed to outside instantiation.
    private ExpenseBusiness() {
        billRepository = new Repository<>();
        purchaseRepository = new Repository<>();
    }

    static {
        try {
            if (instance == null) {
                instance = new ExpenseBusiness();
            }
        } catch (Exception ex) {

        }
    }

    public static ExpenseBusiness getInstance() {
        return instance;
    }

    public List<Bill> fetchBills() {
        return billRepository.fetchAll();
    }

    public Bill fetchBill(UUID id) {
        return billRepository.fetchById(id);
    }

    public List<Purchase> fetchPurchases() {
        return purchaseRepository.fetchAll();
    }

    public Purchase fetchPurchase(UUID id) {
        return purchaseRepository.fetchById(id);
    }

    public void save(Expense expense) {

        setProperValues(expense); // inc 2 addition

        if (expense instanceof Bill) {
            billRepository.save((Bill)expense);
        } else {
            purchaseRepository.save((Purchase)expense);
        }
    }

    public boolean delete(Expense expense) {
        if (expense instanceof Bill) {
            return billRepository.delete((Bill)expense);
        } else {
            return purchaseRepository.delete((Purchase)expense);
        }
    }

    //inc 2 addition
    //sets proper values... upon save. overwrites Expense if subexpense are not all paid, and sets amount to amount of all subexpenses
    public void setProperValues(Expense expense){

        if(expense.getSubExpenses().size() > 0){
            //if parent is paid... set all children to paid
            if(expense.getPaid() == true){
                for(int i = 0; i<expense.getSubExpenses().size(); i++){
                    expense.getSubExpenses().get(i).setPaid(true);
                }
            }
            //set amount to amount of children...
            //if all children are paid set parent to paid
            double amount = 0.00;
            boolean paid = true;
            for(int i = 0; i<expense.getSubExpenses().size(); i++){

                amount = amount + expense.getSubExpenses().get(i).getAmount();

                if(expense.getSubExpenses().get(i).getPaid() == false){
                    paid = false;
                }
            }
            expense.setAmount(amount);
            expense.setPaid(paid);

        }

    }


}
