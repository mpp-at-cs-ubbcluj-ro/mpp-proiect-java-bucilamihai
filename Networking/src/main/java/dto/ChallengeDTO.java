package dto;

import domain.Challenge;

import java.util.List;
import java.util.Objects;

public class ChallengeDTO {
    private String name;
    private String groupAge;
    private Integer numberOfParticipants;

    public ChallengeDTO(String name, String groupAge, Integer numberOfParticipants) {
        this.name = name;
        this.groupAge = groupAge;
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupAge() {
        return groupAge;
    }

    public void setGroupAge(String groupAge) {
        this.groupAge = groupAge;
    }


    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void increaseNumberOfParticipants() {
        this.numberOfParticipants++;
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
                "name='" + name + '\'' +
                ", groupAge='" + groupAge + '\'' +
                ", numberOfParticipants=" + numberOfParticipants +
                '}';
    }

    public boolean matchAge(int age) {
        List<String> parts = List.of(groupAge.split("-"));
        int lowerBound = Integer.parseInt(parts.get(0));
        int upperBound = Integer.parseInt(parts.get(1));
        return lowerBound <= age && age <= upperBound;
    }
}
