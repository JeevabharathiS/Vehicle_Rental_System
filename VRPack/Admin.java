package VRPack;
import java.util.*;

public class Admin extends User {
    private RentalSystem system;

    public Admin(String email, String password, RentalSystem system){
        super(email, password, "Admin");
        this.system = system;
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu(){
        while(true){
            System.out.println();
            System.out.println("===== Admin Menu =====");
            System.out.println("\n1. Add Vehicle");
            System.out.println("2. Modify Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. View Vehicles by Name");
            System.out.println("5. View Vehicles by Availability");
            System.out.println("6. Search Vehicle by Name or Number");
            System.out.println("7. Change Security Deposit");
            System.out.println("8. Add Borrower");
            System.out.println("9. Add Admin");
            System.out.println("10. Vehicles Due for Service");
            System.out.println("11. Vehicles Sorted by Rental Price");
            System.out.println("12. View Rental Status");
            System.out.println("0. Log Out");

            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter Your Choice : ");

            switch (choice) {
                case 1:
                    System.out.println();
                    String id = Utils.getValidString(sc, "Enter Vehicle ID: ");
                    String name = Utils.getValidString(sc, "Enter Vehicle Name: ");
                    String type = Utils.getValidString(sc, "Enter Type (Car/Bike): ");
                    String number = Utils.getValidString(sc, "Enter Number Plate: ");
                    double rent = Utils.getValidDouble(sc, "Enter Rental Price: ");
                    int count = Utils.getValidInteger(sc, "Enter Available Count: ");
                    addVehicle(new Vehicle(id, name, type, number, rent, count));
                    break;

                case 2:
                    System.out.println();
                    String mid = Utils.getValidString(sc, "Enter Vehicle ID: ");
                    int newQty = Utils.getValidInteger(sc, "Enter New Quantity: ");
                    double newRent = Utils.getValidDouble(sc, "Enter New Rent: ");
                    modifyVehicle(mid, newQty, newRent);
                    break;

                case 3:
                    System.out.println();
                    String did = Utils.getValidString(sc, "Enter Vehicle ID to Delete: ");
                    deleteVehicle(did);
                    break;

                case 4:
                    System.out.println();
                    viewVehiclesByName();
                    break;

                case 5:
                    System.out.println();
                    viewVehiclesByAvailability();
                    break;

                case 6:
                    System.out.println();
                    String keyword = Utils.getValidString(sc, "Enter Name or Number Plate: ");
                    searchVehicle(keyword);
                    break;

                case 7:
                    System.out.println();
                    String email = Utils.getValidString(sc, "Enter Borrower Email: ");
                    double amount = Utils.getValidDouble(sc, "Enter New Security Deposit: ");
                    changeSecurity(email, amount);
                    break;

                case 8:
                    System.out.println();
                    String bmail = Utils.getValidString(sc, "Enter Borrower Email: ");
                    String bpass = Utils.getValidString(sc, "Enter Password: ");
                    addBorrower(new Borrower(bmail, bpass, system));
                    break;

                case 9:
                    System.out.println();
                    String amail = Utils.getValidString(sc, "Enter Admin Email: ");
                    String apass = Utils.getValidString(sc, "Enter Password: ");
                    addAdmin(new Admin(amail, apass, system));
                    break;

                case 10:
                    System.out.println();
                    ReportGenerator.vehiclesDueForService(system);
                    break;

                case 11:
                    System.out.println();
                    ReportGenerator.vehiclesSortedByRent(system);
                    break;

                case 12:
                    System.out.println();
                    ReportGenerator.viewRentalStatus(system);
                    break;

                case 0:
                    System.out.println();
                    System.out.println("Logging Out...");
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    public void addVehicle(Vehicle v){
        system.addVehicle(v);
        System.out.println("Vehicle Added.");
    }

    public void modifyVehicle(String id, int qty, double rent){
        Vehicle v = system.getVehicleById(id);
        if(v != null){
            v.setAvailableCount(qty);
            v.setRentalPrice(rent);
            System.out.println("Vehicle Modified.");
        } else {
            System.out.println("Vehicle Not Found.");
        }
    }

    public void deleteVehicle(String id){
        if(system.getVehicleById(id) != null){
            system.removeVehicle(id);
            System.out.println("Vehicle Deleted.");
        } else {
            System.out.println("Vehicle Not Found.");
        }
    }

    public void viewVehiclesByName(){
        List<Vehicle> list = system.getAllVehicles();
        list.sort(Comparator.comparing(Vehicle::getName));
        for(Vehicle v : list){
            System.out.println(v.getAsRow());
        }
    }

    public void viewVehiclesByAvailability(){
        List<Vehicle> list = system.getAllVehicles();
        list.sort((a, b) -> b.getAvailableCount() - a.getAvailableCount());
        for(Vehicle v : list){
            System.out.println(v.getAsRow());
        }
    }

    public void searchVehicle(String keyword){
        List<Vehicle> list = system.getAllVehicles();
        for(Vehicle v : list){
            if(v.getName().toLowerCase().contains(keyword.toLowerCase()) || v.getNumberPlate().equalsIgnoreCase(keyword)){
                System.out.println(v.getAsRow());
            }
        }
    }

    public void changeSecurity(String email, double amount){
        for(User u : system.getAllUsers()){
            if(u instanceof Borrower && u.getEmail().equals(email)){
                ((Borrower) u).setSecurityDeposit(amount);
                System.out.println("Security Deposit Updated.");
                return;
            }
        }
        System.out.println("Borrower Not Found.");
    }

    public void addBorrower(Borrower b){
        if(system.emailExists(b.getEmail())){
            System.out.println("Email Already Exists.");
        } else {
            system.addUser(b);
            System.out.println("Borrower Added.");
        }
    }

    public void addAdmin(Admin a){
        if(system.emailExists(a.getEmail())){
            System.out.println("Email Already Exists.");
        } else {
            system.addUser(a);
            System.out.println("Admin Added.");
        }
    }
}
