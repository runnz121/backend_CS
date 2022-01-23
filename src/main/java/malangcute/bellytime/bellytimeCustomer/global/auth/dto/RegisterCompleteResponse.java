package malangcute.bellytime.bellytimeCustomer.global.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegisterCompleteResponse {

    private boolean complete;

    private String message;

    public static RegisterCompleteResponse of(boolean complete, String message){
        return new RegisterCompleteResponse(complete, message);
    }
}
