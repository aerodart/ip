package ev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ev.task.Deadline;
import ev.task.Task;
import ev.task.Todo;

/**
 * Contains unit tests for {@link Storage}.
 */
public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void load_fileDoesNotExist_returnsEmptyList() throws EvException {
        Storage storage = new Storage(tempDir.resolve("missing.txt").toString());

        assertEquals(0, storage.load().size());
    }

    @Test
    public void saveThenLoad_todoAndDeadline_surviveRoundTrip() throws EvException {
        Storage storage = new Storage(tempDir.resolve("ev.txt").toString());
        TaskList tasks = new TaskList();

        tasks.add(new Todo("borrow book"));
        tasks.add(new Deadline("return book", LocalDateTime.of(2026, 9, 18, 18, 0)));
        tasks.get(0).markAsDone();

        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("[T][X] borrow book", loaded.get(0).toString());
        assertEquals("[D][ ] return book (by: Sep 18 2026, 6:00PM)", loaded.get(1).toString());
    }

    @Test
    public void load_unknownTaskType_throwsEvException() throws IOException {
        Path file = tempDir.resolve("ev.txt");
        Files.writeString(file, "X | 0 | not a real task type\n");

        Storage storage = new Storage(file.toString());

        assertThrows(EvException.class, storage::load);
    }

    @Test
    public void load_tooFewFields_throwsEvException() throws IOException {
        Path file = tempDir.resolve("ev.txt");
        Files.writeString(file, "T | 0\n");

        Storage storage = new Storage(file.toString());

        assertThrows(EvException.class, storage::load);
    }
}
