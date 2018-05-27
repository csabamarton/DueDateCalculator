import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import static com.google.common.truth.Truth.assertWithMessage;

public class DueDateCalculatorTest {

    DueDateCalculator dueDateCalculator = null;
    @Before
    public void setUp() throws Exception {
        dueDateCalculator = new DueDateCalculator();
    }

    @Test
    public void givenADate_shouldGetBackTheRightDayNumber() {
        LocalDateTime monday = LocalDateTime.of(2018, Month.MAY, 28, 15,34);

        DayOfWeek dayOfWeek = dueDateCalculator.getNumOfDay(monday);

        assertWithMessage("Wrong Day number").that(dayOfWeek).isEqualTo(DayOfWeek.MONDAY);
    }
    
}