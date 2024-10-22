package cz.cvut.fel.omo.model.reports;

import cz.cvut.fel.omo.model.actor.Person;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.house.Floor;
import cz.cvut.fel.omo.model.house.Room;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HouseReport {
    public static void generateHouseConfigurationReport(String house) {
        try {
            File file = new File("src/main/java/cz/cvut/fel/omo/reports/HouseConfigurationReport.txt");
            boolean isNewFile = file.createNewFile();

            try (FileWriter writer = new FileWriter(file, isNewFile)) { // Overwrite if new, append otherwise
                writer.write(house);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String extractDeviceType(String input) {
        int dotIndex = input.lastIndexOf('.');
        int atIndex = input.indexOf('@');

        if (dotIndex != -1 && atIndex != -1 && dotIndex < atIndex) {
            return input.substring(dotIndex + 1, atIndex);
        } else {
            return "Invalid Input";  // Or any other error handling
        }
    }

    public static String extractFloorType(String input) {
        int dotIndex = input.lastIndexOf('.');
        int atIndex = input.indexOf('@');

        if (dotIndex != -1 && atIndex != -1 && dotIndex < atIndex) {
            return input.substring(atIndex+1);
        } else {
            return "Invalid Input";  // Or any other error handling
        }
    }


}
