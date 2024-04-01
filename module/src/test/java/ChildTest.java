import domain.Child;
import domain.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChildTest {
    @Test
    @DisplayName("ChildTest")
    public void childTest() {
        Child child = new Child(1234567890123L, "Mihai", 10);
        Child child2 = new Child(1234567890123L, "Mihai", 10);
        assertTrue(child.equals(child2));
        assertEquals(child.hashCode(), child2.hashCode());
        assertEquals("Child{cnp=1234567890123, name='Mihai', age=10}", child.toString());
        assertEquals(1234567890123L, child.getCnp());
        assertEquals("Mihai", child.getName());
        assertEquals(10, child.getAge());
        child.setCnp(1234567890124L);
        child.setName("Ion");
        child.setAge(11);
        assertEquals(1234567890124L, child.getCnp());
        assertEquals("Ion", child.getName());
        assertEquals(11, child.getAge());
    }
}
