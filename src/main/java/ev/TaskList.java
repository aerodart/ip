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
     * Constructs a registry holding the given tasks.
     *
     * @param tasks the tasks to start with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
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

    /**
     * Returns the tasks whose descriptions contain the given text.
     *
     * @param keyword the text to search for.
     * @return a registry holding only the matching tasks.
     */
    public TaskList find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                matches.add(task);
            }
        }

        return new TaskList(matches);
    }
}
