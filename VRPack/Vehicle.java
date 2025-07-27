package VRPack;

public class Vehicle {
    private String id;
    private String name;
    private String type;
    private String numberPlate;
    private double rentalPrice;
    private int availableCount;
    private double totalKms;
    private boolean requiresService;

    public Vehicle(String id, String name, String type, String numberPlate, double rentalPrice, int availableCount){
        this.id = id;
        this.name = name;
        this.type = type;
        this.numberPlate = numberPlate;
        this.rentalPrice = rentalPrice;
        this.availableCount = availableCount;
        this.totalKms = 0;
        this.requiresService = false;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public String getNumberPlate(){
        return numberPlate;
    }

    public double getRentalPrice(){
        return rentalPrice;
    }

    public int getAvailableCount(){
        return availableCount;
    }

    public void setAvailableCount(int count){
        this.availableCount = count;
    }

    public void setRentalPrice(double price){
        this.rentalPrice = price;
    }

    public void addKms(double kms){
        totalKms += kms;
        if(type.equalsIgnoreCase("Car") && totalKms >= 3000){
            requiresService = true;
        }
        if(type.equalsIgnoreCase("Bike") && totalKms >= 1500){
            requiresService = true;
        }
    }

    public boolean isServiceDue(){
        return requiresService;
    }

    public void service(){
        totalKms = 0;
        requiresService = false;
    }

    public String getAsRow(){
        return id + " - " + name + " - " + type + " - " + numberPlate + " - ₹" + rentalPrice + " - Avl: " + availableCount;
    }
}
