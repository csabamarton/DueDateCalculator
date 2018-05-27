import java.time.LocalDateTime;

public class Task {
    private LocalDateTime current;
    private long remainingInMin;

    public Task(LocalDateTime start, long remainingInHours) {
        this.current = start;
        this.remainingInMin = remainingInHours * 60;
    }

    public long getRemainingInMin() {
        return remainingInMin;
    }

    public LocalDateTime getCurrent() {
        return current;
    }

    public void reduceRemainingTime(long reduceTime) {
        remainingInMin = remainingInMin - reduceTime;
    }

    public void setTimeBackToBeginingOfTheDay() {
        current = current.withHour(9).withMinute(0);
    }

    public void setCurrent(LocalDateTime current) {
        this.current = current;
    }

    protected void addWeeksOfWorking(long numOfWeeks) {
        setCurrent(current.plusWeeks(numOfWeeks));
        reduceRemainingTime(numOfWeeks * 40 * 60);
    }

    protected void addDaysOfWorking(long numOfDays) {
        setCurrent(current.plusDays(numOfDays));
        reduceRemainingTime(numOfDays * 8 * 60);
    }

    protected void addTimeInSameDay() {
        setCurrent(current.plusMinutes(remainingInMin));
        reduceRemainingTime(0);
    }

}
