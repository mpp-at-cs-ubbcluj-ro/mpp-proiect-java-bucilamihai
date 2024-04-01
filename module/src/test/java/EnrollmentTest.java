import domain.Challenge;
import domain.Child;
import domain.Enrollment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnrollmentTest {

    @Test
    @DisplayName("EnrollmentTest")
    public void enrollmentTest() {
        Child child = new Child(1234567890123L, "Mihai", 7);
        Child child2 = new Child(1234567890123L, "Mihai", 7);
        Challenge challenge = new Challenge("poezie", "6-8", 5);
        Challenge challenge2 = new Challenge("poezie", "6-8", 5);
        Enrollment enrollment = new Enrollment(child, challenge);
        Enrollment enrollment2 = new Enrollment(child2, challenge2);
        assertTrue(enrollment.equals(enrollment2));
        assertEquals(enrollment.hashCode(), enrollment2.hashCode());
        assertEquals("Enrollment{child=Child{cnp=1234567890123, name='Mihai', age=7}, challenge=Challenge{name='poezie', groupAge='6-8', numberOfParticipants=5}}", enrollment.toString());
        assertEquals(child, enrollment.getChild());
        assertEquals(challenge, enrollment.getChallenge());
        enrollment.setChild(child2);
        enrollment.setChallenge(challenge2);
        assertEquals(child2, enrollment.getChild());
        assertEquals(challenge2, enrollment.getChallenge());
    }
}
