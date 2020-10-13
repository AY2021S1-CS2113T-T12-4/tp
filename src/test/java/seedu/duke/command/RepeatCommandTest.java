package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.data.UserData;
import seedu.duke.event.Event;
import seedu.duke.exception.DukeException;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private UserData data = new UserData();
    private Ui ui = new Ui();
    private Storage storage = new Storage("data");

    @BeforeEach
    void setupEventLists() throws DukeException {
        // Add Personal event to data
        String personalInput = "personal party; 09/10/2000; 1300";
        Command addCommand = new AddCommand(personalInput);
        addCommand.execute(data, ui, storage);

        //Add Zoom event to data
        // Add zoom event to data
        String zoomInput = "zoom Math class; zoom.com; 09/10/2000; 1300";
        addCommand = new AddCommand(zoomInput);
        addCommand.execute(data, ui, storage);

        //Add Timetable Event to Data
        String timeTableInput = "timetable Science class; S17; 17/10/2000; 1500";
        addCommand = new AddCommand(timeTableInput);
        addCommand.execute(data, ui, storage);


    }


    @Test
    void repeat_personalEventMonthly_personalEventRepeatedMonthly() throws DukeException {


        // Create Repeat Command
        String inputString = "personal 1 monthly 4";
        System.setOut(new PrintStream(outputStreamCaptor));

        Command repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);
        assertEquals("[P][✕] party on 2000-10-09, 13:00" + System.lineSeparator()
                        + "is now repeating monthly for 4 times." + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());

        //check the dates reported and erase previous output
        inputString = "personal 1";
        outputStreamCaptor.reset();

        repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);

        assertEquals("[P][✕] party on 2000-10-09, 13:00 is also on:"
                        + System.lineSeparator()
                        + "09 Nov 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "09 Dec 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "09 Jan 2001 1:00 PM [✕]" + System.lineSeparator()
                        + "09 Feb 2001 1:00 PM [✕]" + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());
    }

    @Test
    void repeat_zoomEventWeekly_zoomEventRepeatedWeekly() throws DukeException {

        //creating repeat command
        String inputString = "zoom 1 weekly 3";
        System.setOut(new PrintStream(outputStreamCaptor));

        Command repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);
        assertEquals("[Z][✕] Math class, Link: zoom.com on 2000-10-09, 13:00" + System.lineSeparator()
                        + "is now repeating weekly for 3 times." + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());
        //check the dates reported and erase previous output
        inputString = "zoom 1";
        outputStreamCaptor.reset();

        repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);

        assertEquals("[Z][✕] Math class, Link: zoom.com on 2000-10-09, 13:00 is also on:"
                        + System.lineSeparator()
                        + "16 Oct 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "23 Oct 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "30 Oct 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());

    }

    @Test
    void repeat_timeTableEventDaily_timeTableEventRepeatedDaily() throws DukeException {

        //creating repeat command
        String inputString = "timetable 1 daily 3";
        PrintStream outputLoc = new PrintStream(outputStreamCaptor);
        System.setOut(outputLoc);

        Command repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);
        assertEquals("[T][✕] Science class, Location: S17 on 2000-10-17, 15:00" + System.lineSeparator()
                        + "is now repeating daily for 3 times." + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());

        //check the dates reported and erase previous output
        inputString = "timetable 1";
        outputStreamCaptor.reset();

        repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);

        assertEquals("[T][✕] Science class, Location: S17 on 2000-10-17, 15:00 is also on:"
                        + System.lineSeparator()
                        + "18 Oct 2000 3:00 PM [✕]" + System.lineSeparator()
                        + "19 Oct 2000 3:00 PM [✕]" + System.lineSeparator()
                        + "20 Oct 2000 3:00 PM [✕]" + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());
    }

    @Test
    void repeat_personalEventRepeatDailyMonthlyChange_personalEventDailyToMonthly() throws DukeException {

        //create repeat command for daily
        String inputString = "personal 1 daily 4";
        PrintStream outputLoc = new PrintStream(outputStreamCaptor);
        System.setOut(outputLoc);

        Command repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);
        assertEquals("[P][✕] party on 2000-10-09, 13:00" + System.lineSeparator()
                        + "is now repeating daily for 4 times." + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());

        //clear screen and now test changing repeat from daily 4 to monthly 2
        outputStreamCaptor.reset();
        inputString = "personal 1 monthly 2";

        repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);

        assertEquals("[P][✕] party on 2000-10-09, 13:00" + System.lineSeparator()
                        + "is now repeating monthly for 2 times." + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());

        //clear screen and check if monthly repeats were successfully recorded
        outputStreamCaptor.reset();
        inputString = "personal 1";

        repeatCommand = RepeatCommand.parse(inputString);
        repeatCommand.execute(data, ui, storage);

        assertEquals("[P][✕] party on 2000-10-09, 13:00 is also on:"
                        + System.lineSeparator()
                        + "09 Nov 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "09 Dec 2000 1:00 PM [✕]" + System.lineSeparator()
                        + "_________________________________" + System.lineSeparator(),
                outputStreamCaptor.toString());



    }


}