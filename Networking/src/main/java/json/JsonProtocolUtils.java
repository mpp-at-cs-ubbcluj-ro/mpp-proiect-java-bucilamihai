package json;

import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;
import dto.DTOUtils;

import java.util.Collection;

public class JsonProtocolUtils {
//    public static Response createNewMessageResponse(Message message){
//        Response resp=new Response();
//        resp.setType(ResponseType.NEW_MESSAGE);
//        resp.setMessage(DTOUtils.getDTO(message));
//        return resp;
//    }
//
//    public static Response createFriendLoggedInResponse(User friend){
//        Response resp=new Response();
//        resp.setType(ResponseType.FRIEND_LOGGED_IN);
//        resp.setUser(DTOUtils.getDTO(friend));
//        return resp;
//    }
//
//    public static Response createFriendLoggedOutResponse(User friend){
//        Response resp=new Response();
//        resp.setType(ResponseType.FRIEND_LOGGED_OUT);
//        resp.setUser(DTOUtils.getDTO(friend));
//        return resp;
//    }
//
    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Request createLoginRequest(OfficeResponsable officeResponsable) {
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setOfficeResponsable(DTOUtils.getDTO(officeResponsable));
        return request;
    }

    public static Request createAllChallengesRequest(OfficeResponsable loggedOfficeResponsable) {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_CHALLENGES);
        request.setOfficeResponsable(DTOUtils.getDTO(loggedOfficeResponsable));
        return request;
    }

    public static Response createAllChallengesResponse(Challenge[] challenges) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_CHALLENGES);
        response.setChallenges(DTOUtils.getDTO(challenges));
        return response;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Request createGetChildrenByChallengeNameAndGroupAgeRequest(Challenge challenge) {
        Request request = new Request();
        request.setType(RequestType.GET_CHILDREN_BY_CHALLENGE_NAME_AND_GROUP_AGE);
        request.setChallenge(DTOUtils.getDTO(challenge));
        return request;
    }

    public static Response createAllChildrenByChallengeNameAndGroupAgeResponse(Child[] children) {
        Response response = new Response();
        response.setType(ResponseType.GET_CHILDREN_BY_CHALLENGE_NAME_AND_GROUP_AGE);
        response.setChildren(DTOUtils.getDTO(children));
        return response;
    }

    public static Request createEnrollChildRequest(Child child, String challengeName) {
        Request request = new Request();
        request.setType(RequestType.ENROLL_CHILD);
        request.setChild(DTOUtils.getDTO(child));
        request.setChallengeName(challengeName);
        return request;
    }

//    public static Response createEnrollChildResponse() {
//        Response response = new Response();
//        response.setType(ResponseType.ENROLL_CHILD);
//        return response;
//    }

    public static Response createUpdateChallenges() {
        Response response = new Response();
        response.setType(ResponseType.UPDATE_CHALLENGES);
        return response;
    }
}
