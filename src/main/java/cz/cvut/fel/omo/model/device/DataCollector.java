package cz.cvut.fel.omo.model.device;

import cz.cvut.fel.omo.model.reports.ConsumptionReport;

import java.util.List;

public class DataCollector {

    private static ConsumptionReport consumptionReport = new ConsumptionReport();

    public DataCollector() {
    }

    public void collect() {

    }

    public static void collectConsumption(List<DeviceAction> performedActions) {
        for (DeviceAction action : performedActions) {
            double powerConsumed = action.getPowerConsumed();
            Device device = action.getDevice();
            String actionS = action.getAction();
            Double minutes = action.getMinutes();

            consumptionReport.add(device,actionS,powerConsumed, minutes);
        }

    }

    public static ConsumptionReport getConsumptionReport() {
        return consumptionReport;
    }
}