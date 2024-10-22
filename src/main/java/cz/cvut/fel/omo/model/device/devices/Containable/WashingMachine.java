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

public class WashingMachine extends Device implements Containable {
    private static final int MAX_CAPACITY = 10; // define a maximum capacity for the washing machine
    private static final List<String> CLOTHES_LIST = Arrays.asList("Shirt", "Pants", "Socks", "Jacket", "Underwear"); // clothes to choose from
    private List<String> content;

    public WashingMachine() {
        setState(new OnState());
        this.content = new ArrayList<>();
        refill();
    }

    @Override
    public void addContent(String content) {
        if (this.content.size() < MAX_CAPACITY) { // check if the washing machine is full before adding content
            this.content.add(content);
        } else {
//            System.out.println("The washing machine is full, can't add more clothing."); // notify if the washing machine is full
        }
    }

    @Override
    public boolean removeContent() {
        while (!this.content.isEmpty()) { // keep adding random clothing until the washing machine is full
            this.content.remove(0);

        }
        return true;
    }

    public void empty(){
        while (!this.content.isEmpty()){
            removeContent();
        }
    };

    // in context of
    @Override
    public boolean refill() {
        Random random = new Random();
        if (this.content.size() < MAX_CAPACITY - 1) { // check if the washing machine is empty before removing content
            this.content.add(CLOTHES_LIST.get(random.nextInt(CLOTHES_LIST.size()))); // get a random clothes from CLOTHES_LIST
            return true;
        } else if (!Controller.getExecutedActions().stream().anyMatch(action -> action.getActionToDo() == ActionList.emptyVM && this.content.size() == MAX_CAPACITY - 1)) {
            this.content.add(CLOTHES_LIST.get(random.nextInt(CLOTHES_LIST.size()))); // get a random clothes from CLOTHES_LIST
            Controller.addAction(new Action(this, ActionList.emptyVM));
            return true;
        }
        return false;
    }

    @Override
    public boolean isNotEmpty(){
        return !content.isEmpty();
    }
}