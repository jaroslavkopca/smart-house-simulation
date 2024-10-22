package cz.cvut.fel.omo;

import cz.cvut.fel.omo.config.Controller.Controller;
import cz.cvut.fel.omo.config.Decay;
import cz.cvut.fel.omo.config.Outside;
import cz.cvut.fel.omo.config.SimulationConfig;
import cz.cvut.fel.omo.config.Timer;
import cz.cvut.fel.omo.config.houseBuilder.HouseDirector;
import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.actor.Person;
import cz.cvut.fel.omo.model.actor.Pet;
import cz.cvut.fel.omo.model.device.DataCollector;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.house.House;
import cz.cvut.fel.omo.model.house.Room;
import cz.cvut.fel.omo.model.reports.ConsumptionReport;
import cz.cvut.fel.omo.model.reports.ControllerReport;
import cz.cvut.fel.omo.model.reports.HouseReport;
import cz.cvut.fel.omo.model.reports.PeopleAndPetsActionsReport;
import cz.cvut.fel.omo.service.RoomService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Path to the consumption report file
        String filePath = "src/main/java/cz/cvut/fel/omo/reports/AllDevicesConsumptionReport.txt";

        // Delete the file if it exists
        File file = new File(filePath);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (isDeleted) {
//                System.out.println(filePath + " has been deleted.");
            } else {
                System.out.println("Could not delete " + filePath);
            }
        }

        House house = new HouseDirector().buildHouse(20);
        Controller controller = new Controller();
        Outside outside = new Outside();

        SimulationConfig config = new SimulationConfig();
        config.configureDevices(house);
        config.configurePeopleAndPets(house);

        List<RoomService> roomServiceList = new ArrayList<>();
        for (Room room : house.getRooms()) {
            roomServiceList.add(new RoomService(room, outside));
        }

        Decay decay = new Decay(roomServiceList, config.getAllDevices(house), config.getAllActors(house));

        boolean oneDay = true;


//        controller.addAction(new Action(new Refrigirator(), new Person("Pepa"), ActionList.fullRefrigerator));


        while (oneDay) {
            outside.update(Timer.getCurrentMinutes());
            decay.decay();
            if (Timer.getCurrentMinutes() % 30 == 0) {
                outside.update(Timer.getCurrentMinutes());
                roomServiceList.forEach(roomService -> roomService.measure());
            }


            // Check for a person who is not busy and is free to look at the controller
            for (Actor actor : house.getActors()) {
                Random random = new Random();
                boolean choice = random.nextBoolean();
                if (actor instanceof Person) {
                    if (!actor.isBusy()) {
                        if (choice) {
                            controller.look((Person) actor);
                        } else {
                            actor.startPerformingPersonalAction(house);
                        }
                    }
                } else if (actor instanceof Pet) {
                    if (!actor.isBusy()) {
                        actor.startPerformingPersonalAction(house);
                    }
                }
            }

            //Perform action if the time for the action passed
            for (Person person : house.getPeople()) {
                person.performAction();
                person.finishPerformingPersonalAction(house);
            }

            for (Pet pet : house.getPets()) {
                pet.finishPerformingPersonalAction(house);
            }

            for (Device device : config.getAllDevices(house)) {
                device.updateConsumption();
            }

            Timer.tick();

            if (Timer.getCurrentMinutes() == 5000) {
                oneDay = false;
            }
        }

        DataCollector.getConsumptionReport().generate();

        ControllerReport controllerReport = new ControllerReport();
        controllerReport.generateReport();

        PeopleAndPetsActionsReport.createReport(house.getPeople(),house.getPets());

        String houseConfigurationReport = house.getConfigurationReport();
        HouseReport.generateHouseConfigurationReport(houseConfigurationReport);

        System.out.println(""+"Finished loading reports." +"\n");


        System.out.println(DataCollector.getConsumptionReport().calculateStatePercentages());
    }
}