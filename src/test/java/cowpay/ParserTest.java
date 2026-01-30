package cowpay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cowpay.task.Deadline;
import cowpay.task.Event;

/**
 * Tests the Parser class
 */
public class ParserTest {

    /*
     * Tests the parseEvent method
     */
    @Test
    public void parseEvent_validInput_success() {
        Event e = Parser.parseEvent("SLEEEEEP /from 28/1/2026 2359 /to 29/1/2026 2359");
        assertEquals("SLEEEEEP", e.getDescription());
        assertNotNull(e.getFrom());
        assertNotNull(e.getTo());
    }

    /**
     * Tests the parseEvent method with missing parameters
     */
    @Test
    public void parseEvent_missingParams_exceptionThrown() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, (
                    ) -> Parser.parseEvent("SLEEEEEP")
        );
        assertTrue(ex.getMessage().contains("An event has a description"));
    }

    /**
     * Tests the parseDeadline method
     */
    @Test
    public void parseDeadline_validInput_success() {
        Deadline d = Parser.parseDeadline("submit file /by 29/1/2026 2359");
        assertEquals("submit file", d.getDescription());
        assertNotNull(d.getBy());
    }

    /**
     * Tests the parseDeadline method with missing parameters
     */
    @Test
    public void parseDeadline_missingParams_exceptionThrown() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, (
                ) -> Parser.parseDeadline("submit file")
        );
        assertTrue(ex.getMessage().contains("A deadline has a description"));
    }

}
