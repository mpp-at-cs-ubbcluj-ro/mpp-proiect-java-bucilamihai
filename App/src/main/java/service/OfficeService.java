package service;

import domain.Challenge;
import domain.Child;
import domain.Enrollment;
import domain.OfficeResponsable;
import javafx.collections.ObservableList;
import repository.database.ChildDBRepository;
import repository.interfaces.ChallengeRepository;
import repository.interfaces.ChildRepository;
import repository.interfaces.EnrollmentRepository;
import repository.interfaces.OfficeResponsableRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class OfficeService {
    private OfficeResponsableRepository officeResponsableRepository;
    private ChallengeRepository challengeRepository;
    private ChildRepository childRepository;
    private EnrollmentRepository enrollmentRepository;

    public OfficeService(OfficeResponsableRepository officeResponsableRepository,
                         ChallengeRepository challengeRepository,
                         ChildRepository childRepository,
                         EnrollmentRepository enrollmentRepository) {
        this.officeResponsableRepository = officeResponsableRepository;
        this.challengeRepository = challengeRepository;
        this.childRepository = childRepository;
        this.enrollmentRepository = enrollmentRepository;
    }
    public Collection<Challenge> getAllChallenges() {
        return challengeRepository.getAll();
    }

    public Collection<Child> getChildrenByChallengeNameAndGroupAge(String challengeName, String groupAge) {
        return childRepository.getChildrenByChallengeNameAndGroupAge(challengeName, groupAge);
    }

    public void enrollChild(Long cnp, String name, int age, String challengeName) {
        Child child = new Child(cnp, name, age);
        childRepository.add(child);
        Child childWithId = getChildByCnp(cnp);
        // search challenge to match challengeName and age
        Challenge challenge = getChallengeMatched(age, challengeName);
        assert challenge != null;
        challenge.increaseNumberOfParticipants();
        challengeRepository.update(challenge, challenge.getId());
        // add enrollment based on child and challenge
        Enrollment enrollment = new Enrollment(childWithId, challenge);
        enrollmentRepository.add(enrollment);
    }

    public Child getChildByCnp(Long cnp) {
        for(Child child: childRepository.getAll())
            if(Objects.equals(child.getCnp(), cnp))
                return child;
        return null;
    }

    public Challenge getChallengeMatched(int age, String challengeName) {
        for(Challenge challenge: challengeRepository.getAll())
            if(Objects.equals(challenge.getName(), challengeName) && challenge.matchAge(age))
                return challenge;
        return null;
    }

    public boolean existsOfficeResponsable(String username, String password) {
        for(OfficeResponsable officeResponsable: officeResponsableRepository.getAll())
            if(Objects.equals(officeResponsable.getUsername(), username) && Objects.equals(officeResponsable.getPassword(), password))
                return true;
        return false;
    }
}
