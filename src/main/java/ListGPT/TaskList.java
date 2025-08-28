package ListGPT;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public String add(Task t) {
        tasks.add(t);
        return "   added: " + t.toString();
    }

    public String remove(int ind) {
        Task output = tasks.get(ind);
        tasks.remove(ind);
        return "   " + output.toString();
    }

    public Boolean contains(int ind) {
        return ind >= 0 && ind <= tasks.size();
    }

    public String markDone(int ind) {
        StringBuilder output = new StringBuilder("   Nice! I've marked this task as done:\n");
        output.append("      ").append(tasks.get(ind).markDone()).append("\n");
        return output.toString();
    }

    public String markUndone(int ind) {
        StringBuilder output = new StringBuilder("   OK, I've marked this task as not done yet:\n");
        output.append("      ").append(tasks.get(ind).markUndone()).append("\n");
        return output.toString();
    }

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

    public ArrayList<Task> exportList() {
        return this.tasks;
    }

    public int getCount() {
        return tasks.size();
    }

    public String getPrettyCount() {
        return "   Now you have " + this.getCount() + " " + (tasks.size() == 1 ? "task" : "tasks") + " in the list.";
    }
}
