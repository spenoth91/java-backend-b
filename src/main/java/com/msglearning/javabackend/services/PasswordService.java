package com.msglearning.javabackend.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

@Service
public class PasswordService {

    private static final String SHA1PRNG = "SHA1PRNG";
    private static final String SECRET_KEY = "PBKDF2WithHmacSHA1";
    private static final String EMPTY_PASSWORD_MESSAGE = "Empty passwords are not supported.";
    private static final String SALT_AND_PASS_DELIMITER = "\\$";
    private static final String INVALID_STORED_PASSWORD_MESSAGE = "The stored password MUST have the form 'salt$hash'";

    private static final int saltLen = 32;
    private static final int iterations = 20 * 1000;
    private static final int desiredKeyLen = 256;

    private static final Logger LOG = LoggerFactory.getLogger(PasswordService.class.getName());

    public static String hashPassword(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance(SHA1PRNG).generateSeed(saltLen);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException(EMPTY_PASSWORD_MESSAGE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(SECRET_KEY);
        SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.encodeBase64String(key.getEncoded());
    }

    private static boolean check(String password, String stored) throws Exception {
        String[] saltAndPass = stored.split(SALT_AND_PASS_DELIMITER);
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(INVALID_STORED_PASSWORD_MESSAGE);
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    public static boolean checkPassword(final String password,String actualPassword){
        try {
            return check(password,actualPassword);
        }
        catch (Exception e) {
            LOG.error("Something went wrong during password check", e);
            return false;
        }
    }
}
