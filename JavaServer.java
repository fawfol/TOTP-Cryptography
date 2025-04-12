import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class JavaServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server listening on port 8000...");
        
        Socket client = serverSocket.accept();
        System.out.println("Client connected");
        
        InputStream in = client.getInputStream();
        byte[] encryptedData = in.readAllBytes();
        
        String ip1 = "192.168.1.100"; // Server IP
        String ip2 = "192.168.1.20";  // Client IP
        byte[] key = generateKey(ip1, ip2);
        
        try {
            byte[] decrypted = decrypt(encryptedData, key);
            System.out.println("Decrypted Message: " + new String(decrypted));
        } catch (Exception e) {
            System.out.println("Decryption failed. Possible clock mismatch or invalid key.");
            e.printStackTrace();
        } finally {
            client.close();
            serverSocket.close();
        }
    }
    
    public static byte[] generateKey(String ip1, String ip2) throws Exception {
        String combined = ip1.compareTo(ip2) < 0 ? ip1 + ip2 : ip2 + ip1;
        long currentTime = System.currentTimeMillis();
        long timeWindow = Math.round(currentTime / 30000.0); // Rounded to nearest 30s
        String keyBase = combined + timeWindow;
        
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        return Arrays.copyOf(sha.digest(keyBase.getBytes("UTF-8")), 16);
    }
    
    public static byte[] decrypt(byte[] encrypted, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encrypted);
    }
}