package cz.cvut.fel.omo.model.house;

import cz.cvut.fel.omo.model.utils.Cabinet;
import cz.cvut.fel.omo.model.utils.RoomType;

import java.util.function.Supplier;

public class StorageRoom<T> extends Room{

    private final Cabinet<T> storageCabinet;

    public StorageRoom(RoomType type, int size, Supplier<T> itemSupplier) {
        super(type);
        this.storageCabinet = new Cabinet<>(size, itemSupplier);
    }

    public Cabinet<T> getStorageCabinet() {
        return storageCabinet;
    }

}
