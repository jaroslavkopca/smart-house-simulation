package cz.cvut.fel.omo.model.device.devices;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.device.deviceState.OffState;

public class Light extends Device {
    private int brightnessLevel;


    public Light() {
        setState(new OffState());
        brightnessLevel = 100;
    }


    public void turnLightsOn() {
        this.turnOn();
        this.brightnessLevel = 100;
        double powerConsumed = this.getPowerForState();
        this.addAction(new DeviceAction("Turn Lights On",this,powerConsumed, Timer.getCurrentMinutes()));
    }

    public void turnLightsOff() {
        this.turnOff();
        this.brightnessLevel = 0;
        double powerConsumed = this.getPowerForState();
        this.addAction(new DeviceAction("Turn Lights Off",this,powerConsumed,Timer.getCurrentMinutes()));
    }

    public int getBrightnessLevel() {
        return brightnessLevel;
    }

}
