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
     * Starts E.V. and runs it until the user says bye.
     *
     * @param args command line arguments; not used
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList tasks = new TaskList();

        ui.showWelcome();
        runCommandLoop(ui, tasks);
        ui.showGoodbye();
        ui.close();
    }

    /**
     * Reads and executes user commands until the bye command is given.
     *
     * @param ui the user interface to read from and write to
     * @param tasks the registry the commands act on
     */
    private static void runCommandLoop(Ui ui, TaskList tasks) {
        while (true) {
            String input = ui.readCommand();

            try {
                Command command = Parser.parseCommand(input);

                if (command == Command.BYE) {
                    break;
                }

                execute(command, Parser.parseArguments(input), tasks, ui);
            } catch (EvException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Carries out a single command against the task registry.
     *
     * @param command the command to carry out
     * @param arguments the text following the command word
     * @param tasks the registry the command acts on
     * @param ui the user interface used to report the result
     * @throws EvException if the command's arguments are invalid
     */
    private static void execute(Command command, String arguments, TaskList tasks, Ui ui)
            throws EvException {
        switch (command) {
        case LIST:
            ui.showList(tasks);
            break;
        case MARK:
            markTask(arguments, tasks, ui);
            break;
        case UNMARK:
            unmarkTask(arguments, tasks, ui);
            break;
        case DELETE:
            deleteTask(arguments, tasks, ui);
            break;
        case TODO:
            addTask(Parser.parseTodo(arguments), tasks, ui);
            break;
        case DEADLINE:
            addTask(Parser.parseDeadline(arguments), tasks, ui);
            break;
        case EVENT:
            addTask(Parser.parseEvent(arguments), tasks, ui);
            break;
        default:
            break;
        }
    }

    /**
     * Adds a task to the registry and reports it to the user.
     *
     * @param task the task to add
     * @param tasks the registry to add to
     * @param ui the user interface used to report the result
     */
    private static void addTask(Task task, TaskList tasks, Ui ui) {
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }

    /**
     * Marks the task named by the arguments as done.
     *
     * @param arguments the text following the command word
     * @param tasks the registry holding the task
     * @param ui the user interface used to report the result
     * @throws EvException if the arguments name no existing task
     */
    private static void markTask(String arguments, TaskList tasks, Ui ui) throws EvException {
        Task task = tasks.get(Parser.parseTaskIndex(arguments, tasks.size()));

        task.markAsDone();
        ui.showMarked(task);
    }

    /**
     * Marks the task named by the arguments as not done.
     *
     * @param arguments the text following the command word
     * @param tasks the registry holding the task
     * @param ui the user interface used to report the result
     * @throws EvException if the arguments name no existing task
     */
    private static void unmarkTask(String arguments, TaskList tasks, Ui ui) throws EvException {
        Task task = tasks.get(Parser.parseTaskIndex(arguments, tasks.size()));

        task.markAsNotDone();
        ui.showUnmarked(task);
    }

    /**
     * Removes the task named by the arguments from the registry.
     *
     * @param arguments the text following the command word
     * @param tasks the registry to remove from
     * @param ui the user interface used to report the result
     * @throws EvException if the arguments name no existing task
     */
    private static void deleteTask(String arguments, TaskList tasks, Ui ui) throws EvException {
        Task removed = tasks.remove(Parser.parseTaskIndex(arguments, tasks.size()));

        ui.showRemoved(removed, tasks.size());
    }
}
