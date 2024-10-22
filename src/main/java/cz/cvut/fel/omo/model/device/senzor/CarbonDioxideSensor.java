package cz.cvut.fel.omo.model.device.senzor;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.service.RoomConditionService;

public class CarbonDioxideSensor extends Sensor{
    public CarbonDioxideSensor(RoomConditionService roomConditionService) {
        super(roomConditionService);
        roomConditionService.addObserver(this);
    }

    @Override
    public void measure() {
        setValue(getRoom().getCo2Level());
        this.addAction(new DeviceAction("Measure CO2",this,getPowerForState(), Timer.getCurrentMinutes()));
    }
}
