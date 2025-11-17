package util;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Helps to hash password and verify them using the SHA3-256 algorithm
 */
public class PasswordHasher {


    /**
     * Algorithm type
     */
    private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 *  hashes password
	 * @param password plain password
     * @return String
	 */
	public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password",e);
        }
	}

	/**
	 * check if the plain and hashed after hashing plain
	 * @param password plain password
	 * @param hashpassword hashed version
	 */
	public static boolean verify(String password, String hashpassword) {
        return hash(password).equals(hashpassword);
	}


}