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
        LocalDateTime monday = LocalDateTime.of(2018, Month.MAY, 28, 15, 34);

        DayOfWeek dayOfWeek = dueDateCalculator.getNumOfDay(monday);

        assertWithMessage("Wrong Day number").that(dayOfWeek).isEqualTo(DayOfWeek.MONDAY);
    }

    @Test
    public void givenATurnaroundTime_shouldGetBackNumberOfRoundedDays() {
        int mins = 800;
        int rightNumberOfWorkingDays = 1;

        long days = dueDateCalculator.getNumOfDays(mins);

        assertWithMessage("Wrong number of days").that(days).isEqualTo(rightNumberOfWorkingDays);
    }

    @Test
    public void givenATurnaroundTimeLessThanAWorkingDay_shouldGetBackNumberOfRoundedDays() {
        int hours = 300;
        int rightNumberOfWorkingDays = 0;

        long days = dueDateCalculator.getNumOfDays(hours);

        assertWithMessage("Wrong number of days").that(days).isEqualTo(rightNumberOfWorkingDays);
    }

    @Test
    public void givenADate_shouldGetBackTheRightAmountOfMinutesUntilWeekend() {
        LocalDateTime monday = LocalDateTime.of(2018, Month.MAY, 28, 15, 34);

        int minutesUntilWeekend = dueDateCalculator.calcMinutesUntilWeekend(monday);

        assertWithMessage("Wrong amount of minutes").that(minutesUntilWeekend).isEqualTo(4 * 8 * 60 + 60 + 26);
    }

    @Test
    public void givenATask_WithFinishDateOnTheSameDate_shouldGetBackTheDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 28, 15, 34), 1);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.MAY, 28, 16, 34));
    }

    @Test
    public void givenATask_WithFinishDateOnTheNextDate_shouldGetBackTheDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 28, 15, 34), 4);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.MAY, 29, 11, 34));
    }

    @Test
    public void givenATask_WithFinishingNextMonday_shouldGetBackTheRightDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 25, 15, 34), 4);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.MAY, 28, 11, 34));
    }

    @Test
    public void givenATask_WithFinishingFollowingWeekNextTuesday_shouldGetBackTheRightDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 25, 15, 34), 12);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.MAY, 29, 11, 34));
    }

    @Test
    public void givenATask_WithFinishingInTwoWeeksMonday_shouldGetBackTheRightDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 25, 15, 34), 44);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.JUNE, 4, 11, 34));
    }

    @Test
    public void givenATask_WithFinishingInTwoWeeksTuesday_shouldGetBackTheRightDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 25, 15, 34), 52);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.JUNE, 5, 11, 34));
    }

    @Test
    public void givenATask_WithFinishingInTwoMonthsTuesday_shouldGetBackTheRightDate() {
        LocalDateTime finish = dueDateCalculator.calcFinish(LocalDateTime.of(2018, Month.MAY, 25, 15, 34), 324);

        assertWithMessage("OUCH! Some work is still remained!").that(finish).isEqualTo(LocalDateTime.of(2018, Month.JULY, 23, 11, 34));
    }

}