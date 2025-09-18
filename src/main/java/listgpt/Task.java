package listgpt;

/**
 * Represents a task with a name and a completion state.
 * The display format follows "[X] name" when completed and "[ ] name" otherwise.
 */
public class Task {

    /** Task name/description. */
    private String description;

    /** Completion flag; set to true if the task is done. */
    private Boolean isComplete;

    /** Task tag; can be left empty */
    private String tag = "";

    /**
     * Constructs a Task with the given name.
     *
     * @param description the task name/description.
     * @throws InvalidTaskException if name is empty.
     */
    public Task(String description) throws InvalidTaskException {
        if (description.isEmpty()) {
            throw new InvalidTaskException("   OOPS!! The description of a todo cannot be empty.");
        }
        this.description = description;
        this.isComplete = false;
    }

    /**
     * Constructs a Task with the given name.
     *
     * @param description the task name/description.
     * @param tag the tag assigned to the Task.
     * @throws InvalidTaskException if name is empty.
     */
    public Task(String description, String tag) throws InvalidTaskException {
        if (description.isEmpty()) {
            throw new InvalidTaskException("   OOPS!! The description of a todo cannot be empty.");
        }
        this.description = description;
        this.isComplete = false;
        this.tag = tag;
    }

    /**
     * Returns the display string for this task in the form "[X] name" or "[ ] name".
     *
     * @return the string representation of this task.
     */
    @Override
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + description + (tag.isEmpty() ? "" : " [tag: " + tag + "]");
    }

    /**
     * Returns the task name/description.
     *
     * @return the task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the task tag.
     *
     * @return the task tag.
     */
    public String getTag() {
        return this.tag;
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
