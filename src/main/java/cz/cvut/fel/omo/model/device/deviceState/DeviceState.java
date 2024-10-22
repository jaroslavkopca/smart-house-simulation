package cz.cvut.fel.omo.model.device.deviceState;

import cz.cvut.fel.omo.model.device.Device;

public interface DeviceState {
    void turnOn(Device device);

    void turnOff(Device device);

    void setIdle(Device device);

}
