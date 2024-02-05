package learn.theater.ui;

import learn.theater.domain.*;
import learn.theater.models.*;

import java.util.List;

public class Controller {

    private final View view;
    private final CustomerService customerService;
    private final PerformanceService performanceService;
    private final SeatService seatService;
    private final ShowService showService;
    private final TicketService ticketService;

    public Controller(View view,
                      CustomerService customerService,
                      PerformanceService performanceService,
                      SeatService seatService,
                      ShowService showService,
                      TicketService ticketService) {
        this.view = view;
        this.customerService = customerService;
        this.performanceService = performanceService;
        this.seatService = seatService;
        this.showService = showService;
        this.ticketService = ticketService;
    }

    public void run() {
        view.displayHeader("Ramsey County Tiny Theaters");
        try {
            runMainMenu();
        } catch (Exception ex) {
            view.displayException(ex);
        }
        view.displayMessage("");
        view.displayMessage("Good bye.");
    }

    private void runMainMenu() {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case BOOK_TICKET -> bookTicket();
                case DISPLAY_PERFORMANCE_TICKETS -> displayPerformanceTickets();
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void bookTicket() {
        Customer customer = getCustomer();
        if (customer == null) {
            return;
        }
        view.displayCustomer(customer);

        List<Show> futureShows = showService.findByFuturePerformanceDate();
        Show show = view.selectShow(futureShows);
        if (show == null) {
            return;
        }
        view.displayShow(show);

        List<Performance> performances = performanceService.findByFutureShowId(show.getShowId());
        Performance performance = view.selectPerformance(performances);
        if (performance == null) {
            return;
        }
        view.displayPerformance(performance);

        List<Seat> availableSeats = seatService.findByPerformance(performance.getPerformanceId());
        List<Seat> selectedSeats = view.selectSeats(availableSeats);
        if (selectedSeats.size() == 0) {
            return;
        }

        List<Ticket> tickets = Ticket.fromCustomerPerformanceSeats(customer, performance, selectedSeats);

        Result<List<Ticket>> result = ticketService.add(tickets);
        view.displayResult(result);
    }

    private void displayPerformanceTickets() {
        DateRange range = view.getDateRange("Tickets By Performance");
        List<Show> shows = showService.findByPerformanceDateBetween(range.start(), range.end());
        Show show = view.selectShow(shows);
        if(show == null) {
            return;
        }

        view.displayShow(show);

        List<Performance> performances =
                performanceService.findByPerformanceDateBetweenAndShowId(
                        range.start(), range.end(), show.getShowId());
        Performance performance = view.selectPerformance(performances);
        if(performance == null) {
            return;
        }
        view.displayPerformance(performance);

        List<Ticket> tickets = ticketService.findByPerformanceId(performance.getPerformanceId());
        view.displayTickets(tickets);
    }

    private Customer getCustomer() {
        return switch (view.selectCustomerMenuOption()) {
            case EXIT_MENU -> null;
            case CREATE_NEW_CUSTOMER -> createNewCustomer();
            case SEARCH_EXISTING -> searchCustomerLastName();
        };
    }

    private Customer createNewCustomer() {
        Customer customer = view.createCustomer();
        Result<Customer> result = customerService.add(customer);
        view.displayResult(result);
        if (result.isSuccess()) {
            return result.getPayload();
        }
        return null;
    }

    private Customer searchCustomerLastName() {
        String lastNamePrefix = view.searchCustomerLastName();
        List<Customer> customers = customerService.findByLastName(lastNamePrefix);
        return view.selectCustomer(customers);
    }
}
