package start;

import client.ChallengeClient;
import domain.Challenge;
import service.ServiceException;

public class StartRestClient {

    public static void main(String[] args) {
        ChallengeClient challengeClient = new ChallengeClient();

        Challenge newChallenge = new Challenge("RestClientTest", "6-8", 0);
        System.out.println("Adding a new challenge: " + newChallenge);
        show(() -> challengeClient.create(newChallenge));

        System.out.println("\nPrinting all challenges ...");
        show(() -> {
            Challenge[] challenges = challengeClient.getAll();
            for (Challenge challenge : challenges) {
                System.out.println(challenge.getId() + ": " + challenge.getName());
            }
        });

        long id = 5;
        System.out.println("\nChallenge with ID " + id + ":");
        show(() -> System.out.println(challengeClient.getById(id)));

        System.out.println("\nUpdating challenge ...");
        Challenge updateChallenge = new Challenge("RestClientTestUpdate", "6-8", 0);
        updateChallenge.setId(20L);
        show(() -> challengeClient.update(updateChallenge));

        System.out.println("\nPrinting all challenges ...");
        show(() -> {
            Challenge[] challenges = challengeClient.getAll();
            for (Challenge challenge : challenges) {
                System.out.println(challenge.getId() + ": " + challenge.getName());
            }
        });
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service exception"+ e);
        }
    }
}
