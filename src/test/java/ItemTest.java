import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest {

    private Item item;

    @BeforeEach
    void setup() {
        item = new Item("Pork ribs", 350);
    }

    @Test
    void test_getName() {
        assertEquals("Pork ribs", item.getName());
    }

    @Test
    void test_getPrice() {
        assertEquals(350, item.getPrice());
    }

    @Test
    void test_toString() {
        assertTrue(item.toString().contains(item.getName()));
        assertTrue(item.toString().contains(String.valueOf(item.getPrice())));
    }
}
