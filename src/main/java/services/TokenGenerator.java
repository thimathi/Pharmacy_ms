package services;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;

public class TokenGenerator {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final long TOKEN_EXPIRY_DURATION = 3600 * 1000; // 1 hour in milliseconds

    public static String generateToken(String userType, String email) {
        try {
            long expiryTime = new Date().getTime() + TOKEN_EXPIRY_DURATION; // Current time + 1 hour
            String data = userType + ":" + email + ":" + expiryTime; // Combine user data with expiry time

            String key = ConfigLoader.getProperty("SECRET_KEY");;
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes());

            String encodedData = Base64.encodeBase64URLSafeString(data.getBytes());
            String encodedHmac = Hex.encodeHexString(rawHmac);
            return encodedHmac + "." + encodedData; // Append encoded data to the HMAC
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }
}
