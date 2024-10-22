package cz.cvut.fel.omo.config.Controller;

public class ActoredActionLog {
    private int timeInMinutes;
    private String actionName;
    private String logTypeName;

    public ActoredActionLog(int timeInMinutes, String logTypeName, String actionName){
        this.timeInMinutes = timeInMinutes;
        this.logTypeName = logTypeName;
        this.actionName = actionName;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public String getLogTypeName() {
        return logTypeName;
    }

    public String getActionName() {
        return actionName;
    }
}
