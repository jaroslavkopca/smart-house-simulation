package cz.cvut.fel.omo.config.houseBuilder;

import cz.cvut.fel.omo.config.roomFactory.AutomatedRoomFactory;
import cz.cvut.fel.omo.config.roomFactory.RoomFactory;
import cz.cvut.fel.omo.model.house.Floor;
import cz.cvut.fel.omo.model.house.House;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.utils.RoomType;

public class HouseBuilder {

    private final House house;
    private final AutomatedRoomFactory roomFactory;

    public HouseBuilder(AutomatedRoomFactory factory, int peopleCapacity) {
        house = new House(peopleCapacity);
        this.roomFactory = factory;
    }

    public HouseBuilder addFloor() {
        Floor floor = new Floor();
        house.addFloor(floor);
        return this;
    }

    public HouseBuilder addRoom(RoomType type, int floorIndex, int peopleCapacity) {
        Floor floor = house.getFloors().get(floorIndex);
        Room room = roomFactory.createRoom(type, floorIndex, peopleCapacity);
        floor.addRoom(room);
        return this;
    }

    public House build() {
        return house;
    }
}
