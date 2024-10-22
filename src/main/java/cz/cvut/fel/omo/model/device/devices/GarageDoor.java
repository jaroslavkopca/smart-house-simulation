package cz.cvut.fel.omo.model.device.devices;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.device.deviceState.OffState;

public class GarageDoor extends Device {
    private boolean isOpen;

    public GarageDoor() {
        setState(new OffState());
        isOpen = false;
    }

    public void openDoor() {
        if (!isOpen) {
            turnOn();
            isOpen = true;
            addAction(new DeviceAction("OpenDoor", this, getPowerForState(), Timer.getCurrentMinutes()));
            setIdle();
        }
    }

    public void closeDoor() {
        if (isOpen) {
            turnOn();
            isOpen = false;
            addAction(new DeviceAction("CloseDoor", this, getPowerForState(), Timer.getCurrentMinutes()));
            setIdle();
        }
    }
}
