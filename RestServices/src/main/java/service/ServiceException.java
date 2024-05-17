package service;

public class ServiceException extends RuntimeException {
    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }
}

