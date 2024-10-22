package cz.cvut.fel.omo.service;

import cz.cvut.fel.omo.model.device.devices.*;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.device.Device;

public class DeviceControlFacade {
    private Room room;

    public DeviceControlFacade(Room room) {
        this.room = room;
    }

    public void turnOnAllDevices() {
        room.getDevices().forEach(Device::turnOn);
    }

    public void turnOffAllDevices() {
        room.getDevices().forEach(Device::turnOff);
    }

    public void adjustTemperature(boolean up) {
        if (up) {
            room.getDevices().forEach(device
                    -> {
                if (device instanceof Heating) {
                    ((Heating) device).increaseTemperature();
                }
            });
        } else {
            room.getDevices().forEach(device
                    -> {
                if (device instanceof Heating) {
                    ((Heating) device).decreaseTemperature();
                }
            });
        }
    }


    public void adjustLight(boolean shouldTurnOn) {
        if (shouldTurnOn) {
            room.getDevices().forEach(device
                    -> {
                if (device instanceof Light) {
                    ((Light) device).turnLightsOn();
                }
            });
        } else {
            room.getDevices().forEach(device
                    -> {
                if (device instanceof Light) {
                    ((Light) device).turnLightsOff();
                }
            });
        }
    }

    public void openGarageDoor() {
        room.getDevices().forEach(device -> {
            if (device instanceof GarageDoor) {
                ((GarageDoor) device).openDoor();
            }
        });
    }

    public void closeGarageDoor() {
        room.getDevices().forEach(device -> {
            if (device instanceof GarageDoor) {
                ((GarageDoor) device).closeDoor();
            }
        });
    }

    public void adjustAirPurrifier(int newAirQualityLevel) {
        if (newAirQualityLevel < room.getAirQuality()) {
            room.getDevices().forEach(device
                    -> {
                if (device instanceof AirPurifier) {
                    ((AirPurifier) device).increaseAirIntensity();
                }
            });
        } else {
            room.getDevices().forEach(device
                    -> {
                if (device instanceof AirPurifier) {
                    ((AirPurifier) device).decreaseAirIntensity();
                }
            });
        }
    }

    public void closeWindow() {
        room.getDevices().forEach(device -> {
            if (device instanceof Window) {
                ((Window) device).close();
            }
        });
    }

    public void openWindow() {
        room.getDevices().forEach(device -> {
            if (device instanceof Window) {
                ((Window) device).open();
            }
        });
    }

    public void increaseBlinds(Device window) {
        if (window instanceof Window) {
            ((Window) window).increaseBlindsLevel();
        }
    }

    public void decreaseBlinds(Device window) {
        if (window instanceof Window) {
            ((Window) window).decreaseBlindsLevel();
        }
    }

    public void startVacuum(boolean state) {
        if (state) {
            room.getDevices().forEach(device -> {
                if (device instanceof RoboticVacuumCleaner) {
                    ((RoboticVacuumCleaner) device).startCleaning();
                }
            });
        } else {
            room.getDevices().forEach(device -> {
                if (device instanceof RoboticVacuumCleaner) {
                    ((RoboticVacuumCleaner) device).stopCleaning();
                }
            });
        }
    }




}
