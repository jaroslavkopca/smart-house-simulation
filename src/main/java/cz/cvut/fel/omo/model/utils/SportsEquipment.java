package cz.cvut.fel.omo.model.utils;

public class SportsEquipment {
    private static int idCounter = 0;

    private final int id;

    public SportsEquipment() {
        this.id = ++idCounter;
    }

    public int getId() {
        return id;
    }
}
