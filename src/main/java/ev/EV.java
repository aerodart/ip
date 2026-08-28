package ev;

import ev.task.Deadline;
import ev.task.Event;
import ev.task.Task;
import ev.task.Todo;
import java.util.ArrayList;

/**
 * Entry point of E.V., a Spiderman-themed chatbot that reads and executes user commands.
 */
public class EV {
    /** Hides the implicit public constructor; E.V. is never instantiated. */
    private EV() {
    }

    /**
     * Runs the E.V. command loop, reading user input until the bye command is given.
     *
     * @param args command line arguments; not used
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ArrayList<Task> tasks = new ArrayList<>();

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.isEmpty()) {
                    throw new EvException("No command entered.");
                }

                String[] words = input.split(" ", 2);
                Command command = Command.parseKeyword(words[0]);
                String arguments = words.length > 1 ? words[1].trim() : "";

                if (command == Command.BYE) {
                    break;
                }

                if (command == Command.LIST) {
                    ui.showList(tasks);

                    continue;
                }

                if (command == Command.MARK) {
                    int index = parseTaskIndex(arguments, tasks.size());
                    Task task = tasks.get(index);

                    task.markAsDone();

                    ui.showMarked(task);
                    continue;
                }

                if (command == Command.UNMARK) {
                    int index = parseTaskIndex(arguments, tasks.size());
                    Task task = tasks.get(index);

                    task.markAsNotDone();

                    ui.showUnmarked(task);
                    continue;
                }

                if (command == Command.DELETE) {
                    int index = parseTaskIndex(arguments, tasks.size());

                    Task removed = tasks.remove(index);

                    ui.showRemoved(removed, tasks.size());
                    continue;
                }

                if (command == Command.TODO) {
                    if (arguments.isEmpty()) {
                        throw new EvException("A todo needs a description.");
                    }

                    Task task = new Todo(arguments);
                    tasks.add(task);

                    ui.showAdded(task, tasks.size());
                    continue;
                }

                if (command == Command.DEADLINE) {
                    String[] parts = arguments.split(" /by ", 2);

                    if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new EvException("A deadline needs a description and a /by time.");
                    }

                    Task task = new Deadline(parts[0], parts[1]);
                    tasks.add(task);

                    ui.showAdded(task, tasks.size());
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

                    ui.showAdded(task, tasks.size());
                    continue;
                }
            } catch (EvException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();

        ui.close();
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
}
