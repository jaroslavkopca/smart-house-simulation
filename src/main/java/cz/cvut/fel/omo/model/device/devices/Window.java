package cz.cvut.fel.omo.model.device.devices;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.device.deviceState.OffState;

public class Window extends Device {
    private int blindsLevel;
    private boolean hasBlinds;
    private boolean isOpen;

    public Window() {
        setState(new OffState());
        blindsLevel = 0;
    }

    public void increaseBlindsLevel() {
        turnOn();
        if (blindsLevel < 100) {
            blindsLevel += 20;
            double powerConsumed = getPowerForState() * (blindsLevel / 100.0);
            addAction(new DeviceAction("IncreaseBlindsLevel",this,  powerConsumed, Timer.getCurrentMinutes()));
        }
        setIdle();
    }

    public void fullyIncreaseBlindsLevel() {
        turnOn();
        if (blindsLevel < 100) {
            blindsLevel = 100;
            double powerConsumed = getPowerForState() * (blindsLevel / 100.0);
            addAction(new DeviceAction("FullyIncreaseBlindsLevel",this,  powerConsumed, Timer.getCurrentMinutes()));
        }
        setIdle();
    }

    public void decreaseBlindsLevel() {
        this.turnOn();
        if (blindsLevel > 0) {
            blindsLevel -= 20;
            double powerConsumed = getPowerForState() * (blindsLevel / 100.0);
            addAction(new DeviceAction("DecreaseBlindsLevel", this, powerConsumed, Timer.getCurrentMinutes()));
        }
        setIdle();
    }

    public void fullyDecreaseBlindsLevel() {
        this.turnOn();
        if (blindsLevel > 0) {
            blindsLevel -= 20;
            double powerConsumed = getPowerForState() * (blindsLevel / 100.0);
            addAction(new DeviceAction("DecreaseBlindsLevel", this, powerConsumed, Timer.getCurrentMinutes()));
        }
        setIdle();
    }

    public int getBlindsLevel(){
        return this.blindsLevel;
    }

    public void open(){
        this.turnOn();
        isOpen = true;
        this.setIdle();
    }

    public void close(){
        this.turnOn();
        isOpen = false;
        this.setIdle();
    }
}
