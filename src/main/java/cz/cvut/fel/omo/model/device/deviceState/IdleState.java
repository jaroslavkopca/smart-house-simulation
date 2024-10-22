package cz.cvut.fel.omo.model.device.deviceState;

import cz.cvut.fel.omo.model.device.Device;

public class IdleState implements DeviceState {
    @Override
    public void turnOn(Device device) {
        device.setState(new OnState());
    }

    @Override
    public void turnOff(Device device) {
        device.setState(new OffState());
    }

    @Override
    public void setIdle(Device device) {
    }
}

