package cz.cvut.fel.omo.model.device.devices.Containable;

public interface Containable {
    void addContent(String content);
    boolean removeContent();
    boolean refill();
    boolean isNotEmpty();
}
