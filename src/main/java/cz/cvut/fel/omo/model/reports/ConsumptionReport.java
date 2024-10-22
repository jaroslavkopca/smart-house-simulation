package cz.cvut.fel.omo.model.reports;

import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.utils.UsefulMethods;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ConsumptionReport {
    List<Device> devices;
    List<String> action;
    List<Double> power;
    List<Double> minutes;
    double totalPower;
    private double totalPowerAllDevices;

    public ConsumptionReport() {
        devices = new ArrayList<>();
        action = new ArrayList<>();
        power = new ArrayList<>();
        minutes = new ArrayList<>();
        totalPower = 0;
        totalPowerAllDevices = 0;
    }

    public void add(Device device, String actionS, double powerConsumed, double minutes_now) {
        devices.add(device);
        action.add(actionS);
        power.add(powerConsumed);
        minutes.add(minutes_now);
        totalPower += powerConsumed;
    }

//    public void generate(){
//        for (int i = 0; i < devices.size(); i++) {
//            System.out.println("Device: " + devices.get(i).toString());
//            System.out.println("Action: " + action.get(i));
//            System.out.println("Power Consumed: " + power.get(i));
//            System.out.println("Time happened: " + UsefulMethods.convertMinutesToTimeString(minutes.get(i).intValue()));
//            System.out.println();
//        }
//        System.out.println("Total power consumed: " + totalPower);
//    }

//
//    public void generate(Device device) {
//        try {
//            // Check if the file exists, create if not
//            File file = new File("src/main/java/cz/cvut/fel/omo/reports/AllDevicesConsumptionReport.txt");
//            boolean isNewFile = file.createNewFile();
//
//            FileWriter writer = new FileWriter(file, true); // true to append to file
//
//            // If new file, write the header
//            if (isNewFile) {
//                writer.write("All Devices Consumption Report\n\n");
//            }
//
//            // Writing device information
//            writer.write("Device Name: " + device.toString() + "\n");
//
//            // Loop through actions, power, and minutes
//            for (int i = 0; i < this.devices.size(); i++) {
//                if (this.devices.get(i).equals(device)) {
//                    writer.write("Action: " + action.get(i) +
//                            "\nPower Consumed: " + power.get(i) +
//                            "\nTime: " + UsefulMethods.convertMinutesToTimeString(minutes.get(i).intValue()) +
//                            "\n\n");
//                }
//            }
//
//            // Writing total power consumption for the device
//            writer.write("Total Power Consumed by " + device.toString() + ": " + getTotalPowerForDevice(device) + "\n\n");
//
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void generate() {
        Set<Device> processedDevices = new HashSet<>();
        try {
            File file = new File("src/main/java/cz/cvut/fel/omo/reports/AllDevicesConsumptionReport.txt");
            boolean isNewFile = file.createNewFile();

            FileWriter writer = new FileWriter(file, true); // true to append to file

            if (isNewFile) {
                writer.write("All Devices Consumption Report\n\n");
            }

            for (Device device : devices) {
                if (processedDevices.contains(device)) {
                    // Skip this device if it's already been processed
                    continue;
                }

                writer.write("DEVICE " + device.toString() +"THIS IS THE ROOM:  "+device.getRoom().getType() + " has this consumption:\n\n");

                Map<String, List<String>> actionDetails = new HashMap<>();

                for (int i = 0; i < this.devices.size(); i++) {
                    if (this.devices.get(i).equals(device)) {
                        String actionKey = action.get(i);
                        String detail = "Time: " + UsefulMethods.convertMinutesToTimeString(minutes.get(i).intValue()) +
                                ", Power Consumed: " + power.get(i);

                        actionDetails.computeIfAbsent(actionKey, k -> new ArrayList<>()).add(detail);
                    }
                }

                for (Map.Entry<String, List<String>> entry : actionDetails.entrySet()) {
                    writer.write(entry.getKey() + " happened totally " + entry.getValue().size() + " times:\n");
                    for (String detail : entry.getValue()) {
                        writer.write(detail + "\n");
                    }
                    writer.write("\n");
                }

                writer.write("Total Power Consumed by DEVICE " + device.toString() + ": " + getTotalPowerForDevice(device) + "\n\n");
                totalPowerAllDevices += getTotalPowerForDevice(device);
                processedDevices.add(device);
            }
            writer.write("Total Power Consumed by All Devices: " + totalPowerAllDevices + "\n\n");
//            System.out.println("Total Power Consumed by All Devices: " + totalPowerAllDevices + "\n\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private double getTotalPowerForDevice(Device device) {
        double totalPowerForDevice = 0;
        for (int i = 0; i < this.devices.size(); i++) {
            if (this.devices.get(i).equals(device)) {
                totalPowerForDevice += power.get(i);
            }
        }
        return totalPowerForDevice;
    }

    public String calculateStatePercentages() {
        Map<String, Integer> stateCounts = new HashMap<>();
        int totalTicks = 0;

        File file = new File("src/main/java/cz/cvut/fel/omo/reports/AllDevicesConsumptionReport.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the specific pattern
                if (line.contains("happened totally")) {
                    String[] parts = line.split(" ");
                    int totallyIndex = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("totally")) {
                            totallyIndex = i;
                            break;
                        }
                    }
                    // Extract the count which is the number after 'totally' and before 'times'
                    int count = Integer.parseInt(parts[totallyIndex + 1]);
//                    String state = "";
                    int atIndex =line.indexOf('@');
                    if (atIndex == -1){
                        String state = "Actions";  //parts[0];  // Adjust as per your data format
                        stateCounts.put(state, stateCounts.getOrDefault(state, 0) + count);
                        totalTicks += count;
                    }else{
                        String state = (atIndex != -1) ? line.substring(0, atIndex) : line;

                        // Removing the package path to get only the state name, e.g., "OnState"
                        int lastDotIndex = state.lastIndexOf('.');
                        state = (lastDotIndex != -1) ? state.substring(lastDotIndex + 1) : state;


                        state = state.trim(); // Removing any trailing whitespace

                        stateCounts.put(state, stateCounts.getOrDefault(state, 0) + count);
                        totalTicks += count;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file.";
        }

        if (totalTicks == 0) {
            return "No data available for state percentages.";
        }
        AtomicReference<Double> totalPercentages = new AtomicReference<>((double) 0);

        StringBuilder result = new StringBuilder("State Percentages:\n");
//        for (Map.Entry<String, Integer> entry : stateCounts.entrySet()) {
//            double percentage = 100.0 * entry.getValue() / totalTicks;
//            result.append(String.format("%s: %.2f%%\n", entry.getKey(), percentage));
//            totalPercentages += percentage;
//        }
        // Sorting entries based on percentage
        int finalTotalTicks = totalTicks;
        stateCounts.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> {
                    double percentage = 100.0 * entry.getValue() / finalTotalTicks;
                    result.append(String.format("%s: %.2f%%\n", entry.getKey(), percentage));
                    totalPercentages.updateAndGet(aDouble -> aDouble + percentage);
                });
        result.append(String.format("%s: %.2f%%\n\n", "Totalne", totalPercentages.get()));

        return result.toString();
    }


}