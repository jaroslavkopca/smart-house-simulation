package cz.cvut.fel.omo.model.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Cabinet<T> {
    private final int size;
    private final List<T> items;

    public Cabinet(int size, Supplier<T> itemSupplier) {
        this.size = size;
        this.items = new ArrayList<>(size);
        fillWithItems(itemSupplier);
    }

    private void fillWithItems(Supplier<T> itemSupplier) {
        while (items.size() < size) {
            items.add(itemSupplier.get());
        }
    }

    public boolean isOneItemAvailable() {
        return !items.isEmpty();
    }

    public T takeItem() {
        if (isOneItemAvailable()) {
            return items.remove(0);
        } else {
            throw new IllegalStateException("No items available in the cabinet");
        }
    }

    public void returnItem(T item) {
        if (items.size() < size) {
            items.add(item);
        } else {
            throw new IllegalStateException("Cabinet is full");
        }
    }
}
