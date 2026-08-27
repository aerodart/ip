package ev;

/**
 * Represents E.V.'s understood commands, each paired with the keyword the user types.
 */
public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the command whose keyword matches the given word.
     *
     * @param keyword the first word of the user's input
     * @return the matching command
     * @throws EvException if no command uses that keyword
     */
    public static Command parseKeyword(String keyword) throws EvException {
        for (Command command : values()) {
            if (command.keyword.equals(keyword)) {
                return command;
            }
        }

        throw new EvException("I'm sorry Jonathan, but this command seems to be outside "
                + "my current scope. Try again.");
    }
}
