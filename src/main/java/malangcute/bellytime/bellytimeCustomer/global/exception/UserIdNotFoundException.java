package malangcute.bellytime.bellytimeCustomer.global.exception;

public class UserIdNotFoundException extends IllegalArgumentException{

    public UserIdNotFoundException(String message){
        super(message);
    }
}
