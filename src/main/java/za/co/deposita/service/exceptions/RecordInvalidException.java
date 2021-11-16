package za.co.deposita.service.exceptions;

public class RecordInvalidException extends RuntimeException {
    public RecordInvalidException(String message) {
        super(message);
    }
}
