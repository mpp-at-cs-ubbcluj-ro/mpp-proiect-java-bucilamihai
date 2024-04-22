package json;

import domain.Challenge;
import domain.Child;
import dto.ChallengeDTO;
import dto.ChildDTO;
import dto.OfficeResponsableDTO;

import java.io.Serializable;
import java.util.Arrays;

public class Response implements Serializable {
    private ResponseType type;
    private String errorMessage;
    private ChallengeDTO[] challenges;
    private ChildDTO[] children;


    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ChallengeDTO[] getChallenges() {
        return challenges;
    }
    public void setChallenges(ChallengeDTO[] challenges) {
        this.challenges = challenges;
    }

    public ChildDTO[] getChildren() {
        return children;
    }

    public void setChildren(ChildDTO[] children) {
        this.children = children;
    }
}
