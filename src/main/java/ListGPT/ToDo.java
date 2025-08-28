package ListGPT;

/**
 * Represents a todo task for a simple to-do item.
 */
public class ToDo extends Task {
    /**
     * Constructs a new to-do task with the given description.
     *
     * @param name The description of the to-do task.
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Returns the string representation of the to-do task, prefixed with "[T]"
     * to indicate its type.
     *
     * @return The formatted string representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
