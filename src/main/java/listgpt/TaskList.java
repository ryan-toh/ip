package listgpt;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public String add(Task task) {
        tasks.add(task);
        return "   added: " + task.toString();
    }

    public String remove(int ind) {
        Task output = tasks.get(ind);
        tasks.remove(ind);
        return "   " + output.toString();
    }

    public Boolean contains(int index) {
        return index >= 0 && index < tasks.size();
    }

    public String markDone(int index) {
        StringBuilder output = new StringBuilder("   Nice! I've marked this task as done:\n");
        output.append("      ").append(tasks.get(index).markDone()).append("\n");
        return output.toString();
    }

    public String markUndone(int index) {
        StringBuilder output = new StringBuilder("   OK, I've marked this task as not done yet:\n");
        output.append("      ").append(tasks.get(index).markUndone()).append("\n");
        return output.toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("   Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            int idx = i + 1;
            output.append("   ").append(idx).append(". ").append(tasks.get(i)).append("\n");
        }

        return output.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final TaskList other = (TaskList) obj;

        if (!this.tasks.equals(other.tasks)) {
            return false;
        }

        return true;
    }

    public ArrayList<Task> exportList() {
        return this.tasks;
    }

    public int getCount() {
        return tasks.size();
    }

    public String getPrettyCount() {
        return "   Now you have " + this.getCount() + " "
                + (tasks.size() == 1 ? "task" : "tasks") + " in the list.";
    }
}
