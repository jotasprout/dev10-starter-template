package learn.theater;

import learn.theater.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestHelper {

    public static final int NEXT_CUSTOMER_ID = 5;
    public static final int NEXT_THEATER_ID = 4;
    public static final int NEXT_SHOW_ID = 3;
    public static final int NEXT_PERFORMANCE_ID = 4;

    public static Customer makeCustomer(int customerId) {
        // ('First 1','Last 1', '555-5551','fl1@example.com'),
        Customer result = new Customer();
        result.setCustomerId(customerId);
        result.setFirstName(String.format("First %s", customerId));
        result.setLastName(String.format("Last %s", customerId));
        result.setPhone(String.format("555-555%s", customerId));
        result.setEmailAddress(String.format("fl%s@example.com", customerId));
        return result;
    }

    public static Theater makeTheater(int theaterId) {
        // ('Theater 1', '1 E Exchange St, St Paul, MN 55101', '(651) 555-5551', '1@rcttc.com'),
        Theater theater = new Theater();
        theater.setTheaterId(theaterId);
        theater.setName(String.format("Theater %s", theaterId));
        theater.setAddress(String.format("%s E Exchange St, St Paul, MN 55101", theaterId));
        theater.setPhone(String.format("(651) 555-555%s", theaterId));
        theater.setEmailAddress(String.format("%s@rcttc.com", theaterId));
        return theater;
    }

    public static Performance makePerformance(int performanceId) {
        // (date(concat(@next_year, '-01-01')), 11.00, 2, 1),
        int nextYear = LocalDate.now().getYear() + 1;
        Performance performance = new Performance();
        performance.setPerformanceId(performanceId);
        performance.setDate(LocalDate.of(nextYear, ((performanceId - 1) % 12) + 1, 1));
        performance.setTicketPrice(BigDecimal.TEN.add(BigDecimal.valueOf(performanceId)).setScale(2));
        int showId = ((performanceId - 1) % 2) + 1;
        performance.setShow(new Show(showId, String.format("Show %s", showId)));
        performance.setTheater(makeTheater(((performanceId - 1) % 3) + 1));
        return performance;
    }

    public static Ticket makeTicket(int ticketId) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setCustomer(makeCustomer(((ticketId - 1) % 4) + 1));
        ticket.setPerformance(makePerformance(ticketId / 3 + 1));
        ticket.setSeat(new Seat());
        return ticket;
    }
}
