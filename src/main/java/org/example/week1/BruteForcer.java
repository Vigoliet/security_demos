package org.example.week1;

import java.util.Scanner;

public class BruteForcer {
    public static void main(String[] args) {
        var encryptedMessage = "mffmowqdm,nmeqz,zadd,ay,xazpaz,wxaowmz,faxh";

        var scanner = new Scanner(System.in);

        for (int i = 0; i < 50; i++) {
            var decryptedMessage = SymmetricEncryption.decryptMessage(encryptedMessage, i);
            System.out.println(i + ":" + decryptedMessage);
            scanner.nextLine();
        }
    }
}
