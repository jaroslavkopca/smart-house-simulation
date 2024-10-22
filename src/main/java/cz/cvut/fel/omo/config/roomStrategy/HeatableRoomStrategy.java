package cz.cvut.fel.omo.config.roomStrategy;

import cz.cvut.fel.omo.model.device.devices.Heating;
import cz.cvut.fel.omo.model.device.senzor.TemperatureSensor;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;

import java.util.List;

public class HeatableRoomStrategy implements RoomConfigurationStrategy{
    private List<RoomType> applicableRoomTypes;

    @Override
    public boolean appliesTo(RoomType type) {
        return applicableRoomTypes.contains(type);
    }

    public HeatableRoomStrategy(List<RoomType> types) {
        this.applicableRoomTypes = types;
    }
    @Override
    public void configure(Room room) {
        room.addDevice(new Heating());
        room.addDevice(new TemperatureSensor(room.getRoomConditionService()));
    }
}
