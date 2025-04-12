package com.example.secureclient;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class JavaClient extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure you have a layout file
        new Thread(this::sendEncryptedMessage).start(); // Run network ops in background
    }

    private void sendEncryptedMessage() {
        try {
            //connect to the server
            Socket socket = new Socket("192.168.1.100", 8000); // Server IP
            String message = "Hello from S24 Ultra!";
            
            //enerate the same key as the server
            byte[] key = generateKey("192.168.1.100", "192.168.1.20"); // Server and client IPs
            
            // encrypt the message
            byte[] encrypted = encrypt(message.getBytes("UTF-8"), key);
            
            //send the encrypted data
            OutputStream out = socket.getOutputStream();
            out.write(encrypted);
            out.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //key generation matching server key
    private byte[] generateKey(String ip1, String ip2) throws Exception {
        String combined = ip1.compareTo(ip2) < 0 ? ip1 + ip2 : ip2 + ip1;
        long currentTime = System.currentTimeMillis();
        long timeWindow = Math.round(currentTime / 30000.0); // 30-second window
        String keyBase = combined + timeWindow;
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        return Arrays.copyOf(sha.digest(keyBase.getBytes("UTF-8")), 16); // 128-bit AES key
    }

    // AES Encryption (ECB mode)
    private byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
}