package cz.cvut.fel.omo.model.actor;

import cz.cvut.fel.omo.config.Controller.*;
import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.model.house.House;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.utils.ActoredActionLogType;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor {

    protected String name;

    public String getName() {
        return name;
    }

    private List<ActoredActionLog> performedActionsLog = new ArrayList<>();

    public List<ActoredActionLog> getPerformedActionsLog() {
        return performedActionsLog;
    }


    private FreeTimeAction currentFreeTimeAction;

    private int currentFreeTimeActionStartingTime;

    protected int satiety;
    protected int energy;


    private int timeForAction;
    private boolean busy;

    private Room myBedroom;

    public void setMyBedroom(Room room) {
        this.myBedroom = room;
    }

    protected Room getMyBedroom() {
        return myBedroom;
    }

    private Action performedAction;





    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return currentRoom;
    }

    public void setRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    protected Room currentRoom;

    public Actor(String name) {
        this.name = name;
        this.satiety = 100;
        this.energy = 100;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setTimeForAction(int timeForAction) {
        this.timeForAction = timeForAction;
    }

    public Action getPerformedAction() {
        return performedAction;
    }

    public void setPerformedAction(Action performedAction) {
        this.performedAction = performedAction;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    public void performAction() {
        if (performedAction == null) {
            return;
        }
        if (timeForAction + performedAction.getTimeForAction() == Timer.getCurrentMinutes()) {
            performedAction.execute();
            performedActionsLog.add(new ActoredActionLog(Timer.getCurrentMinutes(), ActoredActionLogType.PERFORMING.name(), performedAction.getActionToDo().name()));
            Controller.removeActionFromExecuted(performedAction);
            Controller.addActionToCompleted(performedAction);
            performedAction = null;
            setBusy(false);
        }
    }


    public int getTimeForAction() {
        return timeForAction;
    }

    public void changeLocation(Room newLocation) {

        currentRoom.removeActor(this);
        newLocation.addActor(this);
        this.setCurrentRoom(newLocation);
    }

    public void goToBedroom() {
        changeLocation(myBedroom);
    }



    public void setPerformedActionsLog(List<ActoredActionLog> performedActionsLog) {
        this.performedActionsLog = performedActionsLog;
    }

    // optimal decrease rate for roughly 3 food cravings a day considering 1 tick is 10 minutes
    public void decreaseEnergy() {
        energy = energy >= 1 ? energy - 1 : 0;
    }

    // optimal decrease rate for roughly 16 waking hours a day considering 1 tick is 10 minutes
    public void decreaseSatiety() {
        satiety = satiety >= 2 ? satiety - 2 : 0;
    }

    // optimal increase rate for roughly 8 hours of sleep a day considering 1 tick is 10 minutes
    public void increaseEnergy() {
        energy = energy <= 98 ? energy + 2 : 100;
    }

    public void increaseEnergy(int i) {
        energy = energy <= 100-i ? energy + i : 100;
    }

    // optimal increase rate for 30-minute eating session to restore satiety considering 1 tick is 10 minutes
    public void increaseSatiety() {
        satiety = satiety <= 66 ? satiety + 33 : 100;
    }

    public void startPerformingPersonalAction(House house) {
        setBusy(true);
        this.currentFreeTimeActionStartingTime = Timer.getCurrentMinutes();
        FreeTimeAction personalAction = createPersonalAction(house);
        FreeTimeActionList actionType = personalAction.getActionToDo();
        getPerformedActionsLog().add(new ActoredActionLog(Timer.getCurrentMinutes(), ActoredActionLogType.STARTING.name(), actionType.name()));
        Room actionRoom = personalAction.getRoomToDoTheActionIn();
        setCurrentFreeTimeAction(personalAction);
        changeLocation(personalAction.getRoomToDoTheActionIn());
        boolean successfullyStartedFreeTimeAction = actOnFreeTimeAction(actionType, actionRoom);
        if (!successfullyStartedFreeTimeAction) {
            getPerformedActionsLog().add(new ActoredActionLog(Timer.getCurrentMinutes(), ActoredActionLogType.ABORTING_CONTENT_NOT_AVAILABLE.name(), actionType.name()));
            currentFreeTimeAction = null;
            setBusy(false);
        }
    }

    public void finishPerformingPersonalAction(House house) {
        if (currentFreeTimeAction == null) {
            return;
        }
        if (currentFreeTimeActionStartingTime + currentFreeTimeAction.getTimeForAction() == Timer.getCurrentMinutes()) {
            Room actionRoom = currentFreeTimeAction.getRoomToDoTheActionIn();
            FreeTimeActionList actionType = currentFreeTimeAction.getActionToDo();
            cleanUpAfterPersonalAction(actionType, actionRoom, house);
            getPerformedActionsLog().add(new ActoredActionLog(Timer.getCurrentMinutes(), ActoredActionLogType.FINISHING.name(), actionType.name()));
            currentFreeTimeAction = null;
            setBusy(false);
        }
    }

    public FreeTimeAction getCurrentFreeTimeAction() {
        return currentFreeTimeAction;
    }

    public void setCurrentFreeTimeAction(FreeTimeAction currentFreeTimeAction) {
        this.currentFreeTimeAction = currentFreeTimeAction;
    }

    protected abstract boolean actOnFreeTimeAction(FreeTimeActionList actionType, Room actionRoom);

    protected abstract void cleanUpAfterPersonalAction(FreeTimeActionList actionType, Room actionRoom, House house);

    protected abstract FreeTimeAction createPersonalAction(House house);

}
