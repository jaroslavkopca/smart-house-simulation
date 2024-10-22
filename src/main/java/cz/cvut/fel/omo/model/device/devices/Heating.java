package cz.cvut.fel.omo.model.device.devices;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.device.deviceState.OffState;

public class Heating extends Device {
    private int temperature;

    public Heating() {
        setState(new OffState());
        temperature = 20;
    }

    public void increaseTemperature() {
        this.turnOn();
            temperature += 5;
            double powerConsumed = getPowerForState() * (temperature / 30.0);
            addAction(new DeviceAction("IncreaseTemperature",this, powerConsumed, Timer.getCurrentMinutes()));
    }

    public void decreaseTemperature() {
        this.turnOn();
            temperature -= 5;
            double powerConsumed = getPowerForState() * (temperature / 30.0);
            addAction(new DeviceAction("DecreaseTemperature",this, powerConsumed, Timer.getCurrentMinutes()));
    }

    public void changeTemperature(){
        this.turnOn();

    }

    public int getTemperature() {
        return temperature;
    }
}
