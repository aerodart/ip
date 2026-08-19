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
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            }

            if (input.equals("list")) {
                System.out.println("Current task registry:");

                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }

                continue;
            }

            if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int index = taskNumber - 1;

                tasks[index].markAsDone();

                System.out.println("Task completed.");
                System.out.println(tasks[index]);

                continue;
            }

            if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                int index = taskNumber - 1;

                tasks[index].markAsNotDone();

                System.out.println("Task reopened.");
                System.out.println(tasks[index]);

                continue;
            }

            if (input.startsWith("todo ")) {
                String description = input.substring("todo ".length());

                tasks[taskCount] = new Todo(description);
                taskCount++;

                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            if (input.startsWith("deadline ")) {
                String details = input.substring("deadline ".length());
                String[] parts = details.split(" /by ", 2);

                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;

                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            if (input.startsWith("event ")) {
                String details = input.substring("event ".length());
                String[] fromParts = details.split(" /from ", 2);
                String[] timeParts = fromParts[1].split(" /to ", 2);

                tasks[taskCount] = new Event(fromParts[0], timeParts[0], timeParts[1]);
                taskCount++;

                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            System.out.println("Command not recognised.");
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
}