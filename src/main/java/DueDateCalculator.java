import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DueDateCalculator {

    final int NUMBER_OF_WORKING_DAYS = 5;
    final int NUMBER_OF_WORKING_HOURS = 8;
    final int AMOUNT_OF_MINUTES_IN_A_WORKING_DAY = 8 * 60;

    protected DayOfWeek getNumOfDay(final LocalDateTime date) {
        return date.getDayOfWeek();
    }

    protected long getNumOfDays(final long minutes) {
        return minutes / 60 / NUMBER_OF_WORKING_HOURS;
    }

    protected long getNumOfWeeks(final long days) {

        return days / NUMBER_OF_WORKING_DAYS;
    }


    protected int calcMinutesUntilWeekend(final LocalDateTime date) {
        int untilEndOfTheDay = calcMinutesUntilDayEnd(date);

        int extraDays = 5 - date.getDayOfWeek().getValue();


        return untilEndOfTheDay + extraDays * AMOUNT_OF_MINUTES_IN_A_WORKING_DAY;
    }

    protected int calcMinutesUntilDayEnd(final LocalDateTime date) {
        return (int) date.until(LocalDateTime.from(date).withHour(17).withMinute(0), ChronoUnit.MINUTES);
    }


    public LocalDateTime calcFinish(final LocalDateTime start, final long turnAroundInHours) {
        Task task = new Task(start, turnAroundInHours);

        long numOfDays = getNumOfDays(task.getRemainingInMin());

        if (numOfDays >= 1) {
            long numOfWeeks = getNumOfWeeks(numOfDays);

            if (numOfWeeks >= 1) {
                task.addWeeksOfWorking(numOfWeeks);
            }
        }

        int minutesUntilWeekend = calcMinutesUntilWeekend(task.getCurrent());

        if (minutesUntilWeekend < task.getRemainingInMin()) {
            moveCurentTimeToNextWeek(task, minutesUntilWeekend);
        }

        numOfDays = getNumOfDays(task.getRemainingInMin());

        if (numOfDays >= 1) {
            task.addDaysOfWorking(numOfDays);
        }

        int minutesUntilDayEnd = calcMinutesUntilDayEnd(task.getCurrent());

        if (minutesUntilDayEnd < task.getRemainingInMin()) {
            moveCurrentTimeToNextDay(task, minutesUntilDayEnd, task.getCurrent().plusDays(1));
        }

        task.addTimeInSameDay();

        return task.getCurrent();
    }

    private void moveCurrentTimeToNextDay(final Task task, final int minutesUntilDayEnd, final LocalDateTime current) {
        task.reduceRemainingTime(minutesUntilDayEnd);
        task.setCurrent(current);
        task.setTimeBackToBeginingOfTheDay();
    }

    private void moveCurentTimeToNextWeek(final Task task, final int minutesUntilWeekend) {
        task.reduceRemainingTime(minutesUntilWeekend);
        task.setCurrent(task.getCurrent().with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
        task.setTimeBackToBeginingOfTheDay();
    }

}
