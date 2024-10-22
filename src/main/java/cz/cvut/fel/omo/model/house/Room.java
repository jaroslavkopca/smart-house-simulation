package cz.cvut.fel.omo.model.house;

import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.reports.HouseReport;
import cz.cvut.fel.omo.model.utils.RoomType;
import cz.cvut.fel.omo.service.RoomConditionService;

import java.util.ArrayList;
import java.util.List;

public abstract class Room extends HouseComponent{

    private RoomType type;
    private List<Device> devices;
    private List<Actor> actors;
    private int temperature;
    private int lightLevel;
    private int co2Level;
    private int airQuality;
    private boolean hasWindow;

    private int floorIndex;
    private RoomConditionService roomConditionService;

    public Room(RoomType type) {
        this.devices = new ArrayList<>();
        this.type = type;
        this.roomConditionService = new RoomConditionService(this);
        this.hasWindow = false;
        this.actors = new ArrayList<>();
    }

    public void setFloorIndex(int floorIndex) {
        this.floorIndex = floorIndex;
    }

    public void addDevice(Device device){
        devices.add(device);
        device.setRoom(this);
    }



    public List<Device> getDevices() {
        return devices;
    }

    public RoomConditionService getRoomConditionService(){
        return this.roomConditionService;
    }

    @Override
    public void switchOffElectricity() {
    }

    @Override
    public int getConsumptionReport() {
        return  0; // Placeholder value
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    public int getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }

    public int getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(int airQuality) {
        this.airQuality = airQuality;
    }

    // TODO: in essence, however sensor (motion) should access it and notify observer (light);
    public boolean getMotion() {return !actors.isEmpty();}

    public void setHasWindow() {
        this.hasWindow = true;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void addActor(Actor actor){
        actors.add(actor);
    }

    public void removeActor(Actor actor){
        actors.remove(actor);
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String getConfigurationReport() {
        StringBuilder report = new StringBuilder("\t\t\tRoom"+ HouseReport.extractFloorType(this.toString())+" Configuration:\n");
        for (Device device : devices) {
            report.append("\t\t\t\tDevice: ").append(HouseReport.extractDeviceType(device.toString())).append("\n");
        }
        return report.toString();
    }
}
