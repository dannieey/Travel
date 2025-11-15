import facade.TravelFacade;
import booking.PriceSubject;
import booking.UserObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {

    public static void run() {
        Scanner sc = new Scanner(System.in);
        TravelFacade travel = new TravelFacade();
        PriceSubject priceSubject = travel.getPriceSubject();

        System.out.println("     Travel System    ");

        System.out.print("Enter your name (for notifications): ");
        String userName = sc.nextLine();
        UserObserver user = new UserObserver(userName);
        priceSubject.registerObserver(user);

        System.out.print("Where are you departing from? ");
        String origin = sc.nextLine();

        System.out.print("Enter your destination country/city: ");
        String destination = sc.nextLine();

        String transport = askOption(
                sc,
                "Choose transport (plane/car/cruise): ",
                new String[]{"plane", "car", "cruise"}
        );
        travel.selectTransport(transport);

        String seatClass = askOption(
                sc,
                "Select ticket class (economy/business): ",
                new String[]{"economy", "business"}
        );
        if (seatClass.equalsIgnoreCase("business")) {
            System.out.println("Business class selected (+300,00$).");
        } else {
            System.out.println("Economy class selected.");
        }

        String dateTo = askDate(sc, "Departure date (dd-mm-yyyy): ");
        String dateBack = askOptionalDate(sc, "Return date (dd-mm-yyyy) or empty for one-way: ");

        travel.bookTransport(origin, destination, dateTo, dateBack);

        travel.applySeatClassUpgrade(seatClass);

        double ticketPrice = travel.getBooking().getPrice();
        System.out.printf("Current total cost with seat class: %.2f\n", ticketPrice);

        // Опции питания
        boolean mealAdded = false;
        String mealType = "None";
        boolean wantsMeal = askYesNo(sc, "Would you like to add a meal? (yes/no): ");
        if (wantsMeal) {
            mealType = askOption(
                    sc,
                    "Choose meal type (standard/vegetarian/vegan/no-preference): ",
                    new String[]{"standard", "vegetarian", "vegan", "no-preference"}
            );
            travel.addMealOption();
            mealAdded = true;
            ticketPrice = travel.getBooking().getPrice();
            System.out.printf("Meal option \"%s\" added (+30,00$).\n", mealType);
            System.out.printf("Current total cost: %.2f\n", ticketPrice);
        }

        boolean insuranceAdded = false;
        boolean wantsInsurance = askYesNo(sc, "Would you like to add travel insurance? (yes/no): ");
        if (wantsInsurance) {
            travel.addInsuranceOption();
            insuranceAdded = true;
            ticketPrice = travel.getBooking().getPrice();
            System.out.println("Insurance added (+40,00$).");
            System.out.printf("Current total cost: %.2f\n", ticketPrice);
        }

        String mood = askOption(
                sc,
                "Choose travel mood (relaxation/adrenaline/romance/custom): ",
                new String[]{"relaxation", "adrenaline", "romance", "custom"}
        );

        if (mood.equalsIgnoreCase("custom")) {
            System.out.println("You selected CUSTOM mode.");
            System.out.println("What places would you like to visit?");
            System.out.println("(Enter a list separated by commas, or press Enter to skip): ");
            String customInput = sc.nextLine();

            List<String> customStops = new ArrayList<>();
            if (!customInput.trim().isEmpty()) {
                String[] parts = customInput.split(",");
                for (String p : parts) {
                    String stop = p.trim();
                    if (!stop.isEmpty()) {
                        customStops.add(stop);
                    }
                }
            }
            travel.selectCustomRoute(customStops, destination);
        } else {
            travel.selectRoute(mood, destination);
        }

        double finalCost = travel.getBooking().getPrice();

        System.out.println("\nTRIP SUMMARY");
        System.out.println("Traveler: " + userName);
        System.out.println("Origin: " + origin);
        System.out.println("Destination: " + destination);
        System.out.println("Transport: " + transport);
        System.out.println("Departure date: " + dateTo);
        System.out.println("Return date: " + (dateBack.isEmpty() ? "One-way" : dateBack));

        System.out.println("Seat class: " + (seatClass.equalsIgnoreCase("business") ? "business" : "economy"));
        if (mealAdded) {
            System.out.println("Meal: " + mealType + " (included)");
        } else {
            System.out.println("Meal: Not included");
        }
        System.out.println("Insurance: " + (insuranceAdded ? "Included" : "Not included"));

        System.out.println("\nMood / Route Type: " + mood);
        String[] stops = travel.getPlannedStops();
        System.out.println("Planned Stops:");
        for (String s : stops) {
            System.out.println(" - " + s);
        }

        System.out.printf("\nCURRENT TOTAL COST: %.2f\n", finalCost);

        boolean everythingOk = askYesNo(sc, "\nDoes everything look good? (yes/no): ");

        if (!everythingOk) {
            boolean modifyMore = true;
            while (modifyMore) {
                System.out.println("\nWhat would you like to change?");
                System.out.println("1 - Remove a stop from the route");
                System.out.println("2 - Add a new stop to the route");
                System.out.println("3 - Keep everything as it is");

                String choice = askMenuChoice(sc, "Your choice (1/2/3): ", new String[]{"1", "2", "3"});

                switch (choice) {
                    case "1":
                        stops = travel.getPlannedStops();
                        if (stops.length == 0) {
                            System.out.println("There are no stops to remove.");
                        } else {
                            System.out.println("Current stops:");
                            for (int i = 0; i < stops.length; i++) {
                                System.out.printf("%d - %s%n", i + 1, stops[i]);
                            }
                            int idx = askIntInRange(
                                    sc,
                                    "Enter the number of the stop you want to remove: ",
                                    1,
                                    stops.length
                            );
                            String removed = stops[idx - 1];
                            travel.removeStop(idx - 1);
                            System.out.println("Stop \"" + removed + "\" removed from the route.");
                        }
                        break;
                    case "2":
                        System.out.print("Enter the name of the new stop: ");
                        String newStop = sc.nextLine();
                        travel.addStop(newStop);
                        System.out.println("Stop \"" + newStop + "\" added to the route.");
                        break;
                    case "3":
                    default:
                        modifyMore = false;
                        break;
                }

                System.out.println("\nUpdated route:");
                stops = travel.getPlannedStops();
                for (String s : stops) {
                    System.out.println(" - " + s);
                }
                System.out.printf("Route changes do not affect ticket price.%nFINAL TOTAL COST: %.2f%n", finalCost);

                if (!modifyMore) break;

                boolean moreChanges = askYesNo(sc, "\nIs there anything else you want to change? (yes/no): ");
                if (!moreChanges) {
                    modifyMore = false;
                }
            }
        }

        System.out.println("\nYour trip is confirmed!");
        System.out.println("Notification will be sent to: " + userName);
        System.out.println("Thank you for using Travel System.");

        sc.close();
    }

    private static boolean askYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("yes") || ans.equals("y")) return true;
            if (ans.equals("no") || ans.equals("n")) return false;
            System.out.println("Please enter 'yes' or 'no'.");
        }
    }

    private static String askOption(Scanner sc, String prompt, String[] allowed) {
        while (true) {
            System.out.print(prompt);
            String ans = sc.nextLine().trim().toLowerCase();
            for (String opt : allowed) {
                if (ans.equals(opt.toLowerCase())) {
                    return ans;
                }
            }
            System.out.print("Invalid input. Allowed options: ");
            for (int i = 0; i < allowed.length; i++) {
                System.out.print(allowed[i]);
                if (i < allowed.length - 1) System.out.print("/");
            }
            System.out.println(".");
        }
    }

    private static String askMenuChoice(Scanner sc, String prompt, String[] allowed) {
        while (true) {
            System.out.print(prompt);
            String ans = sc.nextLine().trim();
            for (String opt : allowed) {
                if (ans.equals(opt)) {
                    return ans;
                }
            }
            System.out.println("Invalid choice. Please enter one of: " + String.join(", ", allowed) + ".");
        }
    }

    private static int askIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter digits only.");
            }
        }
    }

    private static String askDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String date = sc.nextLine().trim();
            if (isValidDateFormat(date)) {
                return date;
            } else {
                System.out.println("Invalid date format. Please use dd-mm-yyyy (e.g., 05-12-2025).");
            }
        }
    }

    private static String askOptionalDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String date = sc.nextLine().trim();
            if (date.isEmpty()) {
                return "";
            }
            if (isValidDateFormat(date)) {
                return date;
            } else {
                System.out.println("Invalid date format. Please use dd-mm-yyyy (e.g., 05-12-2025) or leave empty for one-way.");
            }
        }
    }

    private static boolean isValidDateFormat(String date) {
        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }
        try {
            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
