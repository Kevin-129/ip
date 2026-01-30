package cowpay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import cowpay.task.Task;

/**
 * Tests the TaskList class
 */
public class TaskListTest {

    /**
     * Tests the add method
     */
    @Test
    public void addTask_success() {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.add(new Task("SHOWERRR"));
        assertEquals(1, tasks.size());
    }

    /**
     * Tests the remove method
     */
    @Test
    public void removeTask_success() {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.add(new Task("A"));
        tasks.add(new Task("B"));

        Task removed = tasks.remove(0);
        assertEquals("A", removed.getDescription());
        assertEquals(1, tasks.size());
        assertEquals("B", tasks.get(0).getDescription());
    }

    /**
     * Tests the asArrayList method
     */
    @Test
    public void asArrayList_afterAddAndRemove_returnsArrayList() {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.add(new Task("A"));
        tasks.add(new Task("B"));

        ArrayList<Task> al = tasks.asArrayList();
        assertTrue(al instanceof ArrayList);
        assertEquals(2, al.size());

        tasks.remove(0);

        ArrayList<Task> al2 = tasks.asArrayList();
        assertTrue(al2 instanceof ArrayList);
        assertEquals(1, al2.size());
        assertEquals("B", al2.get(0).getDescription());
    }
}
