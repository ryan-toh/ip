package ListGPT;

public class Task {
    private String description;
    private Boolean isComplete;

    public Task(String description) throws InvalidTaskException {
        if (description.isEmpty()) {
            throw new InvalidTaskException("   OOPS!! The description of a todo cannot be empty.");
        }
        this.description = description;
        this.isComplete = false;
    }

    @Override
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + description;
    }

    public String getDescription() {
        return this.description;
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
