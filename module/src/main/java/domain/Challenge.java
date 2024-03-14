package domain;

import java.util.Objects;

public class Challenge extends Entity{
    private String name;
    private String groupAge;
    private Integer numberOfParticipants;

    public Challenge(String name, String groupAge) {
        this.name = name;
        this.groupAge = groupAge;
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

    public void increaseNumberOfParticipants() {
        this.numberOfParticipants++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Challenge challenge)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, challenge.name) && Objects.equals(groupAge, challenge.groupAge) && Objects.equals(numberOfParticipants, challenge.numberOfParticipants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, groupAge, numberOfParticipants);
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "name='" + name + '\'' +
                ", groupAge='" + groupAge + '\'' +
                ", numberOfParticipants=" + numberOfParticipants +
                '}';
    }
}
