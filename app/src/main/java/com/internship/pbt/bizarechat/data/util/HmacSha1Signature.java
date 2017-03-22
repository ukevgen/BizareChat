package com.internship.pbt.bizarechat.data.util;

import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.logs.Logger;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.internship.pbt.bizarechat.data.net.ApiConstants.APP_ID;
import static com.internship.pbt.bizarechat.data.net.ApiConstants.AUTH_KEY;


public class HmacSha1Signature {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private HmacSha1Signature() {
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }


    public static String calculateSignatureWithAuth(String login, String pass, int nonce, long timestamp) {

        String data = composeParametersToString(login, pass, nonce, timestamp);

        try {
            SecretKeySpec signingKey = new SecretKeySpec(ApiConstants.AUTH_SECRET.getBytes(),
                    HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            return toHexString(mac.doFinal(data.getBytes()));
        } catch(NoSuchAlgorithmException | InvalidKeyException ex){
            Logger.logExceptionToFabric(ex);
        }
        return null;
    }

    public static String calculateSignature(int nonce, long timestamp) {
        String data = composeParametersToString(nonce, timestamp);

        try {
            SecretKeySpec signingKey = new SecretKeySpec(ApiConstants.AUTH_SECRET.getBytes(),
                    HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            return toHexString(mac.doFinal(data.getBytes()));

        } catch(NoSuchAlgorithmException | InvalidKeyException ex){
            Logger.logExceptionToFabric(ex);
        }
        return null;
    }

    /**
     * Prepare string with parameters for signature calculation
     */
    private static String composeParametersToString(int nonce, long timestamp) {
        StringBuilder sb = new StringBuilder("");
        sb.append("application_id=").append(APP_ID);
        sb.append("&auth_key=").append(AUTH_KEY);
        sb.append("&nonce=").append(nonce);
        sb.append("&timestamp=").append(timestamp);

        return sb.toString();
    }

    private static String composeParametersToString(String login, String pass, int nonce, long timestamp) {
        StringBuilder sb = new StringBuilder("");
        sb.append("application_id=").append(APP_ID);
        sb.append("&auth_key=").append(AUTH_KEY);
        sb.append("&nonce=").append(nonce);
        sb.append("&timestamp=").append(timestamp);
        sb.append("&user[email]=").append(login);
        sb.append("&user[password]=").append(pass);

        return sb.toString();
    }
}
