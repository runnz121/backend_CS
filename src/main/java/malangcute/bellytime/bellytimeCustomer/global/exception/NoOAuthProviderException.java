package malangcute.bellytime.bellytimeCustomer.global.exception;

import javax.naming.AuthenticationException;

public class NoOAuthProviderException extends AuthenticationException {

    public NoOAuthProviderException(String message){
        super(message);
    }
}
