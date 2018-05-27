import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DueDateCalculator {

    public DayOfWeek getNumOfDay(LocalDateTime date) {
        return date.getDayOfWeek();
    }
}
