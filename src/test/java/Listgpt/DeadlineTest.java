package Listgpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests basic functionality of Deadline
 */
class DeadlineTest {
    @Test
    void constructor_validIsoDate_setsDueDate() {
        Deadline d = new Deadline("Submit report", "2025-08-10");
        assertEquals("2025-08-10", d.getDueDate());
    }

    @Test
    void getDueDate_returnsIsoString() {
        Deadline d = new Deadline("Pay bills", "2030-01-01");
        assertEquals("2030-01-01", d.getDueDate());
    }

    @Test
    void toString_containsTypeNameTaskNameAndDate() {
        Deadline d = new Deadline("Do laundry", "2025-08-10");
        String s = d.toString();
        assertTrue(s.startsWith("[D]"));
        assertTrue(s.contains("Do laundry"));
        assertTrue(s.contains("(by: 2025-08-10)"));
    }
}