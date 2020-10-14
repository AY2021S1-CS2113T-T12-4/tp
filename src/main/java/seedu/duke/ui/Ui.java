package seedu.duke.ui;

import seedu.duke.event.DateStatusPair;
import seedu.duke.event.Event;
import seedu.duke.event.EventList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);

    }

    public void printDividerLine() {
        System.out.println("_________________________________");
    }

    public void printWelcomeMessage() {
        printDividerLine();
        System.out.println("Welcome to scheduler--;!");
        System.out.println("What can I do for you?");
        printDividerLine();
    }

    public void printByeMessage() {
        printDividerLine();
        System.out.println("Thank you for using scheduler--;!");
        System.out.println("We hope to see you soon!");
        printDividerLine();
    }

    public String receiveCommand() {
        return in.nextLine().trim();
    }

    public void printEventAddedMessage(Event event) {
        System.out.println("You have successfully added this event to your list!");
        System.out.println(event);
        printDividerLine();
    }

    public void printRepeatAdd(Event event) {
        System.out.println(event);
        System.out.println("is now repeating " + event.getRepeatType() + " for " + event.getRepeatCount() + " times.");
        printDividerLine();
    }

    public void printRepeatList(Event event) {
        System.out.println(event + " is also on:");
        ArrayList<DateStatusPair> repeatList = event.getRepeatList();
        for (DateStatusPair pair : repeatList) {
            System.out.println(pair);
        }
        printDividerLine();
    }

    public void printGoalMessage(Event goal) {
        if (goal != null) {
            System.out.println("Goal: " + goal);
        } else {
            System.out.println("You have no goal! Why not set one now?");
        }
        printDividerLine();
    }

    public void printChangeGoalMessage(Event goal) {
        if (goal != null) {
            System.out.println("Goal changed to: " + goal);
        } else {
            System.out.println("No more goal!");
        }
        printDividerLine();
    }

    public void printCheckMessage() {

    }

    /**
     * Lists the name of all EventList in the given EventList ArrayList.
     *
     * @param eventLists EventList ArrayList to list.
     */
    public void printAvailableList(ArrayList<EventList> eventLists) {
        System.out.println("Following are all existing lists:");
        for (EventList list : eventLists) {
            System.out.println(list.getName());
        }
        System.out.println("'list All' will list all existing lists.");
        printDividerLine();
    }

    /**
     * Lists all the events in the given EventList.
     *
     * @param eventList EventList to list.
     */
    public void printList(EventList eventList) {
        String eventListName = eventList.getName();
        ArrayList<Event> events = eventList.getEvents();
        if (events.size() == 0) {
            System.out.println("You have no " + eventListName + " events!");
        } else {
            System.out.println("Here is a list of your " + eventListName + " events:");
            int index = 1;
            for (Event e : events) {
                System.out.println(index + ". " + e);
                if (e.getRepeat() != null) {
                    System.out.println("   Repeated " + e.getRepeatType() + " for " + e.getRepeatCount() + " times.");
                }
                index++;
            }
        }
        printDividerLine();
    }

    public void printDeadlineChangedMessage(Event eventUpdated) {
        System.out.println("You have successfully updated the deadline for this event!");
        System.out.println(eventUpdated);
    }

    public void printStorageSavedMessage() {
        System.out.println("The file has successfully been saved!");
    }

    public void printStorageLoadingErrorMessage() {
        System.out.println("The file does not exist or has been corrupted!");
    }

    /**
     * Prints exception message.
     *
     * @param exceptionMessage String of warning message from various exceptions.
     */
    public void printErrorMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
        printDividerLine();
    }
}