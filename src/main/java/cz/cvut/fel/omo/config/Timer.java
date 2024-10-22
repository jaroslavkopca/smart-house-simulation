package cz.cvut.fel.omo.config;

public class Timer {
    private static int currentTimeInMinutes;
    private static int days;

    public Timer() {
        this.days = 0;
        this.currentTimeInMinutes = 0; // Midnight
    }

    // Advances the time by one minute
    public static void tick() {
        currentTimeInMinutes += 10;
//        if (currentTimeInMinutes == 24 * 60) {  // If it's past 24 hours,
//            currentTimeInMinutes = 0;
//            days += 1; // reset to midnight.
//        }
    }

    // Returns the current time as a string in the format "HH:MM"
    public static int getCurrentMinutes() {
        return currentTimeInMinutes ;
    }

    public int getCurrentDays() {
        return days;
    }
}
