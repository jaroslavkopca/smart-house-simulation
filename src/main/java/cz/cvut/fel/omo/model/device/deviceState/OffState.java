package cz.cvut.fel.omo.model.device.deviceState;

import cz.cvut.fel.omo.model.device.Device;

public class OffState implements DeviceState {
    @Override
    public void turnOn(Device device) {
        device.setState(new OnState());
    }

    @Override
    public void turnOff(Device device) {
    }

    @Override
    public void setIdle(Device device) {
        device.setState(new IdleState());
    }
}
