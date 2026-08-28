package ev;

import ev.task.Task;

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
        TaskList tasks = new TaskList();

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                Command command = Parser.parseCommand(input);
                String arguments = Parser.parseArguments(input);

                if (command == Command.BYE) {
                    break;
                }

                if (command == Command.LIST) {
                    ui.showList(tasks);

                    continue;
                }

                if (command == Command.MARK) {
                    int index = Parser.parseTaskIndex(arguments, tasks.size());
                    Task task = tasks.get(index);

                    task.markAsDone();

                    ui.showMarked(task);
                    continue;
                }

                if (command == Command.UNMARK) {
                    int index = Parser.parseTaskIndex(arguments, tasks.size());
                    Task task = tasks.get(index);

                    task.markAsNotDone();

                    ui.showUnmarked(task);
                    continue;
                }

                if (command == Command.DELETE) {
                    int index = Parser.parseTaskIndex(arguments, tasks.size());

                    Task removed = tasks.remove(index);

                    ui.showRemoved(removed, tasks.size());
                    continue;
                }

                if (command == Command.TODO) {
                    Task task = Parser.parseTodo(arguments);

                    tasks.add(task);

                    ui.showAdded(task, tasks.size());
                    continue;
                }

                if (command == Command.DEADLINE) {
                    Task task = Parser.parseDeadline(arguments);
                    tasks.add(task);

                    ui.showAdded(task, tasks.size());
                    continue;
                }

                if (command == Command.EVENT) {
                    Task task = Parser.parseEvent(arguments);
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
}
