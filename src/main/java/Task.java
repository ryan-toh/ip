public class Task {
    private String name;
    private Boolean isComplete;

    public Task(String name) {
        this.name = name;
        this.isComplete = false;
    }

    @Override
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + name;
    }

    public String markDone() {
        isComplete = true;
        return this.toString();
    }

    public String markUndone() {
        isComplete = false;
        return this.toString();
    }
}
