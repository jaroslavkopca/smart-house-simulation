package cz.cvut.fel.omo.model.actor;

import cz.cvut.fel.omo.config.Controller.ActoredActionLog;
import cz.cvut.fel.omo.config.Controller.FreeTimeAction;
import cz.cvut.fel.omo.config.Controller.FreeTimeActionList;
import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.device.devices.Containable.CoffeeMaker;
import cz.cvut.fel.omo.model.device.devices.Containable.Refrigirator;
import cz.cvut.fel.omo.model.device.devices.Containable.WashingMachine;
import cz.cvut.fel.omo.model.device.devices.Light;
import cz.cvut.fel.omo.model.device.devices.Window;
import cz.cvut.fel.omo.model.house.House;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.house.StorageRoom;
import cz.cvut.fel.omo.model.utils.ActoredActionLogType;
import cz.cvut.fel.omo.model.utils.Cabinet;
import cz.cvut.fel.omo.model.utils.SportsEquipment;
import cz.cvut.fel.omo.model.utils.UsefulMethods;

import java.util.Optional;
import java.util.Random;

public class Person extends Actor{

    private SportsEquipment equipment;
    public Person(String name) {
        super(name);
        setBusy(false);
    }

    public boolean takeEquipment(Cabinet<SportsEquipment> cabinet) {
        if (cabinet.isOneItemAvailable()) {
            this.equipment = cabinet.takeItem();
            return true;
        } return false;
    }


    public void returnEquipment(Cabinet<SportsEquipment> cabinet) {
        if (equipment != null) {
            cabinet.returnItem(equipment);
            equipment = null;
        }
    }

    @Override
    protected FreeTimeAction createPersonalAction(House house){
        FreeTimeActionList actionToDo;
        if (super.energy < 10) {
            actionToDo = FreeTimeActionList.sleepPerson;
        } else if (super.satiety < 10) {
            actionToDo = FreeTimeActionList.eatFromFridge;
        } else {
            actionToDo = new Random().nextBoolean() ? FreeTimeActionList.doSports : FreeTimeActionList.makeCoffee;
        }
        Room roomToDoTheActionIn = switch (actionToDo) {
            case doSports -> house.getGarage();
            case eatFromFridge, makeCoffee -> house.getKitchen();
            case sleepPerson -> getMyBedroom();
            default -> getCurrentRoom();
        };
        return new FreeTimeAction(this, actionToDo, roomToDoTheActionIn);
    }

    @Override
    protected boolean actOnFreeTimeAction(FreeTimeActionList actionType, Room actionRoom){
        return switch (actionType){
            case doSports -> {
                Cabinet<SportsEquipment> sportsEquipmentCabinet = ((StorageRoom<SportsEquipment>) actionRoom).getStorageCabinet();
                yield takeEquipment(sportsEquipmentCabinet);
            }
            case sleepPerson -> {
                Optional<Light> bedroomLight = UsefulMethods.findDeviceInRoom(Light.class, actionRoom);
                Optional<Window> bedroomWindow = UsefulMethods.findDeviceInRoom(Window.class, actionRoom);
                bedroomLight.ifPresent(Light::turnLightsOff);
                bedroomWindow.ifPresent(Window::fullyIncreaseBlindsLevel);
                yield true;
            }
            case eatFromFridge -> {
                Optional<Refrigirator> optionalRefrigirator = UsefulMethods.findDeviceInRoom(Refrigirator.class, actionRoom);
                if (optionalRefrigirator.isPresent()){
                    Refrigirator refrigirator = optionalRefrigirator.get();
                    this.increaseEnergy(2);
                    yield refrigirator.removeContent();
                };
                yield false;
            }
            case makeCoffee -> {
                Optional<CoffeeMaker> optionalCoffeeMaker = UsefulMethods.findDeviceInRoom(CoffeeMaker.class, actionRoom);
                if (optionalCoffeeMaker.isPresent()){
                    CoffeeMaker coffeeMaker = optionalCoffeeMaker.get();
                    this.increaseEnergy(3);
                    yield coffeeMaker.removeContent();
                };
                yield false;
            }
            default -> false;
        };
    }

    @Override
    protected void cleanUpAfterPersonalAction(FreeTimeActionList actionType, Room actionRoom, House house){
        switch (actionType){
            case sleepPerson -> {
                Optional<Light> bedroomLight = UsefulMethods.findDeviceInRoom(Light.class, actionRoom);
                Optional<Window> bedroomWindow = UsefulMethods.findDeviceInRoom(Window.class, actionRoom);
                bedroomLight.ifPresent(Light::turnLightsOn);
                bedroomWindow.ifPresent(Window::fullyDecreaseBlindsLevel);
            }
            case doSports -> {
                Cabinet<SportsEquipment> sportsEquipmentCabinet = ((StorageRoom<SportsEquipment>) actionRoom).getStorageCabinet();
                returnEquipment(sportsEquipmentCabinet);
                Optional<WashingMachine> optionalWashingMachine = UsefulMethods.findDeviceInRoom(WashingMachine.class, house.getKitchen());
                if (optionalWashingMachine.isPresent()){
                    WashingMachine washingMachine = optionalWashingMachine.get();
                    boolean succeededAddingToVM = washingMachine.refill();
                    getPerformedActionsLog().add(new ActoredActionLog(Timer.getCurrentMinutes(), ActoredActionLogType.PERFORMING.name(), "addToWashingMachineAfterSport"));
                    if (!succeededAddingToVM){
                        getPerformedActionsLog().add(new ActoredActionLog(Timer.getCurrentMinutes(), ActoredActionLogType.ABORTING_SPACE_NOT_AVAILABLE.name(), "addToWashingMachineAfterSport"));
                    }
                }
            }
        }
    }

}
