package model.Authneticate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String hashpassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); //Determine to use SHA-256 to hashed code
            byte[] hashByte = messageDigest.digest(password.getBytes()); //Convert password to the binery code

            StringBuilder hexString = new StringBuilder();

            for(byte b : hashByte) {
                hexString.append(String.format("%02x",b)); // convert binery code to the hexacode
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }
}
