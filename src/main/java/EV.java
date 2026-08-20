// Entry point of E.V., a Spiderman: Brand New Day themed chatbot reading and executing user commands for Jonathan Parker?

import java.util.Scanner;

public class EV {
    private static final int MAX_TASKS = 100;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        System.out.println("E.V. online.");
        System.out.println("Good evening, Jonathan.");
        System.out.println("Systems are operational. What can I do for you?");
        System.out.println();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No command entered.");
                continue;
            }

            String[] words = input.split(" ", 2);
            String command = words[0];
            String arguments = words.length > 1 ? words[1].trim() : "";

            if (command.equals("bye")) {
                break;
            }

            if (command.equals("list")) {
                System.out.println("Current task registry:");

                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }

                continue;
            }

            if (command.equals("mark")) {
                int index = parseTaskIndex(arguments, taskCount);

                if (index < 0) {
                    continue;
                }

                tasks[index].markAsDone();

                System.out.println("Task completed.");
                System.out.println(tasks[index]);

                continue;
            }

            if (command.equals("unmark")) {
                int index = parseTaskIndex(arguments, taskCount);

                if (index < 0) {
                    continue;
                }

                tasks[index].markAsNotDone();

                System.out.println("Task reopened.");
                System.out.println(tasks[index]);

                continue;
            }

            boolean isAddCommand = command.equals("todo") || command.equals("deadline")
                    || command.equals("event");

            if (isAddCommand && taskCount == MAX_TASKS) {
                System.out.println("Registry is full.");
                continue;
            }

            if (command.equals("todo")) {
                if (arguments.isEmpty()) {
                    System.out.println("A todo needs a description.");
                    continue;
                }

                tasks[taskCount] = new Todo(arguments);
                taskCount++;

                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            if (command.equals("deadline")) {
                String[] parts = arguments.split(" /by ", 2);

                if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                    System.out.println("A deadline needs a description and a /by time.");
                    continue;
                }

                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;

                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            if (command.equals("event")) {
                String[] fromParts = arguments.split(" /from ", 2);

                if (fromParts.length < 2 || fromParts[0].isEmpty()) {
                    System.out.println("An event needs a description and a /from time.");
                    continue;
                }

                String[] timeParts = fromParts[1].split(" /to ", 2);

                if (timeParts.length < 2 || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
                    System.out.println("An event needs a /to time.");
                    continue;
                }

                tasks[taskCount] = new Event(fromParts[0], timeParts[0], timeParts[1]);
                taskCount++;

                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            System.out.println("I'm sorry Jonathan, but this command seems to be outside my current scope. Try again.");
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
        System.out.println("Registry holds " + taskCount + (taskCount == 1 ? " task." : " tasks."));
    }

    /**
     * Converts the argument of a mark or unmark command into a task index
     *
     * @param arguments the text following the command word
     * @param taskCount the number of tasks currently in the registry
     * @return zero-based index of task or -1 if argument is not a usable task number
     */

    private static int parseTaskIndex(String arguments, int taskCount) {
        int taskNumber;

        try {
            taskNumber = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            System.out.println("Task number must be a number.");
            return -1;
        }

        if (taskNumber < 1 || taskNumber > taskCount) {
            System.out.println("No task with that number.");
            return -1;
        }

        return taskNumber - 1;
    }
}