package com.internship.pbt.bizarechat.util;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.ApiConstants;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.internship.pbt.bizarechat.data.net.ApiConstants.APP_ID;
import static com.internship.pbt.bizarechat.data.net.ApiConstants.AUTH_KEY;


public class HmacSha1Signature {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static Random randomizer = new Random(27);

    private HmacSha1Signature(){}

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public static String calculateSignature()
    {
        String data = composeParametersToString();

        try {
            SecretKeySpec signingKey = new SecretKeySpec(ApiConstants.AUTH_SECRET.getBytes(),
                                                            HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            return toHexString(mac.doFinal(data.getBytes()));
        } catch(NoSuchAlgorithmException | InvalidKeyException ex){
            Log.e(HmacSha1Signature.class.getSimpleName(), ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Prepare string with parameters for signature calculation
     */
    private static String composeParametersToString(){
        int nonce = randomizer.nextInt();
        long timestamp = new Date().getTime() / 1000;

        StringBuilder sb = new StringBuilder("");
        sb.append("application_id=").append(APP_ID);
        sb.append("&auth_key=").append(AUTH_KEY);
        sb.append("&nonce=").append(nonce);
        sb.append("&timestamp=").append(timestamp);

        return sb.toString();
    }
}
