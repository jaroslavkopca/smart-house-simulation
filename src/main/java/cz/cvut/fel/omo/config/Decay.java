package cz.cvut.fel.omo.config;

import cz.cvut.fel.omo.config.Controller.Action;
import cz.cvut.fel.omo.config.Controller.ActionList;
import cz.cvut.fel.omo.config.Controller.Controller;
import cz.cvut.fel.omo.config.Controller.FreeTimeAction;
import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.devices.RoboticVacuumCleaner;
import cz.cvut.fel.omo.service.RoomService;

import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Decay {
    private List<RoomService> roomServiceList;
    private List<Device> allDevices;
    private List<Actor> allActors;


    public Decay(List<RoomService> roomServiceList, List<Device> allDevices, List<Actor> allActors) {
        this.roomServiceList = roomServiceList;
        this.allDevices = allDevices;
        this.allActors = allActors;
    }


    public void decay(){
        roomServiceList.forEach(roomService ->
                roomService.getRoomConditionService().setTemperature(
                        roomService.getRoomConditionService().getRoom().getTemperature() - 1)); //lower temperature

        allDevices.forEach(device -> {
            if (device instanceof RoboticVacuumCleaner) {
                int random = new Random().nextInt(1,8);
                if (((RoboticVacuumCleaner) device).getBattery() >= random){
                    ((RoboticVacuumCleaner)device).setBattery(((RoboticVacuumCleaner) device).getBattery()-random);
                }
                Queue<Action> actions = Controller.getExecutedActions();
                boolean notInCtrl =  !(Controller.getExecutedActions().stream().anyMatch(action -> action.getActionToDo() == ActionList.Charge)
                                    || Controller.getActionQueue().stream().anyMatch(action -> action.getActionToDo() == ActionList.Charge)) ;
                int battery = ((RoboticVacuumCleaner) device).getBattery();
                if (battery <= 3 && notInCtrl) {
                    ((RoboticVacuumCleaner) device).stopCleaning();
                    Controller.addAction(new Action(device, ActionList.Charge));
                }
            }
        });
        allActors.forEach(actor -> {
            FreeTimeAction freeTimeAction = actor.getCurrentFreeTimeAction();
            if (freeTimeAction == null) {
                actor.decreaseEnergy();
                actor.decreaseSatiety();
            } else {
                switch (freeTimeAction.getActionToDo()) {
                    case doSports, makeCoffee -> {
                        actor.decreaseEnergy();
                        actor.decreaseSatiety();
                    }
                    case sleepPerson, sleepPet -> {
                        actor.decreaseSatiety();
                        actor.increaseEnergy();
                    }
                    case eatFromFridge, eatFromFeeder -> {
                        actor.increaseSatiety();
                        actor.decreaseEnergy();
                    }
                }
            }

        });
    }
}
