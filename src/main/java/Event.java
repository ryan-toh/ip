public class Event extends Task {
    String startDate;
    String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getFrom() {
        return this.startDate;
    }

    public String getTo() {
        return this.endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate + " to:" + endDate + ")";
    }


}
