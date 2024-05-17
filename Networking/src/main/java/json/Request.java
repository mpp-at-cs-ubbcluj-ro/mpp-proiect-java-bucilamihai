package json;


import domain.Challenge;
import dto.ChallengeDTO;
import dto.ChildDTO;
import dto.OfficeResponsableDTO;

import java.util.Collection;

public class Request {
    private RequestType type;
    private OfficeResponsableDTO officeResponsable;
    private ChallengeDTO challenge;
    private ChildDTO child;
    private String challengeName;
    private ChallengeDTO[] challenges;

    public Request(){}
    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public OfficeResponsableDTO getOfficeResponsable() {
        return officeResponsable;
    }

    public void setOfficeResponsable(OfficeResponsableDTO officeResponsable) {
        this.officeResponsable = officeResponsable;
    }

    public ChallengeDTO getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDTO challenge) {
        this.challenge = challenge;
    }


    public ChildDTO getChild() {
        return child;
    }

    public void setChild(ChildDTO child) {
        this.child = child;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }


    public ChallengeDTO[] getChallenges() {
        return challenges;
    }


//
//    public MessageDTO getMessage() {
//        return message;
//    }
//
//    public void setMessage(MessageDTO messageDTO) {
//        this.message = messageDTO;
//    }
//
//    public UserDTO[] getFriends() {
//        return friends;
//    }
//
//    public void setFriends(UserDTO[] friends) {
//        this.friends = friends;
//    }
//
//    @Override
//    public String toString() {
//        return "Request{" +
//                "type=" + type +
//                ", user=" + user +
//                ", messageDTO=" + message +
//                ", friends=" + Arrays.toString(friends) +
//                '}';
//    }
}
