package learn.theater.domain;

import learn.theater.data.CustomerRepository;
import learn.theater.data.PerformanceRepository;
import learn.theater.data.SeatRepository;
import learn.theater.data.TicketRepository;
import learn.theater.models.Customer;
import learn.theater.models.Performance;
import learn.theater.models.Seat;
import learn.theater.models.Ticket;

import java.util.HashSet;
import java.util.List;

public class TicketService {
    private final CustomerRepository customerRepository;
    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public TicketService(CustomerRepository customerRepository,
                         PerformanceRepository performanceRepository,
                         SeatRepository seatRepository,
                         TicketRepository ticketRepository) {
        this.customerRepository = customerRepository;
        this.performanceRepository = performanceRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> findByPerformanceId(int performanceId) {
        return ticketRepository.findByPerformanceId(performanceId);
    }

    public Result<List<Ticket>> add(List<Ticket> tickets) {
        Result<List<Ticket>> result = validate(tickets);
        if (!result.isSuccess()) {
            return result;
        }

        List<Ticket> added = ticketRepository.add(tickets);
        result.setPayload(added);
        return result;
    }

    private Result<List<Ticket>> validate(List<Ticket> tickets) {
        Result<List<Ticket>> result = new Result<>();

        if (tickets == null || tickets.isEmpty() || tickets.stream().anyMatch(t -> t == null)) {
            result.addErrorMessage("tickets cannot be null or empty");
            return result;
        }

        HashSet<Customer> customers = new HashSet<>();
        HashSet<Performance> performances = new HashSet<>();
        HashSet<Integer> seatIds = new HashSet<>();

        for (Ticket ticket : tickets) {
            if (ticket.getCustomer() == null
                    || ticket.getPerformance() == null
                    || ticket.getSeat() == null
                    || ticket.getCustomer().getCustomerId() <= 0
                    || ticket.getPerformance().getPerformanceId() <= 0
                    || ticket.getSeat().getSeatId() <= 0) {
                result.addErrorMessage("invalid customer, performance, or seat");
                return result;
            }
            customers.add(ticket.getCustomer());
            performances.add(ticket.getPerformance());
            seatIds.add(ticket.getSeat().getSeatId());
        }

        for (Customer customer : customers) {
            if (customerRepository.findById(customer.getCustomerId()) == null) {
                result.addErrorMessage("invalid customerId " + customer.getCustomerId());
                return result;
            }
        }

        for (Performance performance : performances) {

            if (performanceRepository.findById(performance.getPerformanceId()) == null) {
                result.addErrorMessage("invalid performanceId " + performance.getPerformanceId());
                return result;
            }

            for (Seat s : seatRepository.findByPerformance(performance.getPerformanceId())) {
                if (seatIds.contains(s.getSeatId())) {
                    if (s.isBooked()) {
                        result.addErrorMessage(String.format("seat %s is booked", s.getLabel()));
                        return result;
                    }
                    seatIds.remove(s.getSeatId());
                }
            }
        }

        if (seatIds.size() > 0) {
            Integer invalidSeatId = seatIds.stream().findFirst().orElse(null);
            result.addErrorMessage(String.format("invalid seatId %s", invalidSeatId));
            return result;
        }

        return result;
    }
}
