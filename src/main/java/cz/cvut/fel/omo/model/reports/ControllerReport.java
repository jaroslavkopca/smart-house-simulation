package cz.cvut.fel.omo.model.reports;

import cz.cvut.fel.omo.config.Controller.Action;
import cz.cvut.fel.omo.config.Controller.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;

public class ControllerReport {
    public void generateReport() {
        try {
            FileWriter writer = new FileWriter("src/main/java/cz/cvut/fel/omo/reports/ControllerReport.txt");

            // Write header
            writer.write("Controller Report\n\n");

            // Report on action queue
            writer.write("Action Queue:\n");
            writeActionQueue(writer, Controller.getActionQueue());

            // Report on executed actions
            writer.write("Executed Actions:\n");
            writeActionQueue(writer, Controller.getExecutedActions());

            // Report on completed actions
            writer.write("Completed Actions:\n");
            writeActionQueue(writer, Controller.getCompletedActions());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeActionQueue(FileWriter writer, Queue<Action> actions) throws IOException {
        for (Action action : actions) {
            writer.write(action.getActionToDo().toString() + "\n"); // Assuming Action has a meaningful toString() method
        }
        writer.write("\n");
    }
}
