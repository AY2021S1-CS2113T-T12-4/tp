package seedu.duke.ui;

import seedu.duke.event.Event;
import seedu.duke.event.EventList;
import seedu.duke.event.Goal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
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
        ArrayList<Event> repeatEventList = event.getRepeatEventList();
        for (Event e : repeatEventList) {
            System.out.printf("%s ", e.getDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
            System.out.printf("%s ", e.getTime().format(DateTimeFormatter.ofPattern("K:mm a")));
            System.out.printf("[%s]", e.getStatus());
            System.out.println();
        }
        printDividerLine();
    }

    public void printGoalMessage(Goal goal) {
        if (goal != null) {
            System.out.println("Goal: " + goal);
        } else {
            System.out.println("You have no goal! Why not set one now?");
        }
        printDividerLine();
    }

    public void printChangeGoalMessage(Goal goal) {
        if (goal != null) {
            System.out.println("Goal changed to: " + goal);
        } else {
            System.out.println("No more goal!");
        }
        printDividerLine();
    }

    private void printCalendarDivider() {
        System.out.println("---------------------------------------------------------------------------------------");
    }

    public void printCalendar(Map.Entry<LocalDate, ArrayList<Event>> entry) {
        System.out.println(entry.getKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        printCalendarDivider();
        ArrayList<Event> eventsOnDate;
        eventsOnDate = entry.getValue();
        eventsOnDate.sort(Comparator.comparing(Event::getTime));
        for (Event e : eventsOnDate) {
            System.out.println(e.toCalendarString());
        }
        printCalendarDivider();
    }

    public void printCalendarStart(int size, int count) {
        System.out.println("Calendar has " + size + " dates to display");
        if (count > 1) {
            System.out.println(count + " events not on the calendar because they have no date and time");
        } else if (count > 0) {
            System.out.println(count + " event not on the calendar because it has no date and time");
        }
        printCalendarDivider();
    }

    public void printCalendarEnd() {
        System.out.println("End of calendar");
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
                if (e.getRepeatEventList() != null) {
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

    public void printReminder(ArrayList<Event> events) {
        if (events.size() == 0) {
            System.out.println("You have no events today!");
        } else {
            System.out.println("You have the following events today: ");
            Collections.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
            for (int i = 0; i < events.size(); i++) {
                System.out.println(events.get(i).toString());
            }
        }
    }

    public void printEventMarkedDoneMessage(Event doneEvent) {
        System.out.println("You have successfully marked this event as done!");
        System.out.println(doneEvent);
    }

    public void printEventMarkedUndoneMessage(Event undoneEvent) {
        System.out.println("You have successfully marked this event as undone!");
        System.out.println(undoneEvent);
    }

    public void printStorageSavedMessage() {
        System.out.println("The file has successfully been saved!");
    }

    public void printStorageLoadMessage() {
        System.out.println("The file has successfully been loaded!");
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