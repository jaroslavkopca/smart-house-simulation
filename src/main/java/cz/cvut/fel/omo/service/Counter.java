package cz.cvut.fel.omo.service;

import cz.cvut.fel.omo.config.Outside;
import cz.cvut.fel.omo.model.device.devices.Heating;
import cz.cvut.fel.omo.model.device.devices.Light;
import cz.cvut.fel.omo.model.house.Room;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private Room room;

    public Counter(Room room) {
        this.room = room;
    }

    public int countLightLevel(){
        int outsideLightLevel = 0;
//        int lightLevel = outsideLightLevel;
        AtomicInteger lightLevel = new AtomicInteger();
        AtomicInteger i = new AtomicInteger();
        room.getDevices().forEach(device -> {
            if (device instanceof Light) {
                lightLevel.addAndGet(((Light) device).getBrightnessLevel());
                i.getAndIncrement();
            }
        });
        return (int) (lightLevel.get()/i.get());
        // further logic for light level add here
    }

    public int countTemperature() {
        int outsideLightLevel = 0;
//        int lightLevel = outsideLightLevel;
        AtomicInteger lightLevel = new AtomicInteger();
        AtomicInteger i = new AtomicInteger();
        room.getDevices().forEach(device -> {
            if (device instanceof Light) {
                lightLevel.addAndGet(((Light) device).getBrightnessLevel());
                i.getAndIncrement();
            }
        });
        return (int) (lightLevel.get()/i.get());
    }

    public int calculateTemperature() {
        int outsideTemperature = 0;
        int insideHeating = 0;
        AtomicInteger temperatureTotal = new AtomicInteger();
        AtomicInteger deviceCount = new AtomicInteger();

        room.getDevices().forEach(device -> {
            if (device instanceof Heating) {
                temperatureTotal.addAndGet(((Heating) device).getTemperature());
                deviceCount.getAndIncrement();
            }
        });
        temperatureTotal.addAndGet(Outside.getOutsideTemperatureInCelsius());

        int averageDeviceTemperature = temperatureTotal.get()/deviceCount.get();
        int roomTemperature = (outsideTemperature + averageDeviceTemperature)/2;

        return roomTemperature;
    }
}
