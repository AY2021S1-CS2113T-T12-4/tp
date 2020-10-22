package seedu.duke.storage;

import seedu.duke.command.ByeCommand;
import seedu.duke.command.CheckCommand;
import seedu.duke.command.Command;
import seedu.duke.command.GoalCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.data.UserData;
import seedu.duke.event.Event;
import seedu.duke.event.Personal;
import seedu.duke.event.Timetable;
import seedu.duke.event.Zoom;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.InvalidTimeUnitException;
import seedu.duke.parser.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class StorageParser {

    public static void saveParser(Storage store, UserData data, Command c) {

        if (c.getClass().equals(ByeCommand.class) || c.getClass().equals(CheckCommand.class)
                || c.getClass().equals(ListCommand.class)) {
            return; //don't need to save, nothing was modified
        }

        store.saveAll(data);

    }

    public static String eventToString(Event activity, String type) {

        ArrayList<String> words = new ArrayList<>();

        switch (type) {

        case "Personal":
            personalToArguments((Personal)activity, words);
            break;
        case "Zoom":
            zoomToArguments((Zoom)activity, words);
            break;
        case "Timetable":
            timetableToArguments((Timetable)activity, words);
            break;
        default:
            System.out.println("Error, wrong data type provided");
            break;
        }

        return String.join(" | ", words);

    }

    public static void handleRepeatSave(ArrayList<String> statuses, ArrayList<Event> activities, int repeatCount) {

        for (int i = 0; i < repeatCount; i++) {
            Boolean repeatedActivityStatus = activities.get(i).getStatus().equals("✓");
            statuses.add(Boolean.toString(repeatedActivityStatus));
        }
    }

    public static void personalToArguments(Personal activity, ArrayList<String> words) {

        ArrayList<String> statuses = new ArrayList<>();



        //obtain the dates and time of the event
        String date = "0";
        String time = "0";
        if (activity.getDate() != null) {
            date = activity.getDate().toString();
        }
        if (activity.getTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
            time = activity.getTime().format(formatter);
        }

        Boolean activityStatus = activity.getStatus().equals("✓");
        statuses.add(Boolean.toString(activityStatus));
        String repeatUnit = "0";
        String repeatNumber = "0";
        if (activity.getRepeatCount() != 0) { //it is a repeated activity
            repeatUnit = activity.getRepeatType().toUpperCase();
            repeatNumber = Integer.toString(activity.getRepeatCount());
            ArrayList<Event> activitiesRepeated = activity.getRepeatEventList();

            handleRepeatSave(statuses, activitiesRepeated, activity.getRepeatCount());

        }

        //obtain the name of the event
        String name = activity.getDescription();
        words.add(name);
        words.add(date);
        words.add(time);
        words.add(repeatUnit);
        words.add(repeatNumber);

        for (int i = 0; i < statuses.size(); i++) {
            boolean isDone = Boolean.parseBoolean(statuses.get(i));
            if (isDone) {
                words.add("T");
            } else {
                words.add("F");
            }
        }

    }

    public static void zoomToArguments(Zoom activity, ArrayList<String> words) {

        ArrayList<String> statuses = new ArrayList<>();



        //obtain the dates and time of the event
        String date = "0";
        String time = "0";
        if (activity.getDate() != null) {
            date = activity.getDate().toString();
        }
        if (activity.getTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
            time = activity.getTime().format(formatter);
        }

        Boolean activityStatus = activity.getStatus().equals("✓");
        statuses.add(Boolean.toString(activityStatus));
        String repeatUnit = "0";
        String repeatNumber = "0";
        if (activity.getRepeatCount() != 0) { //it is a repeated activity
            repeatUnit = activity.getRepeatType().toUpperCase();
            repeatNumber = Integer.toString(activity.getRepeatCount());
            ArrayList<Event> activitiesRepeated = activity.getRepeatEventList();

            handleRepeatSave(statuses, activitiesRepeated, activity.getRepeatCount());

        }

        String url = activity.getZoomLink();
        //obtain the name of the event
        String name = activity.getDescription();
        words.add(name);
        words.add(date);
        words.add(time);
        words.add(repeatUnit);
        words.add(repeatNumber);
        words.add(url);

        for (int i = 0; i < statuses.size(); i++) {
            boolean isDone = Boolean.parseBoolean(statuses.get(i));
            if (isDone) {
                words.add("T");
            } else {
                words.add("F");
            }
        }

    }

    public static void timetableToArguments(Timetable activity, ArrayList<String> words) {

        ArrayList<String> statuses = new ArrayList<>();



        //obtain the dates and time of the event
        String date = "0";
        String time = "0";
        if (activity.getDate() != null) {
            date = activity.getDate().toString();
        }
        if (activity.getTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
            time = activity.getTime().format(formatter);
        }

        Boolean activityStatus = activity.getStatus().equals("✓");
        statuses.add(Boolean.toString(activityStatus));
        String repeatUnit = "0";
        String repeatNumber = "0";
        if (activity.getRepeatCount() != 0) { //it is a repeated activity
            repeatUnit = activity.getRepeatType().toUpperCase();
            repeatNumber = Integer.toString(activity.getRepeatCount());
            ArrayList<Event> activitiesRepeated = activity.getRepeatEventList();

            handleRepeatSave(statuses, activitiesRepeated, activity.getRepeatCount());

        }

        String location = activity.getLocation();
        //obtain the name of the event
        String name = activity.getDescription();
        words.add(name);
        words.add(date);
        words.add(time);
        words.add(repeatUnit);
        words.add(repeatNumber);
        words.add(location);

        for (int i = 0; i < statuses.size(); i++) {
            boolean isDone = Boolean.parseBoolean(statuses.get(i));
            if (isDone) {
                words.add("T");
            } else {
                words.add("F");
            }
        }

    }

    public static Event stringToEvent(String line, String type) {
        String[] words = line.split("\\|");
        String[] statuses;
        String[] info;

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
        switch (type) {
        case "Personal":
            info = Arrays.copyOfRange(words, 0, 5);
            statuses = Arrays.copyOfRange(words, 5, words.length);
            return makePersonal(info, statuses);
        case "Zoom":
            info = Arrays.copyOfRange(words, 0, 6);
            statuses = Arrays.copyOfRange(words, 6, words.length);
            return makeZoom(info, statuses);
        case "Timetable":
            info = Arrays.copyOfRange(words, 0, 6);
            statuses = Arrays.copyOfRange(words, 6, words.length);
            return makeTimetable(info, statuses);
        default:
            return null;
        }
    }

    private static Personal makePersonal(String[] info, String[] statuses) {
        //0 is name, 1 is date, 2 is time, 3 is repeat unit, 4 is repeat number
        try {
            Personal p = new Personal(info[0]);
            if (info[1].equals("0")) {
                //no date, event can be returned as is
                setDone(p, statuses[0]);
                return p;
            } else if (info[2].equals("0")) {
                //no time, but got date
                LocalDate date = DateTimeParser.dateParser(info[1]);
                LocalTime time = null;
                p.setDate(date);
                p.setHasDate(true);
            } else {
                //has both date and time
                LocalDate date = DateTimeParser.dateParser(info[1]);
                LocalTime time = DateTimeParser.timeParser(info[2]);
                p.setDate(date);
                p.setHasDate(true);
                p.setTime(time);
                p.setHasTime(true);
            }
            setDone(p, statuses[0]);
            repeatSetter(p, statuses, info[3], info[4]);
            return p;
        } catch (Exception e) {
            System.out.println("file corruption detected");
        }
        return null;
    }

    private static Zoom makeZoom(String[] info, String[] statuses) {
        //0 is name, 1 is date, 2 is time, 3 is repeat unit, 4 is repeat number, 5 is zoom link
        try {
            Zoom z = new Zoom(info[0], info[5]);
            if (info[1].equals("0")) {
                //no date, event can be returned as is
                setDone(z, statuses[0]);
                return z;
            } else if (info[2].equals("0")) {
                //no time, but got date
                LocalDate date = DateTimeParser.dateParser(info[1]);
                LocalTime time = null;
                z.setDate(date);
            } else {
                //has both date and time
                LocalDate date = DateTimeParser.dateParser(info[1]);
                LocalTime time = DateTimeParser.timeParser(info[2]);
                z.setDate(date);
                z.setTime(time);
            }
            setDone(z, statuses[0]);
            repeatSetter(z, statuses, info[3], info[4]);
            return z;
        } catch (Exception e) {
            System.out.println("file corruption detected");
        }
        return null;
    }

    private static Timetable makeTimetable(String[] info, String[] statuses) {
        //0 is name, 1 is date, 2 is time, 3 is repeat unit, 4 is repeat number, 5 is location
        try {
            LocalDate date = DateTimeParser.dateParser(info[1]);
            LocalTime time = DateTimeParser.timeParser(info[2]);
            Timetable t = new Timetable(info[0], date, time);
            if (!info[5].equals("0")) { //location provided
                t.setLocation(info[5]);
            }
            setDone(t, statuses[0]);
            repeatSetter(t, statuses, info[3], info[4]);
            return t;
        } catch (Exception e) {
            System.out.println("file corruption detected");
        }
        return null;
    }

    private static void setDone(Event e, String doneStatus) {
        boolean isDone = doneStatus.equals("T");
        if (isDone) {
            e.markAsDone();
        }
    }

    private static void repeatSetter(Event activity, String[] statuses, String timeUnit, String repeatNumber) {

        LocalDate startDate = activity.getDate();
        LocalTime startTime = activity.getTime();
        int count = Integer.parseInt(repeatNumber);
        if (count == 0) {
            return;
        }
        ArrayList<Event> repeatEventList = new ArrayList<>();

        try {
            for (int i = 1; i <= count; i++) {
                LocalDate repeatDate;
                switch (timeUnit) {
                case "MONTHLY":
                    repeatDate = startDate.plusMonths(i);
                    break;
                case "WEEKLY":
                    repeatDate = startDate.plusWeeks(i);
                    break;
                case "DAILY":
                    repeatDate = startDate.plusDays(i);
                    break;
                default:
                    throw new InvalidTimeUnitException(timeUnit);
                }
                activity.setRepeatType(timeUnit);
                Event repeatEvent;
                repeatEvent = activity.clone();
                repeatEvent.setDate(repeatDate);
                if (statuses[i].equals("T")) {
                    repeatEvent.markAsDone();
                }
                repeatEventList.add(repeatEvent);
            }
            activity.setRepeatEventList(repeatEventList);
        } catch (Exception e) {
            System.out.println("Error, wrong date should not happen, file corrupted");
            //throw new DukeException("Cant clone");
        }

    }
}