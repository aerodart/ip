import java.util.Scanner;

public class EV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Store tasks while program runs
        String[] tasks = new String[100];
        int taskCount = 0;
        boolean[] isDone = new boolean[100];

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
                    String status = isDone[i] ? "X" : " ";
                    System.out.println((i + 1) + ".[" + status + "] " + tasks[i]);
                }

                continue;
            }

            if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int index = taskNumber - 1;

                isDone[index] = true;

                System.out.println("Task completed.");
                System.out.println("[X] " + tasks[index]);

                continue;
            }

            if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                int index = taskNumber - 1;

                isDone[index] = false;

                System.out.println("Task reopened.");
                System.out.println("[ ] " + tasks[index]);

                continue;
            }

            tasks[taskCount] = input;
            isDone[taskCount] = false;
            taskCount++;

            System.out.println("Task logged: " + input);
        }

        System.out.println();
        System.out.println("E.V. offline. Until next time, Jonathan.");

        scanner.close();
    }
}