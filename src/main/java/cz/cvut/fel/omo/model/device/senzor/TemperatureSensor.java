package cz.cvut.fel.omo.model.device.senzor;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.service.RoomConditionService;

public class TemperatureSensor extends Sensor {
    public TemperatureSensor(RoomConditionService roomConditionService) {
        super(roomConditionService);
        roomConditionService.addObserver(this);
    }

    @Override
    public void measure() {
        setValue(getRoom().getTemperature());
        this.addAction(new DeviceAction("Measure Temperature",this,getPowerForState(), Timer.getCurrentMinutes()));
    }
}
