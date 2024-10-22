package cz.cvut.fel.omo.model.device.devices;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.device.deviceState.OffState;


// Probably needs change and relation between room
public class AirPurifier extends Device {
    private int airIntensity;

    public AirPurifier() {
        setState(new OffState());
        airIntensity = 50;
    }

    public void increaseAirIntensity() {
        turnOn();
        if (airIntensity < 100) {
            airIntensity += 10;
            double powerConsumed = getPowerForState() * (airIntensity / 100.0);
            addAction(new DeviceAction("IncreaseAirIntensity", this ,powerConsumed, Timer.getCurrentMinutes()));
        }
    }

    public void decreaseAirIntensity() {
        this.turnOn();
        if (airIntensity > 0) {
            airIntensity -= 10;
            double powerConsumed = getPowerForState() * (airIntensity / 100.0);
            addAction(new DeviceAction("DecreaseAirIntensity", this, powerConsumed, Timer.getCurrentMinutes()));
        }
    }
}
