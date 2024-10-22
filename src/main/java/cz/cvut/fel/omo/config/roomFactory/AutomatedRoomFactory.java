package cz.cvut.fel.omo.config.roomFactory;

import cz.cvut.fel.omo.config.roomStrategy.*;
import cz.cvut.fel.omo.model.device.devices.Containable.CoffeeMaker;
import cz.cvut.fel.omo.model.device.devices.Containable.Feeder;
import cz.cvut.fel.omo.model.device.devices.Containable.Refrigirator;
import cz.cvut.fel.omo.model.device.devices.Containable.WashingMachine;
import cz.cvut.fel.omo.model.device.devices.GarageDoor;
import cz.cvut.fel.omo.model.device.devices.RoboticVacuumCleaner;
import cz.cvut.fel.omo.model.house.GeneralRoom;
import cz.cvut.fel.omo.model.house.StorageRoom;
import cz.cvut.fel.omo.model.utils.Cabinet;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.utils.SportsEquipment;

import java.util.*;

public class AutomatedRoomFactory implements RoomFactory{
    private final List<RoomConfigurationStrategy> strategies;

    public AutomatedRoomFactory(){
        List<RoomType> allRooms = Arrays.asList(RoomType.values());
        List<RoomType> outerRooms = Arrays.asList(RoomType.LIVING_ROOM, RoomType.BEDROOM, RoomType.KITCHEN);
        List<RoomType> livingQuarters = Arrays.asList(RoomType.LIVING_ROOM, RoomType.BEDROOM, RoomType.KITCHEN, RoomType.HALL);
        strategies = new ArrayList<>();
        strategies.add(new WindowableRoomStrategy(outerRooms));
        strategies.add(new HeatableRoomStrategy(livingQuarters));
        strategies.add(new IlluminableRoomStrategy(allRooms));
        strategies.add(new AirPurifiableRoomStrategy(allRooms));
    }
    @Override
    public Room createRoom(RoomType type, int floorIndex, int peopleCapacity) {
        Room room;
        if (Objects.requireNonNull(type) == RoomType.GARAGE) {
            room = new StorageRoom<>(type, (peopleCapacity + 4 - 1) / 4, SportsEquipment::new);
        } else {
            room = new GeneralRoom(type);
        }
        room.setFloorIndex(floorIndex);
        room.setTemperature(30);
        applyStrategies(room, type);
        equipWithSpecifics(room, type);
        return room;
    }

    private void applyStrategies(Room room, RoomType type) {
        for (RoomConfigurationStrategy strategy : strategies) {
            if (strategy.appliesTo(type)) {
                strategy.configure(room);
            }
        }
    }

    private void equipWithSpecifics(Room room, RoomType type) {
        switch (type) {
            case KITCHEN -> {
                room.addDevice(new Refrigirator());
                room.addDevice(new Feeder());
                room.addDevice(new WashingMachine());
                room.addDevice(new CoffeeMaker());
            }
            case GARAGE -> {
                room.addDevice(new GarageDoor());
            }
            case HALL -> {
                room.addDevice(new RoboticVacuumCleaner());
            }
        }
    }
}
