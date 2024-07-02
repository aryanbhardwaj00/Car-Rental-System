import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
	//USED ENCAPSULATION
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    //CREATED A CONSTRUCTOR
    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true; //AS WHEN A NEW CAR IS CREATED IT WILL BE AVIALABLE , SO INITIALLY SET TO TRUE
    }
    //USED GETTER TO RETRIEVE VALUES
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    
    //IF USER RENTS A CAR
    public void rent() {
        isAvailable = false;
    }
    
    // IF USER RETURNS A CAR
    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;  //CREATED VARIABLE OR CAR TYPE AS CLASS IS ALSO A DATA TYPE
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    //RESPECTIVE GETTERS
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
	// CREATED 3 LISTS TO STORE DETAILS OF CARS CUSTOMER AND RENTED CARS DETAILS
	// DATA TYPE FOR EACH LIST IS THEIR RESPECTIVE CLASSES FOLLoWED BY NAMES OF THE LIST
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
    	// CREATED THE ACTUAL ARRAYLIST
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car); //USED ADD FUNCTION TO ADD CAR IN ARRAYLIST
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // FUNCTION TO RENT A CAR
    
    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {								// IF VALUE OF isAvailable IS TRUE THEN CALL 
            car.rent();											// THIS WILL SET isAvailable TO FALSE, MEANING CAR IS NO LONGER AVAILABLE
            rentals.add(new Rental(car, customer, days));		// CREATED A NEW OBJECT OF RENTAL CLASS AND STORED IT IN THE RENTALS LIST

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    // FUNCTION TO RETURN A CAR
    //
    public void returnCar(Car car) {
        
        Rental rentalToRemove = null;	// CREATED A NULL VARIABLE OF RENTAL CLASS TYPE
        for (Rental rental : rentals) { // ITERATED THROUGH ALL OBJECTS (Rental) IN THE rentals LIST ASSIGNING EACH OBJECT TO VARIABLE rental
            if (rental.getCar() == car) { // getCar() function from RENTAL CLASS WILL RETURN THE CAR WHICH IS RENTED
                rentalToRemove = rental; 
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove); // CUSTOMER HAS RETURNED THE CAR , SO REMOVED IT FROM RENTAL LIST

        } else {
            System.out.println("Car was not rented.");
        }
        car.returnCar();				// SETS ISAVAIALBLE TO TRUE , MEANING IT IS AVAILABLE TO RENT
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                //SHOWED ALL AVAILABLE CARS RUNNING FOR-EACH LOOP IN LIST CAR
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);
 
                // USED THE SAME LOGIC AS IN REMOVING THE RENTED CAR FROM RENTAL LIST
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

}
public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "FORTUNER", 60.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "CITY", 70.0);
        Car car3 = new Car("C003", "Mahindra", "THAR", 120.0);
        Car car4 = new Car("C004" , "Lamborghini", "GALLARDO" , 500.0);
        Car car5 = new Car("C005", "Maruti", "ALTO", 20.0);
        Car car6 = new Car("C006" , "TATA", "SAFARI" , 100.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);
        
        rentalSystem.menu();
    }
}
