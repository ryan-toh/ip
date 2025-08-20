public class TaskList {
    private Task[] tasks = new Task[100];
    private int taskInd = 0;

    public String add(Task t) {
        tasks[taskInd++] = t;
        return "added: " + t.toString();
    }

    public Boolean contains(int ind) {
        return ind < taskInd;
    }

    public String markDone(int ind) {
        StringBuilder output = new StringBuilder("   Nice! I've marked this task as done:\n");
        output.append("      ").append(tasks[ind].markDone()).append("\n");
        return output.toString();
    }

    public String markUndone(int ind) {
        StringBuilder output = new StringBuilder("   OK, I've marked this task as not done yet:\n");
        output.append("      ").append(tasks[ind].markUndone()).append("\n");
        return output.toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("   Here are the tasks in your list:\n");
        for (int i = 0; i < taskInd; i++) {
            int idx = i + 1;
            output.append("   ").append(idx).append(". ").append(tasks[i]).append("\n");
        }

        return output.toString();
    }
}
