package learn.theater.data;

import learn.theater.models.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(rs.getInt("ticket_id"));
        ticket.setCustomer(new CustomerMapper().mapRow(rs, rowNum));
        ticket.setSeat(new SeatMapper().mapRow(rs, rowNum));
        return ticket;
    }
}
