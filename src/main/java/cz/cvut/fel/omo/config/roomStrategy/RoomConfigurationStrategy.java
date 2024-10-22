package cz.cvut.fel.omo.config.roomStrategy;

import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.model.house.Room;

public interface RoomConfigurationStrategy {
    void configure(Room room);
    boolean appliesTo(RoomType type);
}
