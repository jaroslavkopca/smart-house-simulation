package cz.cvut.fel.omo.model.device.deviceState;

import cz.cvut.fel.omo.model.device.Device;

public class OnState implements DeviceState {
    @Override
    public void turnOn(Device device) {
    }

    @Override
    public void turnOff(Device device) {
        device.setState(new OffState());
    }

    @Override
    public void setIdle(Device device) {
        device.setState(new IdleState());
    }
}
