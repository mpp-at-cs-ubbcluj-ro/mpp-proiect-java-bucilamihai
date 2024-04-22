package server;

import domain.Challenge;
import domain.Child;
import domain.Enrollment;
import domain.OfficeResponsable;
import repository.interfaces.ChallengeRepository;
import repository.interfaces.ChildRepository;
import repository.interfaces.EnrollmentRepository;
import repository.interfaces.OfficeResponsableRepository;
import services.Observer;
import services.Service;
import services.ServiceException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OfficeService implements Service{
    private OfficeResponsableRepository officeResponsableRepository;
    private ChallengeRepository challengeRepository;
    private ChildRepository childRepository;
    private EnrollmentRepository enrollmentRepository;
    private Map<String, Observer> loggedResponsables;

    public OfficeService(OfficeResponsableRepository officeResponsableRepository,
                         ChallengeRepository challengeRepository,
                         ChildRepository childRepository,
                         EnrollmentRepository enrollmentRepository) {
        this.officeResponsableRepository = officeResponsableRepository;
        this.challengeRepository = challengeRepository;
        this.childRepository = childRepository;
        this.enrollmentRepository = enrollmentRepository;
        loggedResponsables = new ConcurrentHashMap<>();
    }

    public synchronized void login(OfficeResponsable officeResponsable, Observer client) throws ServiceException {
        OfficeResponsable officeResponsableFound = officeResponsableRepository.findByUsername(officeResponsable.getUsername());
        if(officeResponsableFound != null) {
            System.out.println(officeResponsableFound.getId());
            if(loggedResponsables.get(officeResponsableFound.getId().toString()) != null)
                throw new ServiceException("User is already logged in.");
            loggedResponsables.put(officeResponsableFound.getId().toString(), client);
        }
        else
            throw new ServiceException("Failed log in.");
    }

    public synchronized Collection<Challenge> getAllChallenges(OfficeResponsable loggedOfficeResponsable) throws ServiceException {
        return challengeRepository.getAll();
    }

    public synchronized Collection<Child> getChildrenByChallengeNameAndGroupAge(String challengeName, String groupAge) {
        return childRepository.getChildrenByChallengeNameAndGroupAge(challengeName, groupAge);
    }

    public synchronized void enrollChild(Long cnp, String name, int age, String challengeName) {
        Child child = new Child(cnp, name, age);
        childRepository.add(child);
        Child childWithId = childRepository.getChildByCnp(cnp);
        // search challenge to match challengeName and age
        Challenge challenge = challengeRepository.getChallengeMatched(age, challengeName);
        challenge.increaseNumberOfParticipants();
        challengeRepository.update(challenge, challenge.getId());
        // add enrollment based on child and challenge
        Enrollment enrollment = new Enrollment(childWithId, challenge);
        enrollmentRepository.add(enrollment);
    }
}
