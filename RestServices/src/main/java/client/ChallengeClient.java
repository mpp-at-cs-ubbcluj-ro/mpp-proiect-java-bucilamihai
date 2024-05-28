package client;

import domain.Challenge;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import service.ServiceException;

import java.io.IOException;
import java.util.concurrent.Callable;

public class ChallengeClient {
    public static final String URL = "http://localhost:8080/contest/challenge";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Challenge[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Challenge[].class));
    }

    public Challenge getById(Long id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Challenge.class));
    }

    public Challenge create(Challenge challenge) {
        return execute(() -> restTemplate.postForObject(URL, challenge, Challenge.class));
    }

    public void update(Challenge challenge) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, challenge.getId()), challenge);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

    public static class CustomRestClientInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Sending a " + request.getMethod() + " request to " + request.getURI() + " and body [" + new String(body) + "]");
            ClientHttpResponse response = null;
            try {
                response = execution.execute(request, body);
                System.out.println("Got response code " + response.getStatusCode());
            } catch (IOException ex) {
                System.err.println("Eroare executie " + ex);
            }
            return response;
        }
    }
}
