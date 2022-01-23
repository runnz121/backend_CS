package malangcute.bellytime.bellytimeCustomer.global.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sec")
public class SecurityProperties {

    private final Auth auth = new Auth();
    private final Cors cors = new Cors();
    private final Reuris reuris = new Reuris();

    public static class Auth {
        private String secretkey;
        private long validtimeforAccess;
        private long validtimeforRefresh;

        public String getSecretkey(){
            return secretkey;
        }

        public void setSecretkey(String secretkey) {
            this.secretkey = secretkey;
        }

        public Long getValidtimeforAccess(){
            return validtimeforAccess;
        }

        public void setValidtimeforAccess(long validtimeforAccess) {
            this.validtimeforAccess = validtimeforAccess;
        }

        public Long getValidtimeforRefresh(){
            return validtimeforRefresh;
        }

        public void setValidtimeforRefresh(long validtimeforRefresh) {
            this.validtimeforRefresh = validtimeforRefresh;
        }
    }

    public static class Cors {
        private String frontEndDomain;

        public String getFrontEndDomain(){
            return frontEndDomain;
        }

        public void setFrontEndDomain(String frontEndDomain){
            this.frontEndDomain = frontEndDomain;
        }
    }

    public static class Reuris {
        private String google;

        public String getGoogle(){
            return google;
        }

        public void setGoogle(String google){
            this.google = google;
        }
    }

    public Auth getAuth(){
        return auth;
    }

    public Cors getCors(){
        return cors;
    }

    public Reuris getReuris(){
        return reuris;
    }
}
