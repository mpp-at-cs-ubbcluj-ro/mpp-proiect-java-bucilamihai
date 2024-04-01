import domain.Challenge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChallengeTest {

    @Test
    @DisplayName("ChallengeTest")
    public void challengeTest()
    {
        Challenge challenge = new Challenge("poezie", "6-8", 5);
        Challenge challenge2 = new Challenge("poezie", "6-8", 5);
        assertTrue(challenge.equals(challenge2));
        assertEquals(challenge.hashCode(), challenge2.hashCode());
        assertEquals("Challenge{name='poezie', groupAge='6-8', numberOfParticipants=5}", challenge.toString());
        assertEquals("poezie", challenge.getName());
        assertEquals("6-8", challenge.getGroupAge());
        assertEquals(5, challenge.getNumberOfParticipants());
        challenge.setName("desen");
        challenge.setGroupAge("9-11");
        challenge.setNumberOfParticipants(10);
        challenge.increaseNumberOfParticipants();
        assertEquals("desen", challenge.getName());
        assertEquals("9-11", challenge.getGroupAge());
        assertEquals(11, challenge.getNumberOfParticipants());
    }
}
