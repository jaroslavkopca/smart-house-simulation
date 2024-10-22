package cz.cvut.fel.omo.config;

import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.actor.Person;
import cz.cvut.fel.omo.model.actor.Pet;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.house.Floor;
import cz.cvut.fel.omo.model.house.House;
import cz.cvut.fel.omo.model.house.Room;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class SimulationConfig {
    public void configureDevices(House house) {
        // Add devices to specific rooms in the house
        for (Floor floor : house.getFloors()) {
            for (Room room : floor.getRooms()) {

            }
        }
    }

    public void configurePeopleAndPets(House house) {
        int maxCapacity = house.getHouseCapacity();
        Random random = new Random();
        for(Room room : house.getBedrooms()){
            String personName = "Person" + (random.nextInt(10000) + 1); // generate random name
            Person person = new Person(personName);
            String petName = "Pet" + (random.nextInt(10000) + 1);
            Pet pet = new Pet(petName);
            house.addPerson(person);
            house.addPet(pet);
            room.addActor(person);
            room.addActor(pet);
            person.setMyBedroom(room);
            person.setCurrentRoom(room);
            pet.setCurrentRoom(room);
            pet.setMyBedroom(room);
        }
    }

    public List<Device> getAllDevices(House house) {
        List<Device> all = new ArrayList<>();
        for (Floor floor : house.getFloors()) {
            for (Room room : floor.getRooms()) {
                all.addAll(room.getDevices());
            }
        }
        return all;
    }

    public List<Actor> getAllActors(House house){
        List<Actor> allActors = new ArrayList<>();
        for (Floor floor : house.getFloors()) {
            for (Room room : floor.getRooms()) {
                allActors.addAll(room.getActors());
            }
        }
        return allActors;

    }
}