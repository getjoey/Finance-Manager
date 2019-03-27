package ca.concordia.comp5541.dal;

import ca.concordia.comp5541.model.Expense;

import java.util.*;
import java.util.stream.Collectors;

public class Repository<T extends Expense> implements IRepository<T> {
    // To be replaced once we have a data store
    private Map<UUID, T> backingStore;

    public Repository() {
        backingStore = new HashMap<>();
    }

    public List<T> fetchAll() {
        return backingStore.values().stream().collect(Collectors.toList());
    }

    public T fetchById(UUID id) {
        return backingStore.get(id);
    }

    public void save(T obj) {
        // This is a temporary solution to side-step the fact that out records don't have database unique ids.
        if (obj.getId() == null) {
            obj.setId(UUID.randomUUID());
            backingStore.put(obj.getId(), obj);
        }

        backingStore.put(obj.getId(), obj);
    }

    public boolean delete(T obj) {
        return backingStore.remove(obj.getId()) != null;
    }
}
