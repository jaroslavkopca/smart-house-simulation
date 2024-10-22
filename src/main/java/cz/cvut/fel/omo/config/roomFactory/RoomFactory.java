package cz.cvut.fel.omo.config.roomFactory;

import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;

public interface RoomFactory {
    Room createRoom(RoomType roomType, int floorIndex, int peopleCapacity);
}
