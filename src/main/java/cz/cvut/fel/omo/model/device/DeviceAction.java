package cz.cvut.fel.omo.model.device;

/**
 * Class defines action that happens in the simulation ticks. They are used for
 * calculating consumption precisely. Command pattern is used, because every Device
 * consume power differently for their action. This pattern allows for it to be easily
 * calculated.
 */
public class DeviceAction implements Command{
    private Device device;
    private String action;
    private double powerConsumed;
    private double minutes;


    public DeviceAction(String action, Device device, double powerConsumed, double minute) {
        this.action = action;
        this.device = device;
        this.powerConsumed = powerConsumed;
        this.minutes = minute;
    }

    public double getPowerConsumed() {
        return powerConsumed;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setPowerConsumed(double powerConsumed) {
        this.powerConsumed = powerConsumed;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }
}
