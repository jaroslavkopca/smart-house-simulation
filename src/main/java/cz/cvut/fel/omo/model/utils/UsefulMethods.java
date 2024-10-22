package cz.cvut.fel.omo.model.utils;

import cz.cvut.fel.omo.model.device.Device;
import cz.cvut.fel.omo.model.house.Room;

import java.util.Optional;

public class UsefulMethods {
    public static <T extends Device> Optional<T> findDeviceInRoom(Class<T> deviceClass, Room room) {
        return room.getDevices().stream()
                .filter(deviceClass::isInstance)
                .map(deviceClass::cast)
                .findFirst();
    }

    public static String convertMinutesToTimeString(int totalMinutes) {
        int minutesInADay = 1440;
        int hoursInADay = 24;

        int days = totalMinutes / minutesInADay;
        int remainingMinutes = totalMinutes % minutesInADay;

        int hours = remainingMinutes / 60;
        int minutes = remainingMinutes % 60;

        return String.format("Day %d, %02d:%02d", days + 1, hours, minutes);
    }
}
