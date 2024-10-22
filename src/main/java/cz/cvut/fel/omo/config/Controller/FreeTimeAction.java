package cz.cvut.fel.omo.config.Controller;

import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.house.Room;

public class FreeTimeAction {
    private Actor actor;
    private FreeTimeActionList actionToDo;
    private Room roomToDoTheActionIn;

    public FreeTimeAction(Actor actor, FreeTimeActionList actionToDo, Room roomToDoTheActionIn){
        this.actor = actor;
        this.actionToDo = actionToDo;
        this.roomToDoTheActionIn = roomToDoTheActionIn;
    }

    public Actor getActor() {
        return actor;
    }

    public FreeTimeActionList getActionToDo() {
        return actionToDo;
    }

    public Room getRoomToDoTheActionIn() {
        return roomToDoTheActionIn;
    }

    public int getTimeForAction() {
        return switch (actionToDo) {
            case eatFromFridge -> 30;
            case sleepPerson -> 480;
            case makeCoffee -> 20;
            case doSports -> 100;
            case sleepPet -> 200;
            case eatFromFeeder -> 30;
        };
    }
}
