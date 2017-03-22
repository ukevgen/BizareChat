package com.internship.pbt.bizarechat.util;


import com.internship.pbt.bizarechat.data.util.HmacSha1Signature;

import org.junit.Test;
import static org.junit.Assert.*;

public class HmacSha1SignatureUnitTest {
    private int nonce = 156;
    private long timestamp = 1484830920;
    private String originSignature = "09aeba829e96cd21850e1942a889a518fba0ee7d";

    @Test
    public void checkSignatureCalculation(){
        String signature = HmacSha1Signature.calculateSignature(nonce, timestamp);
        assertEquals(originSignature, signature);
    }
}
