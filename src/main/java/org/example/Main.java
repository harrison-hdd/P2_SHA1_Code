package org.example;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class Main {

    private static byte[] longToByteArray ( final long i ) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(i);
        dos.flush();
        return bos.toByteArray();
    }


    private static void collision(int num_bits){
        HashMap<String, String> hashMap = new HashMap<>(); // maps digest -> original message
        long count = 0;
        while (true){
            String msg = UUID.randomUUID().toString();
            String digest = SHA1Wrapper.hash(msg.getBytes(), num_bits);
            ++count;
            if(hashMap.containsKey(digest)){
                System.out.println(count);
                System.out.println(hashMap.get(digest) + "->" + digest);
                System.out.println(msg + "->" + digest);
                return;
            }else{
                hashMap.put(digest, msg);
            }
        }
    }

    private static void collision(){

    }

    private static void preimage(int num_bits){


    }

    private static void preimage(){

    }

    public static void main(String[] args) throws Exception {
        collision(39);

    }
}