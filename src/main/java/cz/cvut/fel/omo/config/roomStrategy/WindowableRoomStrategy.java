package cz.cvut.fel.omo.config.roomStrategy;

import cz.cvut.fel.omo.model.device.devices.Window;
import cz.cvut.fel.omo.model.device.senzor.CarbonDioxideSensor;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;

import java.util.List;

public class WindowableRoomStrategy implements RoomConfigurationStrategy{
    private List<RoomType> applicableRoomTypes;

    @Override
    public boolean appliesTo(RoomType type) {
        return applicableRoomTypes.contains(type);
    }

    public WindowableRoomStrategy(List<RoomType> types) {
        this.applicableRoomTypes = types;
    }
    @Override
    public void configure(Room room) {
        room.addDevice(new Window());
        room.addDevice(new CarbonDioxideSensor(room.getRoomConditionService()));
        room.setHasWindow();
    }
}
