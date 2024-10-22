package cz.cvut.fel.omo.service;

import cz.cvut.fel.omo.config.Outside;
import cz.cvut.fel.omo.model.device.devices.Window;
import cz.cvut.fel.omo.model.device.senzor.LightSensor;
import cz.cvut.fel.omo.model.device.senzor.Sensor;
import cz.cvut.fel.omo.model.device.senzor.TemperatureSensor;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.utils.RoomType;

import java.util.List;
import java.util.Optional;

public class RoomService {
    private  RoomConditionService roomConditionService;
    private DeviceControlFacade deviceControlFacade;
    private Counter counter;
    private Outside outside;
    private Room room;

    public RoomService(Room room, Outside outside) {
        this.room = room;
        this.deviceControlFacade = new DeviceControlFacade(room);
        this.roomConditionService = new RoomConditionService(room);
        this.counter = new Counter(room);
        this.outside = outside;
    }

    public void changeLightLevel(boolean shouldTurnOn){
        deviceControlFacade.adjustLight(shouldTurnOn);
        roomConditionService.setLightLevel(shouldTurnOn ? 100 : outside.getOutsideLightLevel());
    }


    public void changeTemperature(boolean up){
        deviceControlFacade.adjustTemperature(up);
        roomConditionService.setTemperature(counter.calculateTemperature());
    }

    public RoomConditionService getRoomConditionService() {
        return roomConditionService;
    }

    public void measure() {
        List<Sensor> sensors = roomConditionService.getSensors();
        sensors.forEach(sensor -> sensor.measure());
        for (Sensor sensor : sensors) {
            int val = sensor.getValue();
            if (sensor instanceof LightSensor) {
                if (outside.getOutsideLightLevel() < 50 || blindsAreClosed()) {
                    changeLightLevel(true);
                } else {
                    changeLightLevel(false);
                }
            } else if (sensor instanceof TemperatureSensor) {
                if (val < 20) {
                    changeTemperature(true);
                } else if (val > 30) {
                    changeTemperature(false);
                }
            }

        }
    }
    public boolean blindsAreClosed(){
        Optional<Window> windowOptional = room.getDevices().stream()
                .filter(device -> device instanceof Window)
                .map(device -> (Window) device)
                .findFirst();

        if (windowOptional.isPresent()) {
            return windowOptional.get().getBlindsLevel() == 100;
        } else {
            return true;
        }
    }
}
