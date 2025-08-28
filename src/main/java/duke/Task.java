package duke;

public class Task {
    private String name;
    private Boolean isComplete;

    public Task(String name) throws InvalidTaskException {
        if (name.isEmpty()) {
            throw new InvalidTaskException("   OOPS!! The description of a todo cannot be empty.");
        }
        this.name = name;
        this.isComplete = false;
    }

    @Override
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + name;
    }

    public String getDescription() {
        return this.name;
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
