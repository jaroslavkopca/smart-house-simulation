package cz.cvut.fel.omo.model.reports;

import cz.cvut.fel.omo.config.Controller.ActoredActionLog;
import cz.cvut.fel.omo.model.actor.Actor;
import cz.cvut.fel.omo.model.actor.Person;
import cz.cvut.fel.omo.model.actor.Pet;
import cz.cvut.fel.omo.model.utils.UsefulMethods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PeopleAndPetsActionsReport {

    public static void createReport(List<Person> people, List<Pet> pets) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("PEOPLE REPORT\n\n");

        for (Person person : people) {
            appendActorActions(reportBuilder, person);
        }

        reportBuilder.append("\nPETS REPORT\n\n");

        for (Pet pet : pets) {
            appendActorActions(reportBuilder, pet);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/cz/cvut/fel/omo/reports/PeopleAndPetsActionsReport.txt"))) {
            writer.write(reportBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendActorActions(StringBuilder reportBuilder, Actor actor) {
        reportBuilder.append("Name of ").append(actor.getClass().getSimpleName()).append(": ").append(actor.getName()).append("\n");
        for (ActoredActionLog log : actor.getPerformedActionsLog()) {
            String timeString = UsefulMethods.convertMinutesToTimeString(log.getTimeInMinutes());
            reportBuilder.append(timeString).append(" | ").append(log.getActionName()).append(" | ").append(log.getLogTypeName()).append("\n");
        }
        reportBuilder.append("\n");
    }
}
