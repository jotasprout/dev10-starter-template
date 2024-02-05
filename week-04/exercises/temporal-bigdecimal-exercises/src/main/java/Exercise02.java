import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Exercise02 {

    // LocalDateTime
    // ========================
    // Complete the numbered tasks below.
    // Open and execute the accompanying tests to confirm your solution is correct.

    // 1. return the current date and time as a LocalDateTime
    LocalDateTime getNow() {
        return null;
    }

    // 2. return the current date and 4PM (tea time!) as a LocalDateTime.
    LocalDateTime getTeaTime() {
        return null;
    }

    // 3. add 12 hours to the LocalDateTime parameter and return the value
    LocalDateTime add12Hours(LocalDateTime dateTime) {
        return null;
    }

    // 4. given a LocalDateTime parameter, return a list of the next 4
    // quarter-hour appointments available after the datetime.
    // appointment times should not include seconds even if the time parameter does.
    // ignore seconds and nanoseconds.
    // Examples:
    // time == 16:07:32
    // appointments == 16:15, 16:30, 16:45, 17:00
    //
    // time == 03:00:01
    // appointments == 03:00, 03:15, 03:30, 03:45
    //
    // time == 04:30:00
    // appointments == 04:30, 04:45, 05:00, 05:15
    List<LocalDateTime> getQuarterHourAppointments(LocalDateTime dateTime) {
        return null;
    }
}
