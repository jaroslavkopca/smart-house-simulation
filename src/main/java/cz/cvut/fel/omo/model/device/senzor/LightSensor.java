package cz.cvut.fel.omo.model.device.senzor;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.service.RoomConditionService;

public class LightSensor extends Sensor {
    public LightSensor(RoomConditionService roomConditionService) {
        super(roomConditionService);
        roomConditionService.addObserver(this);
    }

    @Override
    public void measure() {
        setValue(getRoom().getLightLevel());
        addAction(new DeviceAction("Measure Light",this, getPowerForState(), Timer.getCurrentMinutes()));
    }
}
