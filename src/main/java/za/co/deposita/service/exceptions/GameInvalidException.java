package za.co.deposita.service.exceptions;

public class GameInvalidException extends RuntimeException {
    public GameInvalidException(String message) {
        super(message);
    }
}
