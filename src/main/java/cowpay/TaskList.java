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
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> asArrayList() {
        return tasks;
    }
}
