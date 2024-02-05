package learn.theater.models;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int ticketId;
    private Customer customer;
    private Performance performance;
    private Seat seat;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public static List<Ticket> fromCustomerPerformanceSeats(Customer customer, Performance performance, List<Seat> seats) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (Seat s : seats) {
            Ticket ticket = new Ticket();
            ticket.setCustomer(customer);
            ticket.setPerformance(performance);
            ticket.setSeat(s);
            tickets.add(ticket);
        }
        return tickets;
    }
}
