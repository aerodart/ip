package ev;

import ev.task.Task;
import java.util.Scanner;


/**
 * Handles all interaction with the user, reading input and printing E.V.'s responses.
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
     * Prints E.V.'s startup banner.
     */
    public void showWelcome() {
        System.out.println("E.V. online.");
        System.out.println("Good evening, Jonathan.");
        System.out.println("Systems are operational. What can I do for you?");
        System.out.println();
    }

    /**
     * Prints E.V.'s shutdown message.
     */
    public void showGoodbye() {
        System.out.println();
        System.out.println("E.V. offline. Until next time, Jonathan.");
    }

    /**
     * Prints every task currently in the registry, numbered from one.
     *
     * @param tasks the tasks to list.
     */
    public void showList(TaskList tasks) {
        System.out.println("Current task registry:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints the confirmation shown after a task is added to the registry.
     *
     * @param task the task that was just added.
     * @param taskCount the number of tasks now in the registry.
     */
    public void showAdded(Task task, int taskCount) {
        System.out.println("Task logged:");
        System.out.println("  " + task);
        showRegistryCount(taskCount);
    }

    /**
     * Prints the confirmation shown after a task is removed from the registry.
     *
     * @param task the task that was just removed.
     * @param taskCount the number of tasks now in the registry.
     */
    public void showRemoved(Task task, int taskCount) {
        System.out.println("Task removed:");
        System.out.println("  " + task);
        showRegistryCount(taskCount);
    }

    /**
     * Prints the confirmation shown after a task is marked done.
     *
     * @param task the task that was marked done.
     */
    public void showMarked(Task task) {
        System.out.println("Task completed.");
        System.out.println(task);
    }

    /**
     * Prints the confirmation shown after a task is marked not done.
     *
     * @param task the task that was reopened.
     */
    public void showUnmarked(Task task) {
        System.out.println("Task reopened.");
        System.out.println(task);
    }

    /**
     * Prints how many tasks the registry currently holds.
     *
     * @param taskCount the number of tasks currently in the registry.
     */
    public void showRegistryCount(int taskCount) {
        System.out.println("Registry holds " + taskCount + (taskCount == 1 ? " task." : " tasks."));
    }

    /**
     * Prints an error message to the user.
     *
     * @param message the message describing what went wrong.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Prints the tasks matching a search, numbered from one.
     *
     * @param tasks the matching tasks.
     */
    public void showFound(TaskList tasks) {
        System.out.println("Matching entries in the registry:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Closes the input source.
     */
    public void close() {
        scanner.close();
    }
}
