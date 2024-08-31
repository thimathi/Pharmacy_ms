package services;

import io.javalin.http.Context;
import java.util.Date;

public class TokenValidator {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SECRET_KEY = ConfigLoader.getProperty("SECRET_KEY");

    public static boolean validateToken(Context ctx) {
        try{
            String authorization = ctx.header(AUTHORIZATION_HEADER);
            if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
                return false;
            }

            String token = authorization.substring(BEARER_PREFIX.length());
            String userData = TokenDecryptor.decryptToken(token, SECRET_KEY);

            String[] parts = userData.split(":");
            long expiryTime = Long.parseLong(parts[parts.length - 1]); // Assuming the expiry time is the last part
            if (new Date().getTime() > expiryTime) {
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
