package org.example.week1;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class AsymmetricEncryption {
    public static void main(String[] args) throws Exception {

        var secret = "Attackera ingen, vi är fredliga varelser";

        var pair = getKeyPair();
        var publicKey = pair.getPublic();
        var privateKey = pair.getPrivate();

        var encryptedText = encryptText(publicKey, secret);

        decryptText(privateKey, encryptedText);

        //encryptImage(publicKey, "lille-bilden.png", "encrypted-image.png");
        //decryptImage(privateKey, "encrypted-image.png", "decrypted-image.png");


    }

    private static String encryptText(PublicKey publicKey, String secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        var cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        var encryptedBytes = cipher.doFinal(secret.getBytes());
        var encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Krypterad text: " + encryptedText);
        return encryptedText;
    }

    private static void decryptText(PrivateKey privateKey, String encryptedText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        var cipherDecrypt = Cipher.getInstance("RSA");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipherDecrypt.doFinal(Base64.getDecoder().decode(encryptedText)); // här ska den läggas in
        String decryptedText = new String(decryptedBytes);
        System.out.println("Dekrypterad text: " + decryptedText);
    }



    private static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        var keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    private static void encryptImage(PublicKey publicKey, String inputImagePath, String outputFilePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        BufferedImage bImage = ImageIO.read(new File("lille-bilden.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos);
        byte [] imageBytes = bos.toByteArray();


        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte [] encryptedBytes = cipher.doFinal(imageBytes);

        Files.write(Paths.get(outputFilePath), encryptedBytes);
    }

    private static void decryptImage(PrivateKey privateKey, String inputImagePath, String outputFilePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte [] encryptedBytes = Files.readAllBytes(Paths.get(inputImagePath));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte [] decryptedBytes = cipher.doFinal(encryptedBytes);

        Files.write(Paths.get(outputFilePath), decryptedBytes);
    }
}
