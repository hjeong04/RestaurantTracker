package ui;

import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// referenced https://github.students.cs.ubc.ca/CPSC210/TellerApp


// a restaurant list application
public class RestaurantApp {
    private static final String JSON_STORE = "./data/restaurantList.json";
    private RestaurantList rl1;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: runs the teller application
    public RestaurantApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRestaurantList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRestaurantList() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toUpperCase();

            if (command.equals("H")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("A")) {
            viewRestaurant();
        } else if (command.equals("B")) {
            addRestaurant();
        } else if (command.equals("C")) {
            removeRestaurant();
        } else if (command.equals("D")) {
            searchRestaurant();
        } else if (command.equals("E")) {
            visitedRestaurant();
        } else if (command.equals("F")) {
            saveRestaurantList();
        } else if (command.equals("G")) {
            loadRestaurantList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the restaurant list
    private void init() {
        rl1 = new RestaurantList("Hannah's list");
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("A: View My Restaurant List");
        System.out.println("B: Add a Restaurant");
        System.out.println("C: Remove a Restaurant");
        System.out.println("D: Search Restaurants");
        System.out.println("E: Visited a Restaurant");
        System.out.println("F: Save Restaurant List to File");
        System.out.println("G: Load Restaurant List from File");
        System.out.println("H: Quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts viewing of restaurant
    private void viewRestaurant() {
        if (rl1.getSize() == 0) {
            System.out.println("No restaurant has been added to your list yet.");
        }
        for (Restaurant r : rl1.viewRestaurantList()) {
            printInfo(r);
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts adding of restaurant
    private void addRestaurant() {
        System.out.println("Type in the following information. \nName of the restaurant: ");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Type of food served at the restaurant: ");
        String type = input.nextLine();
        System.out.println("Location of the restaurant: ");
        String location = input.nextLine();

        Restaurant r = new Restaurant(name, type, location);
        rl1.addRestaurant(r);

        System.out.println("The restaurant has been successfully added to the list.");
    }

    // MODIFIES: this
    // EFFECTS: conducts removal of restaurant
    private void removeRestaurant() {
        System.out.println("Type in the name of the restaurant you would like to remove.");
        input.nextLine();
        String name = input.nextLine();
        List<Restaurant> listWithName = rl1.searchByName(name);

        if (listWithName.isEmpty()) {
            System.out.println("The name was not found in the list.");
        } else {
            for (Restaurant r : listWithName) {
                rl1.removeRestaurant(r);
            }
            System.out.println("The restaurant/ restaurants have been successfully removed from the list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts search for restaurants
    private void searchRestaurant() {
        System.out.println("How would you like to search? \nBy: name, type or location.");
        System.out.println("N for name");
        System.out.println("T for type");
        System.out.println("L for location");
        input.nextLine();
        String selection = input.nextLine();
        selection = selection.toUpperCase();

        if (selection.equals("N")) {
            searchByName();
        }

        if (selection.equals("T")) {
            searchByType();
        }

        if (selection.equals("L")) {
            searchByLocation();
        }

    }

    // MODIFIES: this
    // EFFECTS: conducts procedure for having visited a restaurant
    private void visitedRestaurant() {
        System.out.println("Type in the name of the restaurant you have visited.");
        input.nextLine();
        String name = input.nextLine();
        List<Restaurant> listByName = rl1.searchByName(name);

        if (listByName.isEmpty()) {
            System.out.println("The name of the restaurant was not found in the list.");
        }
        for (Restaurant r : listByName) {
            printInfo(r);
            System.out.println("Is this the restaurant you are looking for? Type t (true) or f (false).");
            String b = input.next();
            b = b.toLowerCase();

            if (b.equals("t")) {
                r.visited();
                System.out.println("Please provide a rating for this restaurant out of 10.");
                String rating = input.next();
                int i = Integer.parseInt(rating);
                r.setRating(i);
                break;
            }
        }
    }

    // referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the restaurant list to file
    private void saveRestaurantList() {
        try {
            jsonWriter.open();
            jsonWriter.write(rl1);
            jsonWriter.close();
            System.out.println("Saved " + rl1.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads restaurant list from file
    private void loadRestaurantList() {
        try {
            rl1 = jsonReader.read();
            System.out.println("Loaded " + rl1.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: searches the restaurant by name and prints it out
    private void searchByName() {
        System.out.println("Type in the name of the restaurant: ");
        String name = input.nextLine();
        List<Restaurant> listByName = rl1.searchByName(name);

        if (listByName.isEmpty()) {
            System.out.println("The name of the restaurant was not found in the list.");
        } else {
            for (Restaurant r : listByName) {
                printInfo(r);
            }
        }
    }

    // EFFECTS: searches the restaurant by type and prints it out
    private void searchByType() {
        System.out.println("Type in the type of food served at the restaurant: ");
        String type = input.nextLine();
        List<Restaurant> listByType = rl1.searchByType(type);

        if (listByType.isEmpty()) {
            System.out.println("The type of the restaurant was not found in the list.");
        } else {
            for (Restaurant r : listByType) {
                printInfo(r);
            }
        }
    }

    //EFFECTS: searches the restaurant by location and prints it out
    private void searchByLocation() {
        System.out.println("Type in the location of the restaurant: ");
        String location = input.nextLine();
        List<Restaurant> listByLocation = rl1.searchByLocation(location);

        if (listByLocation.isEmpty()) {
            System.out.println("The restaurant with the given location is not found in the list.");
        } else {
            for (Restaurant r : listByLocation) {
                printInfo(r);
            }
        }
    }

    // EFFECTS: prints out the information of the given restaurant
    private void printInfo(Restaurant r) {
        if (!(r.hasVisited())) {
            System.out.println("\nName: " + r.getName() + "\nType: " + r.getType() + "\nLocation: "
                    + r.getLocation() + "\nVisited?: Not yet!");
        } else {
            System.out.println("\nName: " + r.getName() + "\nType: " + r.getType() + "\nLocation: "
                    + r.getLocation() + "\nVisited?: Yes! \nRating: " + r.getRating());
        }
    }
}
