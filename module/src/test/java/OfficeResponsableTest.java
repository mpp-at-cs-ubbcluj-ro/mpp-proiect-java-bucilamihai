import domain.OfficeResponsable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfficeResponsableTest {

    @Test
    @DisplayName("OfficeResponsableTest")
    public void officeResponsableTest() {
        OfficeResponsable officeResponsable = new OfficeResponsable("Mihai", "parola");
        OfficeResponsable officeResponsable2 = new OfficeResponsable("Mihai", "parola");
        assertTrue(officeResponsable.equals(officeResponsable2));
        assertEquals(officeResponsable.hashCode(), officeResponsable2.hashCode());
        assertEquals("OfficeResponsable{username='Mihai', password='parola'}", officeResponsable.toString());
        assertEquals("Mihai", officeResponsable.getUsername());
        assertEquals("parola", officeResponsable.getPassword());
        officeResponsable.setUsername("Ion");
        officeResponsable.setPassword("parola2");
        assertEquals("Ion", officeResponsable.getUsername());
        assertEquals("parola2", officeResponsable.getPassword());
    }
}
