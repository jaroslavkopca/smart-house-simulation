package cz.cvut.fel.omo.model.device;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.deviceState.DeviceState;
import cz.cvut.fel.omo.model.device.deviceState.IdleState;
import cz.cvut.fel.omo.model.device.deviceState.OffState;
import cz.cvut.fel.omo.model.device.deviceState.OnState;
import cz.cvut.fel.omo.model.device.devices.Heating;

import java.util.ArrayList;

public class Consumption {
    private Device device;
    private double maxPower;
    private double onPower; // Power consumed when the device is on
    private double idlePower; // Power consumed when the device is idle
    private double totalEnergyConsumed;
    private DeviceState wasDeviceOnLastTick; // Flag to track device state in the last tick
    private int ticksInCurrentState; // Duration for which device has been in current state

    public Consumption(Device device) {
        this.device = device;
        this.maxPower = 100; //gonna change
        this.onPower = maxPower * 0.8; // 80% of the maxPower
        this.idlePower = maxPower * 0.2; //20% of the maxPower
        this.totalEnergyConsumed = 0.0;
        this.wasDeviceOnLastTick = new OffState();
        this.ticksInCurrentState = 0;
    }


    public void updateConsumption() {
        if (device.getPerformedActions().isEmpty()){
            double powerForState;
            if (device instanceof Heating) {
                powerForState = getPowerForState() * (((Heating) device).getTemperature() / 30.0);
            }else{
                powerForState = getPowerForState();
            }

            device.getPerformedActions().add(new DeviceAction("Device state:" + device.getState().toString(),device,powerForState, Timer.getCurrentMinutes()));
        }
        for (DeviceAction action : device.getPerformedActions()) {
            totalEnergyConsumed += action.getPowerConsumed();
        }
        DataCollector.collectConsumption(device.getPerformedActions());
        device.setPerformedActions(new ArrayList<>());
    }

    /**
     * The function update devices consumption from the simulation tick.
     * The logic is probably going to change later on
     *
     * @param
     */
    public void updateConsumption(DeviceState state) {
        // If device state changed since last tick, update consumption accordingly
        if (state != wasDeviceOnLastTick) {
            if (wasDeviceOnLastTick instanceof OnState) {
                // Device was on and now turned off
                totalEnergyConsumed += onPower * ticksInCurrentState;
            } else {
                // Device was off and now turned on
                totalEnergyConsumed += idlePower * ticksInCurrentState;
            }
            wasDeviceOnLastTick = new OnState();
            ticksInCurrentState = 0; // Reset ticks in current state
        }

        ticksInCurrentState++; // Increment ticks in current state

        // Add consumption for the current state (on or off)
        if (state instanceof OnState) {
            totalEnergyConsumed += onPower;
        } else {
            totalEnergyConsumed += idlePower;
        }
    }
    public double getTotalEnergyConsumed() {
        return totalEnergyConsumed;
    }

    public double getPowerForState() {
        if (device.getState() instanceof OnState) {
            return onPower;
        } else if (device.getState() instanceof IdleState){
            return idlePower;
        }else return 0;
    }
}
