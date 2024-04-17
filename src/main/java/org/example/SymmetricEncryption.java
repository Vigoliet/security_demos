package org.example;

public class SymmetricEncryption {

    // abcdefghijklmnopqrstuvwxyz
    // Secret: linus
    // Key: 2
    // Cipher: nkpwu

    // Secret: Zelda
    // Key: 2
    // Cipher: bgnfc

    // Secret: yxa
    // Key: 2
    // Cipher: azc

    public static void main(String[] args) {

        var secret = "attackera basen norr om london klockan tolv";
        var key = 12;

        var encryptedMessage = encryptMessage(secret, key);
        System.out.println(encryptedMessage);
        System.out.println(decryptMessage(encryptedMessage, key));

    }

    public static String encryptMessage(String secret, int key){
        StringBuilder encryptedMessage = new StringBuilder();
        secret = secret.toLowerCase();

        for(int i = 0; i < secret.length(); i ++) {
            var asciiDecimal = (secret.charAt(i) + key);

            if (asciiDecimal>122){
                asciiDecimal -= 26;
            }
            var encryptedChar = (char) asciiDecimal;

            encryptedMessage.append(encryptedChar);
        }
        return encryptedMessage.toString();

    }

    public static String decryptMessage(String encrypted, int key){
        StringBuilder decryptedMessage = new StringBuilder();

        encrypted = encrypted.toLowerCase();

        for(int i = 0; i < encrypted.length(); i ++) {
            var asciiDecimal = (encrypted.charAt(i) - key);

            if (asciiDecimal<97){
                asciiDecimal += 26;
            }
            var decryptedChar = (char) asciiDecimal;
            decryptedMessage.append(decryptedChar);
        }
        return decryptedMessage.toString();

    }

}
