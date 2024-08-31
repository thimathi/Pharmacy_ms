package services;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class TokenDecryptor {
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String decryptToken(String token, String key) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid token format");

        String receivedHmac = parts[0];
        String encodedData = parts[1];

        byte[] dataBytes = Base64.decodeBase64(encodedData);
        String data = new String(dataBytes);

        // Re-compute HMAC on the received data to ensure integrity
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(dataBytes);
        String computedHmac = Hex.encodeHexString(rawHmac);

        if (!computedHmac.equals(receivedHmac)) {
            throw new RuntimeException("Invalid HMAC: Token data may have been tampered with");
        }

        return data; // Return the decoded data, which includes user type, email, and expiry time
    }
}
