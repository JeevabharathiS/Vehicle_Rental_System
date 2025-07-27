package VRPack;
import java.util.*;

public class RentalSystem {
    private Map<String, User> users;
    private Map<String, Vehicle> vehicles;
    private int nextRentalID = 1001;

    public RentalSystem(){
        users = new HashMap<>();
        vehicles = new HashMap<>();
    }

    public void addUser(User user){
        users.put(user.getEmail(), user);
    }

    public boolean emailExists(String email){
        return users.containsKey(email);
    }

    public User authenticateUser(String email, String password){
        User user = users.get(email);
        if(user != null && user.authenticate(email, password)){
            return user;
        }
        return null;
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.put(vehicle.getId(), vehicle);
    }

    public void removeVehicle(String id){
        vehicles.remove(id);
    }

    public Vehicle getVehicleById(String id){
        return vehicles.get(id);
    }

    public List<Vehicle> getAllVehicles(){
        return new ArrayList<>(vehicles.values());
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(users.values());
    }

    public List<Borrower> getAllBorrowers(){
        List<Borrower> list = new ArrayList<>();
        for(User u : users.values()){
            if(u instanceof Borrower){
                list.add((Borrower) u);
            }
        }
        return list;
    }

    public int getNextRentalID(){
        return nextRentalID++;
    }
}
