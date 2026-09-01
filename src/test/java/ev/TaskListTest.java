package ev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ev.task.Todo;

/**
 * Contains unit tests for {@link TaskList}.
 */
public class TaskListTest {
    @Test
    public void add_threeTasks_sizeReflectsCount() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("borrow book"));
        tasks.add(new Todo("return book"));
        tasks.add(new Todo("buy milk"));

        assertEquals(3, tasks.size());
    }

    @Test
    public void remove_middleTask_returnsItAndShrinksList() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("first"));
        tasks.add(new Todo("second"));
        tasks.add(new Todo("third"));

        assertEquals("[T][ ] second", tasks.remove(1).toString());
        assertEquals(2, tasks.size());
        assertEquals("[T][ ] third", tasks.get(1).toString());
    }

    @Test
    public void find_matchingKeyword_returnsOnlyMatches() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("borrow book"));
        tasks.add(new Todo("return book"));
        tasks.add(new Todo("buy milk"));

        TaskList matches = tasks.find("book");

        assertEquals(2, matches.size());
        assertEquals("[T][ ] borrow book", matches.get(0).toString());
        assertEquals("[T][ ] return book", matches.get(1).toString());
    }

    @Test
    public void find_noMatch_returnsEmptyList() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("borrow book"));

        assertEquals(0, tasks.find("spiderman").size());
    }

    @Test
    public void find_doesNotModifyOriginalList() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("borrow book"));
        tasks.add(new Todo("buy milk"));
        tasks.find("book");

        assertEquals(2, tasks.size());
    }
}
