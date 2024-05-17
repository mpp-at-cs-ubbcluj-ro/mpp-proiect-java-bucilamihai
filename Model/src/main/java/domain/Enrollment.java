package domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "ENROLLMENTS")
public class Enrollment extends Entity<Long>{
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public Enrollment(Child child, Challenge challenge) {
        this.child = child;
        this.challenge = challenge;
    }

    public Enrollment() {

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
