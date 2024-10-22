package cz.cvut.fel.omo.config.Controller;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.device.Command;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.devices.Containable.CoffeeMaker;
import cz.cvut.fel.omo.model.device.devices.Containable.Feeder;
import cz.cvut.fel.omo.model.device.devices.Containable.Refrigirator;
import cz.cvut.fel.omo.model.device.devices.Containable.WashingMachine;
import cz.cvut.fel.omo.model.device.devices.RoboticVacuumCleaner;

public class Action implements Command {
    private Device senderDevice;
    private Actor actor;
    private ActionList actionToDo;


    public Action(Device senderDevice, Actor actor, ActionList actionToDo) {
        this.senderDevice = senderDevice;
        this.actor = actor;
        this.actionToDo = actionToDo;
    }

    public Action(Device senderDevice, ActionList actionToDo) {
        this.senderDevice = senderDevice;
        this.actionToDo = actionToDo;
    }

    public void execute() {
        switch (actionToDo) {
            case Charge:
                actor.changeLocation(((RoboticVacuumCleaner) senderDevice).getRoom());
                ((RoboticVacuumCleaner) senderDevice).charge();
                break;
            case fullRefrigerator:
                ((Refrigirator) senderDevice).refill();
                break;
            case fullFeeder:
                ((Feeder) senderDevice).refill();
                break;
            case emptyVM:
                ((WashingMachine) senderDevice).empty();
                break;
//            case emptyVM:
//                ((WashingMachine) senderDevice).empty();
//                break;
            case FullCM:
                ((CoffeeMaker) senderDevice).refill();
                break;
            default:
                break;
        }
    }

    public Device getSenderDevice() {
        return senderDevice;
    }

    public Actor getActor() {
        return actor;
    }

    public ActionList getActionToDo() {
        return actionToDo;
    }

    public int getTimeForAction() {
        switch (actionToDo) {
            case Charge:
                return 10;
            case fullRefrigerator:
                return 30;
            case fullFeeder:
                return 30;
            case fullVM:
                return 30;
            case emptyVM:
                return 30;
            case FullCM:
                return 30;
            default:
                return 0;
        }
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}