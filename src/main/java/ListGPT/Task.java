package ListGPT;

/**
 * Represents a task with a name and a completion state.
 * The display format follows "[X] name" when completed and "[ ] name" otherwise.
 */
public class Task {

    /** Task name/description. */
    private String name;

    /** Completion flag; set to true if the task is done. */
    private Boolean isComplete;

    /**
     * Constructs a Task with the given name.
     *
     * @param name the task name/description.
     * @throws InvalidTaskException if name is empty.
     */
    public Task(String name) throws InvalidTaskException {
        if (name.isEmpty()) {
            throw new InvalidTaskException("   OOPS!! The description of a todo cannot be empty.");
        }
        this.name = name;
        this.isComplete = false;
    }

    /**
     * Returns the display string for this task in the form "[X] name" or "[ ] name".
     *
     * @return the string representation of this task.
     */
    @Override
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + name;
    }

    /**
     * Returns the task name/description.
     *
     * @return the task description.
     */
    public String getDescription() {
        return this.name;
    }

    /**
     * Returns the display string after marking this task as done.
     *
     * @return the string representation of this task after completion is set to true.
     */
    public String markDone() {
        isComplete = true;
        return this.toString();
    }

    /**
     * Returns the display string after marking this task as not done.
     *
     * @return the string representation of this task after completion is set to false.
     */
    public String markUndone() {
        isComplete = false;
        return this.toString();
    }
}
