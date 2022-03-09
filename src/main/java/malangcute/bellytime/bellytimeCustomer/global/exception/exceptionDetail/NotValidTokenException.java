package malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail;

import io.jsonwebtoken.JwtException;

public class NotValidTokenException extends JwtException {

    public NotValidTokenException(String message){
        super(message);
    }
}
