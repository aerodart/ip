package ev;

import ev.task.Deadline;
import ev.task.Event;
import ev.task.Task;
import ev.task.Todo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads tasks from the data file and saves them back to it.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a storage handler for the given data file path.
     *
     * @param filePath path to the file tasks are stored in.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Returns the tasks recorded in the data file, or an empty list if it does not exist yet.
     *
     * @return the saved tasks.
     * @throws EvException if the file exists but cannot be read or understood.
     */
    public ArrayList<Task> load() throws EvException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (!line.isEmpty()) {
                    tasks.add(decode(line));
                }
            }
        } catch (IOException e) {
            throw new EvException("I could not read my memory banks.");
        }

        return tasks;
    }

    /**
     * Writes every task in the registry to the data file, replacing its previous contents.
     *
     * @param tasks the registry to record.
     * @throws EvException if the file cannot be written.
     */
    public void save(TaskList tasks) throws EvException {
        try {
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (int i = 0; i < tasks.size(); i++) {
                    writer.write(tasks.get(i).toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new EvException("I could not write to my memory banks.");
        }
    }

    /**
     * Returns the task described by one line of the data file.
     *
     * @param line a single encoded task.
     * @return the task that line describes.
     * @throws EvException if the line is not in a format E.V. recognises.
     */
    private Task decode(String line) throws EvException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new EvException("My memory banks are corrupted.");
        }

        Task task;

        switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new EvException("My memory banks are corrupted.");
                }
                task = new Deadline(parts[2], Parser.parseDateTime(parts[3]));
                break;
            case "E":
                if (parts.length < 5) {
                    throw new EvException("My memory banks are corrupted.");
                }
                task = new Event(parts[2], Parser.parseDateTime(parts[3]), Parser.parseDateTime(parts[4]));
                break;
            default:
                throw new EvException("My memory banks are corrupted.");
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        }

        return task;
    }
}
