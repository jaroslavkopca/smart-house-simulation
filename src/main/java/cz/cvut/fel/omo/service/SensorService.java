package cz.cvut.fel.omo.service;

import cz.cvut.fel.omo.model.device.devices.Light;
import cz.cvut.fel.omo.model.device.senzor.LightSensor;
import cz.cvut.fel.omo.model.device.senzor.Sensor;
import cz.cvut.fel.omo.model.device.senzor.TemperatureSensor;
import cz.cvut.fel.omo.model.house.Room;

import java.util.List;

public class SensorService {
    private RoomService roomService;

    public SensorService(RoomService roomService) {
        this.roomService = roomService;
    }

    public void measure() {
        List<Sensor> sensors = roomService.getRoomConditionService().getSensors();
        sensors.forEach(sensor -> sensor.measure());
        for (Sensor sensor : sensors) {
            int val = sensor.getValue();
            if (sensor instanceof LightSensor) {
                if (val < 50) {
                    roomService.changeLightLevel(true);
                } else {
                    roomService.changeLightLevel(false);
                }
            } else if (sensor instanceof TemperatureSensor) {
                if (val > 30) {
                    //....
                }
            }

        }
    }


}
