package dto;


import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;

import java.util.Collection;

public class DTOUtils {
    public static OfficeResponsable getFromDTO(OfficeResponsableDTO officeResponsableDTO){
        String username = officeResponsableDTO.getUsername();
        String password = officeResponsableDTO.getPassword();
        return new OfficeResponsable(username, password);

    }
    public static OfficeResponsableDTO getDTO(OfficeResponsable user){
        String username = user.getUsername();
        String password = user.getPassword();
        return new OfficeResponsableDTO(username, password);
    }

    public static Challenge getFromDTO(ChallengeDTO challengeDTO) {
        String name = challengeDTO.getName();
        String groupAge = challengeDTO.getGroupAge();
        Integer numberOfParticipants = challengeDTO.getNumberOfParticipants();
        return new Challenge(name, groupAge, numberOfParticipants);
    }

    public static ChallengeDTO getDTO(Challenge challenge) {
        String name = challenge.getName();
        String groupAge = challenge.getGroupAge();
        Integer numberOfParticipants = challenge.getNumberOfParticipants();
        return new ChallengeDTO(name, groupAge, numberOfParticipants);
    }

    public static Challenge[] getFromDTO(ChallengeDTO[] challengesDTO) {
        Challenge[] challenges = new Challenge[challengesDTO.length];
        for(int i = 0; i < challengesDTO.length; i++) {
            challenges[i] = getFromDTO(challengesDTO[i]);
        }
        return challenges;
    }

    public static ChallengeDTO[] getDTO(Challenge[] challenges) {
        ChallengeDTO[] challengesDTO = new ChallengeDTO[challenges.length];
        for(int i = 0; i < challenges.length; i++) {
            challengesDTO[i] = getDTO(challenges[i]);
        }
        return challengesDTO;
    }

    public static Child[] getFromDTO(ChildDTO[] childrenDTO) {
        Child[] children = new Child[childrenDTO.length];
        for(int i = 0; i < childrenDTO.length; i++) {
            children[i] = getFromDTO(childrenDTO[i]);
        }
        return children;
    }

    public static ChildDTO[] getDTO(Child[] children) {
        ChildDTO[] childrenDTO = new ChildDTO[children.length];
        for(int i = 0; i < children.length; i++) {
            childrenDTO[i] = getDTO(children[i]);
        }
        return childrenDTO;
    }

    public static ChildDTO getDTO(Child child) {
        Long cnp = child.getCnp();
        String name = child.getName();
        Integer age = child.getAge();
        return new ChildDTO(cnp, name, age);
    }

    public static Child getFromDTO(ChildDTO childDTO) {
        Long cnp = childDTO.getCnp();
        String name = childDTO.getName();
        Integer age = childDTO.getAge();
        return new Child(cnp, name, age);
    }
}
