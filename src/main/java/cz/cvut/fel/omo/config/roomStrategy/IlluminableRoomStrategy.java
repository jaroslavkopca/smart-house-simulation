package cz.cvut.fel.omo.config.roomStrategy;

import cz.cvut.fel.omo.model.device.devices.Light;
import cz.cvut.fel.omo.model.device.senzor.LightSensor;
//import cz.cvut.fel.omo.model.device.senzor.MotionSensor;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;

import java.util.List;

public class IlluminableRoomStrategy implements RoomConfigurationStrategy{
    private List<RoomType> applicableRoomTypes;

    @Override
    public boolean appliesTo(RoomType type) {
        return applicableRoomTypes.contains(type);
    }

    public IlluminableRoomStrategy(List<RoomType> types) {
        this.applicableRoomTypes = types;
    }
    @Override
    public void configure(Room room) {
        room.addDevice(new Light());
        room.addDevice(new LightSensor(room.getRoomConditionService()));
//        room.addDevice(new MotionSensor(room.getRoomConditionService()));
    }
}
