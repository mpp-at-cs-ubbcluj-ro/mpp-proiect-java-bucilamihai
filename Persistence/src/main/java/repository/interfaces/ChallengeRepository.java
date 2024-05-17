package repository.interfaces;

import domain.Challenge;
import repository.Repository;

public interface ChallengeRepository extends Repository<Long, Challenge> {
    Challenge getChallengeMatched(int age, String challengeName);
    Challenge findByName(String name);
}
