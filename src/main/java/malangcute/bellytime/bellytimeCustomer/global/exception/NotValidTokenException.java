package malangcute.bellytime.bellytimeCustomer.global.exception;

import io.jsonwebtoken.JwtException;

public class NotValidTokenException extends JwtException {

    public NotValidTokenException(String message){
        super(message);
    }
}
