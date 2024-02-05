package learn.theater.data;

import learn.theater.models.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

public class TicketJdbcTemplateRepository implements TicketRepository {

    private final JdbcTemplate jdbcTemplate;

    public TicketJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ticket> findByPerformanceId(int performanceId) {
        final String sql = """
                select t.ticket_id,
                    c.customer_id, c.first_name, c.last_name, c.phone, c.email_address,
                    s.seat_id, s.label, s.theater_id, 1 booked
                from ticket t
                inner join customer c on t.customer_id = c.customer_id
                inner join performance p on t.performance_id = p.performance_id
                inner join seat s on t.seat_id = s.seat_id
                where p.performance_id = ?
                order by c.last_name, c.first_name, s.label;
                """;
        return jdbcTemplate.query(sql, new TicketMapper(), performanceId);
    }

    @Override
    @Transactional
    public List<Ticket> add(List<Ticket> tickets) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ticket")
                .usingGeneratedKeyColumns("ticket_id");

        for(Ticket ticket: tickets) {

            HashMap<String, Object> args = new HashMap<>();
            args.put("customer_id", ticket.getCustomer().getCustomerId());
            args.put("performance_id", ticket.getPerformance().getPerformanceId());
            args.put("seat_id", ticket.getSeat().getSeatId());

            int ticketId = insert.executeAndReturnKey(args).intValue();
            ticket.setTicketId(ticketId);
        }

        return tickets;
    }
}
