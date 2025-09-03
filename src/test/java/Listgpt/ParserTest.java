package Listgpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests basic functionality of Parse
 */
public class ParserTest {
    @Test
    void reverseParse_reversesTodo() {
        ToDo t = new ToDo("read book");
        assertEquals("todo read book", Parser.reverseParse(t));
    }

    @Test
    void reverseParse_reversesDeadline() {
        Deadline d = new Deadline("return book", "2025-08-10");
        assertEquals("deadline return book /by 2025-08-10", Parser.reverseParse(d));
    }
}
