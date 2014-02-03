package Generator;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Generator {

    public String getPassword(int lenght, String chars) {
        Random rand = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < lenght; index++) {
            stringBuilder.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return stringBuilder.toString();
    }

    public String getHash(String password, String type) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new HexBinaryAdapter().marshal(md != null ? md.digest(password.getBytes()) : new byte[0]);
    }
}