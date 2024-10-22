package cz.cvut.fel.omo.model.house;

import cz.cvut.fel.omo.model.reports.HouseReport;

import java.util.ArrayList;
import java.util.List;

public class Floor extends HouseComponent {
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public void switchOffElectricity() {
        for (Room room : rooms) {
            room.switchOffElectricity();
        }
    }

    @Override
    public int getConsumptionReport() {
        int total = 0;
        for (Room room : rooms) {
            total += room.getConsumptionReport();
        }
        return total;
    }

    @Override
    public String getConfigurationReport() {
        StringBuilder report = new StringBuilder("\t\tFloor"+ HouseReport.extractFloorType(this.toString())+" Configuration:\n");
        for (Room room : rooms) {
            report.append(room.getConfigurationReport());
        }
        return report.toString();
    }
}