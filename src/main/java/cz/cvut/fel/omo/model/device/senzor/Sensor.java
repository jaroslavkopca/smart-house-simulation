package cz.cvut.fel.omo.model.device.senzor;

import cz.cvut.fel.omo.model.device.Consumption;
import cz.cvut.fel.omo.model.device.DataCollector;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.deviceState.IdleState;
import cz.cvut.fel.omo.model.device.deviceState.OffState;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.service.RoomConditionService;

import java.util.ArrayList;
import java.util.Observer;
import java.util.List;

public class Sensor extends Device {
        private int value;
        private int id;
        private RoomConditionService roomConditionService;
        public void measure(){};

    public Sensor(RoomConditionService roomConditionService) {
            super();
            this.roomConditionService = roomConditionService;
            roomConditionService.addObserver(this);
            setState(new IdleState());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}
