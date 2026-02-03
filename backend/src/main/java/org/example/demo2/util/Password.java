package org.example.demo2.util;
import org.mindrot.jbcrypt.BCrypt;

public class Password {
    private static final Integer SALT_LENGTH = 12;

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(SALT_LENGTH);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
