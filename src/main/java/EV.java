import java.util.Scanner;

public class EV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("E.V. online.");
        System.out.println("Good evening, Jonathan.");
        System.out.println("Systems are operational. What can I do for you?");
        System.out.println();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            }

            System.out.println(input);
        }

        System.out.println();
        System.out.println("E.V. offline. Until next time, Jonathan.");

        scanner.close();
    }
}