package cz.cvut.fel.omo.config.houseBuilder;

import cz.cvut.fel.omo.config.roomFactory.AutomatedRoomFactory;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.House;

public class HouseDirector {

    public House buildHouse(int peopleCapacity) {
        if (peopleCapacity > 21) {
            throw new IllegalStateException("Our house is meant for upmost 21 people.");
        }
        AutomatedRoomFactory roomFactory = new AutomatedRoomFactory();
        HouseBuilder builder = new HouseBuilder(roomFactory, peopleCapacity);
        int floorIndex = 0;
        // basic one-person house setup
        builder.addFloor()
                .addRoom(RoomType.HALL, floorIndex, peopleCapacity)
                .addRoom(RoomType.GARAGE, floorIndex, peopleCapacity)
                .addRoom(RoomType.KITCHEN, floorIndex, peopleCapacity)
                .addRoom(RoomType.LIVING_ROOM, floorIndex, peopleCapacity)
                .addRoom(RoomType.BEDROOM, floorIndex, peopleCapacity);
        int roomsAdded = 5;
        peopleCapacity--;
        while (peopleCapacity > 0){
            if (roomsAdded == 5){
                builder.addFloor();
                roomsAdded = 0;
                floorIndex++;
                builder.addRoom(RoomType.HALL, floorIndex, peopleCapacity);
                roomsAdded++;
            }
            builder.addRoom(RoomType.BEDROOM, floorIndex, peopleCapacity);
            roomsAdded++;
            peopleCapacity--;
        }
        return builder.build();
    }
}
