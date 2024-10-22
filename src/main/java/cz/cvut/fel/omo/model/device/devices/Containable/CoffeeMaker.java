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

public class CoffeeMaker extends Device implements Containable {
    private static final int MAX_CAPACITY = 10; // define a maximum capacity for the coffee maker
    private static final List<String> COFFEE_LIST = Arrays.asList("Espresso", "Cappuccino", "Latte", "Mocha", "Americano"); // coffees to choose from
    private List<String> content;

    public CoffeeMaker() {
        setState(new OnState());
        this.content = new ArrayList<>();
        refill();
    }

    @Override
    public void addContent(String content) {
        if (this.content.size() < MAX_CAPACITY) { // check if the coffee maker is full before adding content
            this.content.add(content);
        } else {
//            System.out.println("The coffee maker is full, can't add more coffee."); // notify if the coffee maker is full
        }
    }

    @Override
    public boolean removeContent() {
        if (!this.content.isEmpty()) { // check if the coffee maker is empty before removing content
            this.content.remove(0);
            return true;
        } else if (!Controller.getExecutedActions().stream().anyMatch(action -> action.getActionToDo() == ActionList.FullCM && this.content.isEmpty())) {
            Controller.addAction(new Action(this, ActionList.FullCM));
        }
        return false;
    }

    @Override
    public boolean refill() {
        Random random = new Random();
        while (this.content.size() < MAX_CAPACITY) { // keep adding random coffee until the coffee maker is full
            this.content.add(COFFEE_LIST.get(random.nextInt(COFFEE_LIST.size()))); // get a random coffee from COFFEE_LIST
        }
        return true;
    }

    @Override
    public boolean isNotEmpty(){
        return !content.isEmpty();
    }
}