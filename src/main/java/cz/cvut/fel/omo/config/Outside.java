package cz.cvut.fel.omo.config;

public class Outside {

    private static int outsideTemperatureInCelsius;
    private int outsideLightLevel;

    public Outside() {
        this.outsideTemperatureInCelsius = 0;
        this.outsideLightLevel = 0;
    }

    public void update(int time) {
        if (time >= 360 && time <= 1200) {
            outsideLightLevel = (int)(100 * Math.abs(Math.cos(Math.PI * (time - 360) / 840)));
        } else {
            outsideLightLevel = 0;
        }
        outsideTemperatureInCelsius = (int)(10 + 10 * Math.sin(Math.PI * (time - 300) / 780));
    }

    public static int getOutsideTemperatureInCelsius() {
        return outsideTemperatureInCelsius;
    }

    public int getOutsideLightLevel() {
        return outsideLightLevel;
    }


}
