package cz.cvut.fel.omo.model.device;

import cz.cvut.fel.omo.model.device.deviceState.DeviceState;
import cz.cvut.fel.omo.model.device.deviceState.OffState;
import cz.cvut.fel.omo.model.device.deviceState.OnState;
import cz.cvut.fel.omo.model.house.Room;

import java.util.ArrayList;
import java.util.List;

public abstract class Device {
    private DeviceState state;
    private Consumption consumption;
    private DataCollector dataCollector;
    private List<DeviceAction> performedActions;
    private Room room;
    private int timeStamp;

    public Device() {
        this.state = new OnState();
        this.consumption = new Consumption(this); // gonna change
        this.dataCollector = new DataCollector();
        this.performedActions = new ArrayList<>();
    }

    public void updateConsumption() {
        consumption.updateConsumption();
    }

    public double getPowerForState() {
        return consumption.getPowerForState();
    }

    public void addAction(DeviceAction action) {
        performedActions.add(action);
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public Room getRoom() {return room;}
    public void setRoom(Room room) {this.room = room;}

    public void turnOn() {
        state.turnOn(this);
    }

    public void turnOff() {
        state.turnOff(this);
    }

    public void setIdle() {
        state.setIdle(this);
    }

    public DeviceState getState() {
        return state;
    }

    public Consumption getConsumption() {
        return consumption;
    }

    public DataCollector getDataCollector() {
        return dataCollector;
    }

    public List<DeviceAction> getPerformedActions() {
        return performedActions;
    }

    public void setPerformedActions(List<DeviceAction> performedActions) {
        this.performedActions = performedActions;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}