package org.example.week2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class PasswordDemosV2 {

        // 12345 % 10 => 5
        // 25 % 10 => 5
        // 15 % 10 => 5

        // 12345 => 1+2+3+4+5 => 15
        // 15 => 1+5 => 6
        // 25 => 2+5 => 7

        private static Scanner scanner;
        public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
            scanner = new Scanner(System.in);

            // Login or Register? (?) l r

            System.out.println("(l)ogin / (r)egister?");
            var selection = scanner.nextLine();

            if (selection.equalsIgnoreCase("l")){
                login();
            }
            else if(selection.equalsIgnoreCase("r")){
                register();
            }
        }

        private static void login() throws SQLException, NoSuchAlgorithmException {
            // Get username
            System.out.print("Enter username: ");
            var username = scanner.nextLine();

            // Get password
            System.out.print("Enter pass: ");
            var password = scanner.nextLine();

            var userService = new UserService();
            var user = userService.getUserByUsername(username);

            var passAndSalt = password + ":" + user.getSalt();
            var hash = hashMd5(passAndSalt);

            var passwordIsCorrect = hash.equalsIgnoreCase(user.getHash());
            System.out.println("Enter pass: " + password);
            System.out.println("Registered salt: " + user.getSalt());
            System.out.println("Pass and salt: " + passAndSalt);
            System.out.println("Entered hash: " + hash);
            System.out.println("Registered hash: " + user.getHash());
            System.out.println("Password is correct: " + passwordIsCorrect);

        }

        private static void register() throws NoSuchAlgorithmException, SQLException {
            // Get username
            System.out.print("Enter username: ");
            var username = scanner.nextLine();

            // Get password
            System.out.print("Enter pass: ");
            var password = scanner.nextLine();
            var salt = generateSalt();
            var passAndSalt = password + ":" + salt;
            var hashedPassAndSalt = hashMd5(passAndSalt) + ":" + salt;

            var user = new User(username, hashedPassAndSalt);
            var userService = new UserService();
            userService.insertUser(user);
        }

        private static String hashMd5(String input) throws NoSuchAlgorithmException {
            var md = MessageDigest.getInstance("MD5");
            var passwordBytes = input.getBytes();
            md.update(passwordBytes); // 5EB63BBBE01EEED093CB22BB8F5ACDC3
            var md5Bytes = md.digest();

            var sb = new StringBuilder();
            for(var b : md5Bytes){
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }

        private static String generateSalt(){
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

