package malangcute.bellytime.bellytimeCustomer.global.exception;

public class UserAlreadyExistException extends IllegalArgumentException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
