package ListGPT;

import java.util.ArrayList;

/**
 * Represents a mutable list of Task objects and provides convenience operations
 * to add/remove tasks, mark them done/undone, and render user-facing messages.
 */
public class TaskList {
    /** Backing store of tasks in insertion order. */
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds the given task to the end of the list.
     *
     * @param task the task to add.
     * @return a user-facing confirmation message including the task's string form.
     */
    public String add(Task task) {
        tasks.add(task);
        return "   added: " + task.toString();
    }

    /**
     * Removes and returns a user-facing message for the task at the specified index.
     *
     * @param index the index of the task to remove.
     * @return a user-facing message including the removed task's string form.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public String remove(int index) {
        Task output = tasks.get(index);
        tasks.remove(index);
        return "   " + output.toString();
    }

    /**
     * Returns whether the provided index refers to an element in this list.
     *
     * @param index the index to test.
     * @return true if the index is within range for this list; false otherwise.
     */
    public Boolean contains(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Marks the task at the specified index as done and returns a user-facing message.
     *
     * @param index the index of the task to mark done.
     * @return a multi-line confirmation message including the task's updated string form.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public String markDone(int index) {
        StringBuilder output = new StringBuilder("   Nice! I've marked this task as done:\n");
        output.append("      ").append(tasks.get(index).markDone()).append("\n");
        return output.toString();
    }

    /**
     * Marks the task at the specified index as not done and returns a user-facing message.
     *
     * @param index the index of the task to mark not done.
     * @return a multi-line confirmation message including the task's updated string form.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public String markUndone(int index) {
        StringBuilder output = new StringBuilder("   OK, I've marked this task as not done yet:\n");
        output.append("      ").append(tasks.get(index).markUndone()).append("\n");
        return output.toString();
    }

    /**
     * Returns a multi-line string enumerating the tasks in this list in display order.
     * The enumeration uses 1-based numbering.
     *
     * @return the string representation of this task list.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("   Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            int idx = i + 1;
            Task task = tasks.get(i);
            output.append("   ").append(idx).append(". ").append(task).append("\n");
        }

        return output.toString();
    }

    /**
     * Takes the given keyword and outputs tasks that contain the keyword.
     * @param keyword a keyword.
     * @return a String of tasks that contain the keyword.
     */
    public String find(String keyword) {
        StringBuilder output = new StringBuilder("   Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            int idx = i + 1;
            Task task = tasks.get(i);
            if (task.getDescription().contains(keyword)) {
                output.append("   ").append(idx).append(". ").append(task).append("\n");
            }
        }

        return output.toString();
    }

    /**
     * Returns the backing list of tasks.
     *
     * @return the internal ArrayList of tasks (mutable).
     */
    public ArrayList<Task> exportList() {
        return this.tasks;
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the task count.
     */
    public int getCount() {
        return tasks.size();
    }

    /**
     * Returns a user-facing message describing the current task count with singular/plural handling.
     *
     * @return a message such as "   Now you have 3 tasks in the list.".
     */
    public String getPrettyCount() {
        return "   Now you have " + this.getCount() + " "
                + (tasks.size() == 1 ? "task" : "tasks") + " in the list.";
    }
}
