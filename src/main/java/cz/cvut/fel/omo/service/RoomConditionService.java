package cz.cvut.fel.omo.service;

import cz.cvut.fel.omo.model.device.devices.Window;
import cz.cvut.fel.omo.model.device.senzor.Sensor;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.utils.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomConditionService {
    private Room room;
    private List<Sensor> sensors;

    public RoomConditionService(Room room) {
        this.room = room;
        this.sensors = new ArrayList<>();
        room.getDevices().forEach(device ->
        {
            if (device instanceof Sensor) {
                sensors.add((Sensor) device);
            }
        });
    }

    public void addObserver(Sensor sensor) {
        sensors.add(sensor);
    }

    public void removeObserver(Sensor sensor) {
        sensors.remove(sensor);
    }

    // Method to notify sensors about changes in room conditions
    public void notifySensors() {
        for (Sensor sensor : sensors) {
            sensor.measure();
        }
    }

    // Methods to update room conditions and trigger notification to sensors
    public void setTemperature(int temperature) {
        room.setTemperature(temperature);
        notifySensors();
    }

    public void setLightLevel(int lightLevel) {
        room.setLightLevel(lightLevel);
        notifySensors();
    }

    public void setCo2Level(int co2Level) {
        room.setCo2Level(co2Level);
        notifySensors();
    }

    public void setAirQuality(int airQuality) {
        room.setAirQuality(airQuality);
        notifySensors();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }


}
