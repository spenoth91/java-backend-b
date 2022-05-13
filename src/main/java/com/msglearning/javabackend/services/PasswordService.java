package com.msglearning.javabackend.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class PasswordService {

    public static String hashPassword(String password) {
        // salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // get algo
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);

        // hashed password
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(salt) + "$" + Base64.encodeBase64String(hash);
    }
}
