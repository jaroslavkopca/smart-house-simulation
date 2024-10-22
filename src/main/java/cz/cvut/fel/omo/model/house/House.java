package cz.cvut.fel.omo.model.house;

import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.actor.Person;
import cz.cvut.fel.omo.model.actor.Pet;
import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.utils.RoomType;

import java.util.*;

public class House extends HouseComponent {

    private int houseCapacity;
    private List<Person> people = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();
    private List<Floor> floors = new ArrayList<>();

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public List<Floor> getFloors(){
        return this.floors;
    }

    public House(int houseCapacity){
        this.houseCapacity = houseCapacity;
    }

    @Override
    public void switchOffElectricity() {
        for (Floor floor : floors) {
            floor.switchOffElectricity();
        }
    }


    @Override
    public int getConsumptionReport() {
        int total = 0;
        for (Floor floor : floors) {
            total += floor.getConsumptionReport();
        }
        return total;
    }

    public Room[] getRooms() {
        List<Room> rooms = new ArrayList<>();
        for (Floor floor : floors) {
            rooms.addAll(floor.getRooms());
        }
        return rooms.toArray(new Room[0]);
    }

    public List<Room> getBedrooms(){
        return Arrays.stream(getRooms()).filter(
                room -> room.getType() == RoomType.BEDROOM
        ).toList();
    }

    public Room getKitchen(){
        Optional<Room> kitchen = Arrays.stream(this.getRooms()).filter(room -> room.getType() == RoomType.KITCHEN).findFirst();
        if (kitchen.isPresent()){
            return kitchen.get();
        } else {
            return null;
        }
    }

    public Room getGarage(){
        Optional<Room> garage = Arrays.stream(this.getRooms()).filter(room -> room.getType() == RoomType.GARAGE).findFirst();
        if (garage.isPresent()){
            return garage.get();
        } else {
            return null;
        }
    }

    public void generateHouseConfigReport(){

    }


    public List<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public int getHouseCapacity() {
        return houseCapacity;
    }

    public void setHouseCapacity(int houseCapacity) {
        this.houseCapacity = houseCapacity;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Actor> getActors() {
        List<Actor> actors = new ArrayList<>();
        actors.addAll(people);
        actors.addAll(pets);
        return actors;

    }

    @Override
    public String getConfigurationReport() {
        StringBuilder report = new StringBuilder("House Configuration:\n");
        for (Floor floor : floors) {
            report.append(floor.getConfigurationReport());
        }
        return report.toString();
    }

    public List<Device> getAllDevices() {
        return getFloors().stream()  // Stream<Floor>
                .flatMap(floor -> floor.getRooms().stream())  // Stream<Room>
                .flatMap(room -> room.getDevices().stream())  // Stream<Device>
                .toList();  // Collecting all devices into a list
    }
}
