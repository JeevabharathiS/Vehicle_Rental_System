package VRPack;
import java.util.*;

public class Borrower extends User {
    private RentalSystem system;
    private double securityDeposit;
    private Map<String, Vehicle> rentedVehicles;
    private List<String> rentalHistory;

    public Borrower(String email, String password, RentalSystem system){
        super(email, password, "Borrower");
        this.system = system;
        this.securityDeposit = 30000.0;
        this.rentedVehicles = new HashMap<>();
        this.rentalHistory = new ArrayList<>();
    }
    Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu(){
        while(true){
            System.out.println();
            System.out.println("===== Borrower Menu =====");
            System.out.println("\n1. View Available Vehicles");
            System.out.println("2. Rent a Vehicle");
            System.out.println("3. Return a Vehicle");
            System.out.println("4. View My Rentals");
            System.out.println("5. Extend Rental");
            System.out.println("0. Log Out");

            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter Your Choice : ");

            switch (choice) {
                case 1:
                    System.out.println();
                    viewAvailableVehicles();
                    break;

                case 2:
                    System.out.println();
                    String rentId = Utils.getValidString(sc, "Enter Vehicle ID to Rent: ");
                    rentVehicle(rentId);
                    break;

                case 3:
                    System.out.println();
                    String returnId = Utils.getValidString(sc, "Enter Vehicle ID to Return: ");
                    returnVehicle(returnId);
                    break;

                case 4:
                    System.out.println();
                    viewMyRentals();
                    break;

                case 5:
                    System.out.println();
                    String extId = Utils.getValidString(sc, "Enter Vehicle ID to Extend: ");
                    extendRental(extId);
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

    public void viewAvailableVehicles(){
        List<Vehicle> list = system.getAllVehicles();
        for(Vehicle v : list){
            if(v.getAvailableCount() > 0 && !v.isServiceDue()){
                System.out.println(v.getAsRow());
            }
        }
    }

    public void rentVehicle(String id){
        if(rentedVehicles.containsKey(id)){
            System.out.println("You have already rented this vehicle.");
            return;
        }

        Vehicle v = system.getVehicleById(id);
        if(v == null){
            System.out.println("Vehicle not found.");
            return;
        }

        if(v.getAvailableCount() <= 0){
            System.out.println("Vehicle not available.");
            return;
        }

        if(v.isServiceDue()){
            System.out.println("Vehicle is due for service.");
            return;
        }

        if(v.getType().equalsIgnoreCase("Car") && hasVehicleType("Car")){
            System.out.println("You can rent only one car at a time.");
            return;
        }

        if(v.getType().equalsIgnoreCase("Bike") && hasVehicleType("Bike")){
            System.out.println("You can rent only one bike at a time.");
            return;
        }

        if(v.getType().equalsIgnoreCase("Car") && securityDeposit < 10000){
            System.out.println("Minimum ₹10000 deposit required to rent a car.");
            return;
        }

        if(v.getType().equalsIgnoreCase("Bike") && securityDeposit < 3000){
            System.out.println("Minimum ₹3000 deposit required to rent a bike.");
            return;
        }

        rentedVehicles.put(id, v);
        rentalHistory.add(id);
        v.setAvailableCount(v.getAvailableCount() - 1);
        System.out.println("\nVehicle Rented Successfully.");
    }

    public void returnVehicle(String id){
        if(!rentedVehicles.containsKey(id)){
            System.out.println("You haven't rented this vehicle.");
            return;
        }

        Vehicle v = rentedVehicles.get(id);
        double km = Utils.getValidDouble(sc, "Enter KM Travelled Today: ");
        v.addKms(km);

        double fine = 0.0;
        if(km > 500){
            fine += v.getRentalPrice() * 0.15;
        }

        String damage = Utils.getValidString(sc, "Enter Damage Level (LOW/MEDIUM/HIGH/NONE): ").toUpperCase();
        if(damage.equals("LOW")){
            fine += v.getRentalPrice() * 0.20;
        } else if(damage.equals("MEDIUM")){
            fine += v.getRentalPrice() * 0.50;
        } else if(damage.equals("HIGH")){
            fine += v.getRentalPrice() * 0.75;
        }

        if(fine > 0){
            System.out.println("Fine amount: ₹" + fine);
            securityDeposit -= fine;
        }

        v.setAvailableCount(v.getAvailableCount() + 1);
        rentedVehicles.remove(id);
        System.out.println("\nVehicle Returned.");
    }

    public void extendRental(String id){
        if(!rentedVehicles.containsKey(id)){
            System.out.println("You haven't rented this vehicle.");
            return;
        }

        System.out.println("\nRental Extended by One More Day.");
    }

    public void viewMyRentals(){
        if(rentedVehicles.isEmpty()){
            System.out.println("You haven't rented any vehicles.");
            return;
        }

        for(Vehicle v : rentedVehicles.values()){
            System.out.println(v.getAsRow());
        }
    }

    public boolean hasVehicleType(String type){
        for(Vehicle v : rentedVehicles.values()){
            if(v.getType().equalsIgnoreCase(type)){
                return true;
            }
        }
        return false;
    }

    public void setSecurityDeposit(double amt){
        this.securityDeposit = amt;
    }

    public double getSecurityDeposit(){
        return securityDeposit;
    }

    public List<String> getRentalHistory(){
        return rentalHistory;
    }
}
