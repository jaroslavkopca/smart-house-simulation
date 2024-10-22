package cz.cvut.fel.omo.model.actor;

import cz.cvut.fel.omo.config.Controller.FreeTimeAction;
import cz.cvut.fel.omo.config.Controller.FreeTimeActionList;
import cz.cvut.fel.omo.model.device.devices.Containable.CoffeeMaker;
import cz.cvut.fel.omo.model.device.devices.Containable.Feeder;
import cz.cvut.fel.omo.model.device.devices.Containable.Refrigirator;
import cz.cvut.fel.omo.model.device.devices.Light;
import cz.cvut.fel.omo.model.device.devices.Window;
import cz.cvut.fel.omo.model.house.House;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.house.StorageRoom;
import cz.cvut.fel.omo.model.utils.Cabinet;
import cz.cvut.fel.omo.model.utils.SportsEquipment;
import cz.cvut.fel.omo.model.utils.UsefulMethods;

import java.util.Optional;
import java.util.Random;

public class Pet extends Actor{
    public Pet(String name) {
        super(name);
    }

    @Override
    protected void cleanUpAfterPersonalAction(FreeTimeActionList actionType, Room actionRoom, House house) {
        if (actionType == FreeTimeActionList.eatFromFeeder){
            super.energy = 0;
        }
    }

    @Override
    protected FreeTimeAction createPersonalAction(House house){
        FreeTimeActionList actionToDo;
        if (super.energy < 10) {
            actionToDo = FreeTimeActionList.sleepPet;
        } else {
            actionToDo = FreeTimeActionList.eatFromFeeder;
        }
        Room roomToDoTheActionIn = switch (actionToDo) {
            case eatFromFeeder -> house.getKitchen();
            case sleepPet -> getMyBedroom();
            default -> getCurrentRoom();
        };
        return new FreeTimeAction(this, actionToDo, roomToDoTheActionIn);
    }

    @Override
    protected boolean actOnFreeTimeAction(FreeTimeActionList actionType, Room actionRoom){
        return switch (actionType){
            case sleepPet -> true;
            case eatFromFeeder -> {
                Optional<Feeder> optionalFeeder = UsefulMethods.findDeviceInRoom(Feeder.class, actionRoom);
                if (optionalFeeder.isPresent()){
                    Feeder feeder = optionalFeeder.get();
                    yield feeder.removeContent();
                };
                yield false;
            }
            default -> false;
        };
    }

    public void live(){

    }
}
