import domain.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {

    @Test
    @DisplayName("EntityTest")
    public void entityTest()
    {
        Entity<Long> entity = new Entity<>();
        entity.setId(1L);
        assertEquals(1L, entity.getId());
        assertEquals("Entity{id=1}", entity.toString());
    }
}
