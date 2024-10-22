package cz.cvut.fel.omo.config.Controller;

import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.actor.Person;
import cz.cvut.fel.omo.model.device.DeviceAction;
import cz.cvut.fel.omo.model.house.StorageRoom;

import java.util.Queue;
import java.util.LinkedList;

public class Controller {
    private static Queue<Action> completedActions = new LinkedList<>();
    private static Queue<Action> executedActions = new LinkedList<>();
    private static Queue<Action> actionQueue = null;

    public Controller() {
        this.actionQueue = new LinkedList<>();
    }

    public static void addAction(Action Action) {
        actionQueue.add(Action);
    }

    public static void removeActionFromExecuted(Action performedAction) {
        executedActions.remove(performedAction);
    }

    public static void addActionToCompleted(Action performedAction) {
        completedActions.add(performedAction);
    }

    public void look(Person person){
        Action currentAction = getNextAction();
        if (currentAction != null) {
            executedActions.add(currentAction);
            currentAction.setActor(person);
//            System.out.println("look at action" + currentAction.getActionToDo());
//            System.out.println("Person is looking" + person.getName());
            person.setPerformedAction(currentAction);
            person.setBusy(true);
            person.setTimeForAction(Timer.getCurrentMinutes());
            person.performAction();
        }
    }

//    public void doSports(Person person, StorageRoom garage){
//        person.takeEquipment(garage.getStorageCabinet());
//        person.setPerformedAction();
//        person.setBusy(true);
//    }

    public Action getNextAction() {
        return actionQueue.poll();
    }

    public static Queue<Action> getActionQueue() {
        return actionQueue;
    }

    public static Queue<Action> getCompletedActions() {
        return completedActions;
    }

    public static void setCompletedActions(Queue<Action> completedActions) {
        Controller.completedActions = completedActions;
    }

    public static Queue<Action> getExecutedActions() {
        return executedActions;
    }

    public static void setExecutedActions(Queue<Action> executedActions) {
        Controller.executedActions = executedActions;
    }

    public static void setActionQueue(Queue<Action> actionQueue) {
        Controller.actionQueue = actionQueue;
    }
}