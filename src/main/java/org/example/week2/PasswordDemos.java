package org.example.week2;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

// Klartext: password
// Hash: 5f4dcc3b5aa765d61d8327deb882cf99
// Salt: 3986016725
// Pass+Salt: password:3986016725
// Hash+Salt: 9817d9f418f02fe97a4d5a3367e30c2e:3986016725
// md5(password:3986016725) -> 9817d9f418f02fe97a4d5a3367e30c2e


public class PasswordDemos {

    // 12345 % 10 -> 5
    // 25 % 10 -> 5
    // 15 % 10 -> 5

    // 12345 -> 1+2+3+4+5 -> 15
    // 15 -> 1+5 -> 6
    // 25 -> 2+5 -> 7
    public static void main(String[] args) throws SQLException {

        var scanner = new Scanner(System.in);
        var userService = new UserService();

        while (true) {
            System.out.println("""
                    -1 to register
                    -2 to login
                    -3 to exit
                    """);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    registerSwitch(scanner, userService);
                    break;
                case 2:
                    loginSwitch(scanner, userService);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 to register or 2 to login.");
                    break;
            }
        }


        //System.out.println("Hashed password and salt: " + hashedPassAndSalt);

    }

    private static void loginSwitch(Scanner scanner, UserService userService) throws SQLException {
        // Login process
        System.out.println("Enter username:");
        var loginUsername = scanner.next();

        User user = userService.getUserByUsername(loginUsername);
        if (user == null) {
            System.out.println("User not found");
            return;
        }

        System.out.println("Enter password:");
        var loginPassword = scanner.next();

        var saltFromUser = userService.getSalt(loginUsername);

        var loginPassAndSalt = loginPassword + ":" + saltFromUser;
        var hashedLoginPassAndSalt = hashMd5(loginPassAndSalt) + ":" + saltFromUser;
        userService.loginUser(loginUsername, hashedLoginPassAndSalt);
    }

    private static void registerSwitch(Scanner scanner, UserService userService) throws SQLException {
        // Registration process
        System.out.println("Enter username:");
        var username = scanner.next();

        System.out.println("Enter password:");
        var password = scanner.next();
        var salt = generateSalt();
        var passAndSalt = password + ":" + salt;
        var hashedPassAndSalt = hashMd5(passAndSalt) + ":" + salt;

        var user = new User(username, hashedPassAndSalt);
        userService.insertUser(user);
    }

    static String hashMd5(String input) {

        return DigestUtils.md5Hex(input);
    }

    private static String generateSalt() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();

        // Ensure the first digit is non-zero
        number.append(random.nextInt(9) + 1);

        // Generate the remaining 9 digits, which can include zero
        for (int i = 1; i < 10; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }

}
