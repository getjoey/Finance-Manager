package ca.concordia.comp5541.dal;

import ca.concordia.comp5541.model.Expense;

import java.util.List;

public interface IRepository<T extends Expense> {
    List<T> fetchAll();

    void save(T obj);

    boolean delete(T obj);
}
