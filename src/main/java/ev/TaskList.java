package ev;

import ev.task.Task;
import java.util.ArrayList;

/**
 * Represents the registry of tasks E.V. is currently tracking.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty task registry.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the registry.
     *
     * @param task the task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task at the given index and returns it.
     *
     * @param index zero-based index of the task to remove.
     * @return the task that was removed.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index of the task.
     * @return the task stored at that index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns how many tasks the registry holds.
     *
     * @return the number of tasks.
     */
    public int size() {
        return tasks.size();
    }
}
