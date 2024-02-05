package learn.theater.ui;

import learn.theater.domain.Result;
import learn.theater.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class View {
    private final ConsoleIO io = new ConsoleIO();

    // Menus
    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        MainMenuOption[] options = MainMenuOption.values();
        for (int i = 0; i < options.length; i++) {
            io.printf("%s. %s%n", i, options[i].getLabel());
        }
        String prompt = String.format("Select [0-%s]: ", options.length - 1);
        return options[io.readInt(prompt, 0, options.length - 1)];
    }

    public CustomerMenuOption selectCustomerMenuOption() {
        displayHeader("Customer Menu");
        CustomerMenuOption[] options = CustomerMenuOption.values();
        for (int i = 0; i < options.length; i++) {
            io.printf("%s. %s%n", i, options[i].getLabel());
        }
        String prompt = String.format("Select [0-%s]: ", options.length - 1);
        return options[io.readInt(prompt, 0, options.length - 1)];
    }

    // displays

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayMessage(String message) {
        io.println(message);
    }

    public <T> void displayResult(Result<T> result) {
        if (result.isSuccess()) {
            displayMessage("Success.");
        } else {
            displayMessage("[ERROR]");
            for (String message : result.getErrorMessages()) {
                displayMessage(message);
            }
        }
    }

    public void displayException(Exception ex) {
        io.println("A critical error occurred.");
        io.println(ex.getMessage());
    }

    public void displayCustomer(Customer customer) {
        io.printf("Customer: %s %s%n", customer.getFirstName(), customer.getLastName());
    }

    public void displayShow(Show show) {
        io.printf("Show: %s%n", show.getName());
    }

    public void displayPerformance(Performance performance) {
        io.printf("Show: %s%n", performance.getShow().getName());
        io.printf("Date: %s%n", performance.getDate().format(io.dateFormatter));
        io.printf("Ticket Price: %s%n", performance.getTicketPrice());
        io.printf("Theater: %s%n", performance.getTheater().getName());
    }

    public void displayTickets(List<Ticket> tickets) {
        if (tickets == null || tickets.size() == 0) {
            io.println("No tickets found.");
        } else {

            Map<Customer, List<String>> map = tickets.stream()
                    .collect(Collectors.groupingBy(
                            t -> t.getCustomer(),
                            Collectors.mapping(t -> t.getSeat().getLabel(), Collectors.toList())
                    ));

            for(Customer key : map.keySet()) {
                io.printf("%s %s%n", key.getFirstName(), key.getLastName());
                io.println(Arrays.toString(map.get(key).toArray()));
            }
        }
    }

    //

    public Customer createCustomer() {
        displayHeader("New Customer");
        Customer c = new Customer();
        c.setFirstName(io.readRequiredString("First Name: "));
        c.setLastName(io.readRequiredString("Last Name: "));
        c.setPhone(io.readRequiredString("Phone: "));
        c.setEmailAddress(io.readRequiredString("Email Address: "));
        return c;
    }

    public String searchCustomerLastName() {
        displayHeader("Search Customer by Last Name");
        return io.readRequiredString("Last Name Prefix: ");
    }

    public DateRange getDateRange(String header) {
        displayHeader(header);
        LocalDate start = io.readLocalDate("Start [MM/dd/yyyy]: ");
        LocalDate end = io.readLocalDate("End [MM/dd/yyyy]: ");
        return new DateRange(start, end);
    }

    // selectors

    public Customer selectCustomer(List<Customer> customers) {
        if (customers.size() == 0) {
            displayMessage("No customers found.");
            return null;
        }

        int size = customers.size();

        if (size > 20) {
            displayMessage("The customer list contains more than 20 customers. Refine your search.");
            size = 20;
        }

        io.println("0. Exit.");
        for (int i = 0; i < size; i++) {
            Customer c = customers.get(i);
            io.printf("%s. %s %s: %s%n", i + 1, c.getFirstName(), c.getLastName(), c.getEmailAddress());
        }

        String prompt = String.format("Select [0-%s]: ", size);
        int index = io.readInt(prompt, 0, size);
        if (index == 0) {
            return null;
        }
        return customers.get(index - 1);
    }

    public Show selectShow(List<Show> shows) {
        if (shows.size() == 0) {
            displayMessage("No shows found.");
            return null;
        }

        io.println("0. Exit.");
        for (int i = 0; i < shows.size(); i++) {
            Show s = shows.get(i);
            io.printf("%s. %s%n", i + 1, s.getName());
        }


        String prompt = String.format("Select [0-%s]: ", shows.size());
        int index = io.readInt(prompt, 0, shows.size());
        if (index == 0) {
            return null;
        }
        return shows.get(index - 1);
    }

    public Performance selectPerformance(List<Performance> performances) {
        if (performances.size() == 0) {
            displayMessage("No performances found.");
            return null;
        }

        io.println("0. Exit.");
        for (int i = 0; i < performances.size(); i++) {
            Performance performance = performances.get(i);
            io.printf("%s. %s: %s, %s Available Seats: %s%n",
                    i + 1,
                    performance.getDate().format(io.dateFormatter),
                    performance.getTicketPrice(),
                    performance.getTheater().getName(),
                    performance.getAvailableSeats());
        }


        String prompt = String.format("Select [0-%s]: ", performances.size());
        int index = io.readInt(prompt, 0, performances.size());
        if (index == 0) {
            return null;
        }
        return performances.get(index - 1);
    }


    public List<Seat> selectSeats(List<Seat> availableSeats) {
        ArrayList<Seat> seats = new ArrayList<>();
        if (availableSeats.size() == 0) {
            io.println("No seats available.");
            return seats;
        }

        String label;

        do {
            if (availableSeats.size() == 0) {
                break;
            }
            printSeats(availableSeats);
            label = io.readString("Select a seat number or press [Enter] to exit: ");
            if (!label.isBlank()) {
                for (Seat seat : availableSeats) {
                    if (!seat.isBooked() && seat.getLabel().equalsIgnoreCase(label)) {
                        seats.add(seat);
                        seat.setBooked(true);
                        break;
                    }
                }
            }
        } while (!label.isBlank());

        if (seats.size() == 0) {
            displayMessage("No seats selected.");
        }

        return seats;
    }

    private void printSeats(List<Seat> seats) {
        char letter = 'A';
        for (Seat seat : seats) {
            if (letter != seat.getLabel().charAt(0)) {
                io.println("");
                letter++;
            }
            if (seat.isBooked()) {
                io.print("__ ");
            } else {
                io.print(seat.getLabel() + " ");
            }
        }
        io.println("");
    }
}
