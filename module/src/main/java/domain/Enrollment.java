package domain;

import java.util.Objects;

public class Enrollment extends Entity{
    private Child child;
    private Challenge challenge;

    public Enrollment(Child child, Challenge challenge) {
        this.child = child;
        this.challenge = challenge;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(child, that.child) && Objects.equals(challenge, that.challenge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), child, challenge);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "child=" + child +
                ", challenge=" + challenge +
                '}';
    }
}
