package cz.cvut.fel.omo.model.house;

public abstract class HouseComponent {
    public abstract void switchOffElectricity();
    public abstract int getConsumptionReport();
    public abstract String getConfigurationReport();
}
