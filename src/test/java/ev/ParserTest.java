package ev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@link Parser}.
 */
public class ParserTest {
    @Test
    public void parseTaskIndex_validNumber_returnsZeroBasedIndex() throws EvException {
        assertEquals(0, Parser.parseTaskIndex("1", 3));
        assertEquals(2, Parser.parseTaskIndex("3", 3));
    }

    @Test
    public void parseTaskIndex_notANumber_throwsEvException() {
        EvException thrown = assertThrows(EvException.class, () -> Parser.parseTaskIndex("abc", 3));
        assertEquals("Task number must be a number.", thrown.getMessage());
    }

    @Test
    public void parseTaskIndex_outOfRange_throwsEvException() {
        assertThrows(EvException.class, () -> Parser.parseTaskIndex("0", 3));
        assertThrows(EvException.class, () -> Parser.parseTaskIndex("4", 3));
    }

    @Test
    public void parseDeadline_validInput_returnsDeadline() throws EvException {
        assertEquals("[D][ ] return book (by: Sunday)",
                Parser.parseDeadline("return book /by Sunday").toString());
    }

    @Test
    public void parseDeadline_missingByTime_throwsEvException() {
        EvException thrown = assertThrows(EvException.class, () -> Parser.parseDeadline("return book"));
        assertEquals("A deadline needs a description and a /by time.", thrown.getMessage());
    }
}