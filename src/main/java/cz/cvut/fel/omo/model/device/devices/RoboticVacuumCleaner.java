package cz.cvut.fel.omo.model.device.devices;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.device.deviceState.OffState;

public class RoboticVacuumCleaner extends Device {
    private boolean isCleaning;
    private int battery;

    public RoboticVacuumCleaner() {
        startCleaning();
        isCleaning = true;
        battery = 100;
    }

    public void startCleaning() {
        turnOn();
        if (!isCleaning) {
            isCleaning = true;
            addAction(new DeviceAction("StartCleaning", this,getPowerForState(), Timer.getCurrentMinutes()));
        }
    }

    public void stopCleaning() {
        turnOff();
        if (isCleaning) {
            isCleaning = false;
            this.addAction(new DeviceAction("StopCleaning",this, getPowerForState(), Timer.getCurrentMinutes()));
        }
    }

    public void charge(){
        setIdle();
        addAction(new DeviceAction("Charged", this,getPowerForState(), Timer.getCurrentMinutes()));
        battery = 100;
        startCleaning();
    }

    public boolean isCleaning() {
        return isCleaning;
    }

    public void setCleaning(boolean cleaning) {
        isCleaning = cleaning;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
