package ev;

import java.util.Scanner;

import ev.task.Task;

/**
 * Handles interaction with the user, reading input and building E.V.'s replies.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a user interface that reads from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the next line of user input with surrounding whitespace removed.
     *
     * @return the trimmed command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Returns E.V.'s startup banner.
     *
     * @return the greeting shown when E.V. starts.
     */
    public String getWelcome() {
        return "E.V. online.\n"
                + "Good evening, Jonathan.\n"
                + "Systems are operational. What can I do for you?\n";
    }

    /**
     * Returns E.V.'s shutdown message.
     *
     * @return the farewell shown when the user says bye.
     */
    public String getGoodbye() {
        return "\nE.V. offline. Until next time, Jonathan.";
    }

    /**
     * Returns every task currently in the registry, numbered from one.
     *
     * @param tasks the tasks to list.
     * @return the numbered registry listing.
     */
    public String getList(TaskList tasks) {
        StringBuilder builder = new StringBuilder("Current task registry:");

        for (int i = 0; i < tasks.size(); i++) {
            builder.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }

        return builder.toString();
    }

    /**
     * Returns the tasks matching a search, numbered from one.
     *
     * @param tasks the matching tasks.
     * @return the numbered listing of matches.
     */
    public String getFound(TaskList tasks) {
        StringBuilder builder = new StringBuilder("Matching entries in the registry:");

        for (int i = 0; i < tasks.size(); i++) {
            builder.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }

        return builder.toString();
    }

    /**
     * Returns the confirmation shown after a task is added to the registry.
     *
     * @param task the task that was just added.
     * @param taskCount the number of tasks now in the registry.
     * @return the confirmation message.
     */
    public String getAdded(Task task, int taskCount) {
        return "Task logged:\n  " + task + "\n" + getRegistryCount(taskCount);
    }

    /**
     * Returns the confirmation shown after a task is removed from the registry.
     *
     * @param task the task that was just removed.
     * @param taskCount the number of tasks now in the registry.
     * @return the confirmation message.
     */
    public String getRemoved(Task task, int taskCount) {
        return "Task removed:\n  " + task + "\n" + getRegistryCount(taskCount);
    }

    /**
     * Returns the confirmation shown after a task is marked done.
     *
     * @param task the task that was marked done.
     * @return the confirmation message.
     */
    public String getMarked(Task task) {
        return "Task completed.\n" + task;
    }

    /**
     * Returns the confirmation shown after a task is marked not done.
     *
     * @param task the task that was reopened.
     * @return the confirmation message.
     */
    public String getUnmarked(Task task) {
        return "Task reopened.\n" + task;
    }

    /**
     * Returns how many tasks the registry currently holds.
     *
     * @param taskCount the number of tasks currently in the registry.
     * @return the registry count message.
     */
    public String getRegistryCount(int taskCount) {
        return "Registry holds " + taskCount + (taskCount == 1 ? " task." : " tasks.");
    }

    /**
     * Closes the input source.
     */
    public void close() {
        scanner.close();
    }
}
