package ui;

import model.Restaurant;
import model.RestaurantList;

import java.util.List;
import java.util.Scanner;

// a restaurant list application
public class RestaurantApp {
    private RestaurantList rl1;
    private Scanner input;

    // EFFECTS: runs the teller application
    public RestaurantApp() {
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

            if (command.equals("F")) {
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
        switch (command) {
            case "A":
                viewRestaurant();
                break;
            case "B":
                addRestaurant();
                break;
            case "C":
                removeRestaurant();
                break;
            case "D":
                searchRestaurant();
                break;
            case "E":
                visitedRestaurant();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the restaurant list
    private void init() {
        rl1 = new RestaurantList();
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
        System.out.println("F: Quit");
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
        System.out.println("Type in the following information.");
        System.out.println("Name of the restaurant: ");
        String name = input.next();
        System.out.println("Type of food served at the restaurant: ");
        String type = input.next();
        System.out.println("Location of the restaurant: ");
        String location = input.next();

        Restaurant r = new Restaurant(name, type, location);
        rl1.addRestaurant(r);

        System.out.println("The restaurant has been successfully added to the list.");
    }

    // MODIFIES: this
    // EFFECTS: conducts removal of restaurant
    private void removeRestaurant() {
        System.out.println("Type in the name of the restaurant you would like to remove.");
        String name = input.next();
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
        String selection = input.next();
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
        String name = input.next();
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

    // EFFECTS: searches the restaurant by name and prints it out
    private void searchByName() {
        System.out.println("Type in the name of the restaurant: ");
        String name = input.next();
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
        String type = input.next();
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
        String location = input.next();
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
