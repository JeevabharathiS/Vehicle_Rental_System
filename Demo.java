import java.util.*;
import VRPack.*;

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RentalSystem system = new RentalSystem();

        system.addUser(new Admin("admin1@gmail.com", "admin123", system));
        system.addUser(new Admin("admin2@gmail.com", "admin456", system));

        while (true) {
            System.out.println();
            System.out.println("===== Vehicle Rental System =====");
            System.out.println("\n1. Login");
            System.out.println("0. Exit");

            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter your choice: ");

            switch (choice) {
                case 1:
                    String email = Utils.getValidString(sc, "Enter Email: ");
                    String password = Utils.getValidString(sc, "Enter Password: ");
                    User user = system.authenticateUser(email, password);
                    if (user != null) {
                        System.out.println("\nLogin Successful as " + user.getRole());
                        user.showMenu();
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
