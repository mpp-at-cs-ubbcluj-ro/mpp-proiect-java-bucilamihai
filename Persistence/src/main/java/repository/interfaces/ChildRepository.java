package repository.interfaces;

import domain.Child;
import repository.Repository;

import java.util.Collection;

public interface ChildRepository extends Repository<Long, Child> {

    Collection<Child> getChildrenByChallengeNameAndGroupAge(String challengeName, String groupAge);

    Child getChildByCnp(Long cnp);
}
