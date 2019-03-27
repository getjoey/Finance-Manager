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
}
