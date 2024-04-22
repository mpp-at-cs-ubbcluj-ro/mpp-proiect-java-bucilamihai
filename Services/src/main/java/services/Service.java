package services;

import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;

import java.util.Collection;

public interface Service {
    public void login(OfficeResponsable officeResponsable, Observer client) throws ServiceException;

    public Collection<Challenge> getAllChallenges(OfficeResponsable loggedOfficeResponsable) throws ServiceException;

    public Collection<Child> getChildrenByChallengeNameAndGroupAge(String challengeName, String groupAge) throws  ServiceException;

    public void enrollChild(Long cnp, String name, int age, String challengeName) throws ServiceException;
}
