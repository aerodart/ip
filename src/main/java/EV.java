import java.util.Scanner;

public class EV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Store tasks while program runs
        String[] tasks = new String[100];
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
                    System.out.println((i + 1) + ". " + tasks[i]);
                }

                continue;
            }

            tasks[taskCount] = input;
            taskCount++;

            System.out.println("Task logged: " + input);
        }

        System.out.println();
        System.out.println("E.V. offline. Until next time, Jonathan.");

        scanner.close();
    }
}