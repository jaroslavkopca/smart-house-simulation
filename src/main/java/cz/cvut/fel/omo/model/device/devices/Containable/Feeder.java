package cz.cvut.fel.omo.model.device.devices.Containable;

import cz.cvut.fel.omo.config.Controller.Action;
import cz.cvut.fel.omo.config.Controller.ActionList;
import cz.cvut.fel.omo.config.Controller.Controller;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.device.deviceState.OnState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Feeder extends Device implements Containable {
    private static final int MAX_CAPACITY = 50; // define a maximum capacity for the feeder
    private static final List<String> FOOD_LIST = Arrays.asList("Chicken", "Cat food", "Fish", "Bread"); // foods to choose from
    private List<String> content;

    public Feeder() {
        setState(new OnState());
        this.content = new ArrayList<>();
        refill();
    }

    @Override
    public void addContent(String content) {
        if (this.content.size() < MAX_CAPACITY) { // check if the feeder is full before adding content
            this.content.add(content);
        } else {
//            System.out.println("The feeder is full, can't add more food."); // notify if the Feeder is full
        }
    }

    @Override
    public boolean removeContent() {
        if (!this.content.isEmpty()) { // check if the feeder is empty before removing content
            this.content.remove(0);
            return true;
        } else if (!Controller.getExecutedActions().stream().anyMatch(action -> action.getActionToDo() == ActionList.fullFeeder && this.content.isEmpty())) {
            Controller.addAction(new Action(this, ActionList.fullFeeder));
        }
        return false;
    }

    @Override
    public boolean refill() {
        Random random = new Random();
        while (this.content.size() < MAX_CAPACITY) { // keep adding random food until the feeder is full
            this.content.add(FOOD_LIST.get(random.nextInt(FOOD_LIST.size()))); // get a random food from FOOD_LIST
        }
        return true;
    }

    @Override
    public boolean isNotEmpty(){
        return !content.isEmpty();
    }
}