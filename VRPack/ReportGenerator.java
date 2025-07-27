package VRPack;
import java.util.*;

public class ReportGenerator {

    public static void vehiclesDueForService(RentalSystem system){
        System.out.println("----- Vehicles Due for Service -----\n");
        for(Vehicle v : system.getAllVehicles()){
            if(v.isServiceDue()){
                System.out.println(v.getAsRow());
            }
        }
    }

    public static void vehiclesSortedByRent(RentalSystem system){
        List<Vehicle> list = system.getAllVehicles();
        list.sort(Comparator.comparing(Vehicle::getRentalPrice));
        System.out.println("----- Vehicles Sorted by Rental Price -----\n");
        for(Vehicle v : list){
            System.out.println(v.getAsRow());
        }
    }

    public static void viewRentalStatus(RentalSystem system){
        System.out.println("----- Rental Status Report -----\n");
        for(Vehicle v : system.getAllVehicles()){
            if(v.getAvailableCount() < 1){
                System.out.println(v.getName() + " [" + v.getId() + "] - Currently Rented Out");
            } else {
                System.out.println(v.getName() + " [" + v.getId() + "] - Available");
            }
        }
    }
}
