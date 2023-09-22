package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class SHA1Wrapper {
    private static MessageDigest d;

    static {
        try {
            d = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String byte_arr_to_binary_str(byte[] arr){
        StringBuilder sb = new StringBuilder();
        for (byte elem: arr){
            sb.append(String.format("%8s", Integer.toBinaryString(elem & 0xff)).replace(' ', '0'));
        }
        return sb.toString();
    }

    public static String hash(byte[] in, int num_bits){ //get the first num_bits bits from the hash binary string
        byte[] out = d.digest(in);
        return byte_arr_to_binary_str(out).substring(0, num_bits);
    }
}
