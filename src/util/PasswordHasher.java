package util;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.List;

/**
 * Helps to hash password and verify them using the SHA3-256 algorithm
 */
public class PasswordHasher {

    private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * 
	 * @param password
     * @return String
	 */
	public String hash(String password) {
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
	 * 
	 * @param password
	 * @param hashpassword
	 */
	public boolean verify(String password, String hashpassword) {
        return hash(password).equals(hashpassword);
	}


}