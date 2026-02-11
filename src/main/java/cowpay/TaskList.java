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

        //Index should be within valid bounds
        assert index >= 0 && index < this.tasks.size() : "Index out of bounds!!";

        return this.tasks.get(index);
    }

    /**
     * Adds a task to the task list
     *
     * @param task Task to add
     */
    public void add(Task task) {

        //Task should not be null
        assert task != null : "Cannot add null task!!";

        tasks.add(task);
    }

    /**
     * Removes a task from the task list
     *
     * @param index Index of task to remove
     * @return Task that was removed
     */
    public Task remove(int index) {

        //Index should be within valid bounds
        assert index >= 0 && index < this.tasks.size() : "Index out of bounds!!";

        return this.tasks.remove(index);
    }

    public ArrayList<Task> asArrayList() {
        return this.tasks;
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }
}
