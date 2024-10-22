package cz.cvut.fel.omo.config.roomStrategy;

import cz.cvut.fel.omo.model.device.devices.AirPurifier;
import cz.cvut.fel.omo.model.device.senzor.AirQualitySensor;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;

import java.util.List;

public class AirPurifiableRoomStrategy implements RoomConfigurationStrategy{
    private List<RoomType> applicableRoomTypes;

    @Override
    public boolean appliesTo(RoomType type) {
        return applicableRoomTypes.contains(type);
    }

    public AirPurifiableRoomStrategy(List<RoomType> types) {
        this.applicableRoomTypes = types;
    }
    @Override
    public void configure(Room room) {
        room.addDevice(new AirPurifier());
        room.addDevice(new AirQualitySensor(room.getRoomConditionService()));
    }
}
