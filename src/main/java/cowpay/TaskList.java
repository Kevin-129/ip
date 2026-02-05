package cowpay;

import java.util.ArrayList;

import cowpay.task.Task;

/**
 * Represents a list of tasks
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a TaskList with the given tasks
     *
     * @param tasks ArrayList<Task>to initialize the TaskList
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    public ArrayList<Task> asArrayList() {
        return this.tasks;
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }
}
