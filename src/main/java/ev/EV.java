package ev;

import ev.task.Deadline;
import ev.task.Event;
import ev.task.Task;
import ev.task.Todo;
import java.util.ArrayList;
import java.util.Scanner;

/** Entry point of E.V., a Spiderman themed chatbot reading and executing user commands for Jonathan Parker? */
public class EV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("E.V. online.");
        System.out.println("Good evening, Jonathan.");
        System.out.println("Systems are operational. What can I do for you?");
        System.out.println();

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                if (input.isEmpty()) {
                    throw new EvException("No command entered.");
                }

                String[] words = input.split(" ", 2);
                Command command = Command.fromKeyword(words[0]);
                String arguments = words.length > 1 ? words[1].trim() : "";

                if (command == Command.BYE) {
                    break;
                }

                if (command == Command.LIST) {
                    System.out.println("Current task registry:");

                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }

                    continue;
                }

                if (command == Command.MARK) {
                    int index = parseTaskIndex(arguments, tasks.size());
                    Task task = tasks.get(index);

                    task.markAsDone();

                    System.out.println("Task completed.");
                    System.out.println(task);
                    continue;
                }

                if (command == Command.UNMARK) {
                    int index = parseTaskIndex(arguments, tasks.size());
                    Task task = tasks.get(index);

                    task.markAsNotDone();

                    System.out.println("Task reopened.");
                    System.out.println(task);
                    continue;
                }

                if (command == Command.DELETE) {
                    int index = parseTaskIndex(arguments, tasks.size());

                    Task removed = tasks.remove(index);

                    System.out.println("Task removed:");
                    System.out.println("  " + removed);
                    printRegistryCount(tasks.size());
                    continue;
                }

                if (command == Command.TODO) {
                    if (arguments.isEmpty()) {
                        throw new EvException("A todo needs a description.");
                    }

                    Task task = new Todo(arguments);
                    tasks.add(task);

                    printAdded(task, tasks.size());
                    continue;
                }

                if (command == Command.DEADLINE) {
                    String[] parts = arguments.split(" /by ", 2);

                    if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new EvException("A deadline needs a description and a /by time.");
                    }

                    Task task = new Deadline(parts[0], parts[1]);
                    tasks.add(task);

                    printAdded(task, tasks.size());
                    continue;
                }

                if (command == Command.EVENT) {
                    String[] fromParts = arguments.split(" /from ", 2);

                    if (fromParts.length < 2 || fromParts[0].isEmpty()) {
                        throw new EvException("An event needs a description and a /from time.");
                    }

                    String[] timeParts = fromParts[1].split(" /to ", 2);

                    if (timeParts.length < 2 || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
                        throw new EvException("An event needs a /to time.");
                    }

                    Task task = new Event(fromParts[0], timeParts[0], timeParts[1]);
                    tasks.add(task);

                    printAdded(task, tasks.size());
                    continue;
                }
            } catch (EvException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println();
        System.out.println("E.V. offline. Until next time, Jonathan.");

        scanner.close();
    }

    /**
     * Prints the confirmation shown after a task is added to the registry.
     *
     * @param task the task that was just added
     * @param taskCount the number of tasks now in the registry
     */
    private static void printAdded(Task task, int taskCount) {
        System.out.println("Task logged:");
        System.out.println("  " + task);
        printRegistryCount(taskCount);
    }

    /**
     * Converts the argument of a mark or unmark command into a task index.
     *
     * @param arguments the text following the command word
     * @param taskCount the number of tasks currently in the registry
     * @return zero-based index of task
     * @throws EvException if the argument is not a number or is not an existing task number
     */
    private static int parseTaskIndex(String arguments, int taskCount) throws EvException {
        int taskNumber;

        try {
            taskNumber = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new EvException("Task number must be a number.");
        }

        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new EvException("No task with that number.");
        }

        return taskNumber - 1;
    }

    /**
     * Prints how many tasks the registry currently holds.
     *
     * @param taskCount the number of tasks currently in the registry
     */
    private static void printRegistryCount(int taskCount) {
        System.out.println("Registry holds " + taskCount + (taskCount == 1 ? " task." : " tasks."));
    }
}
