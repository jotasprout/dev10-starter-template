package learn.theater.data;

import learn.theater.models.Performance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformanceMapper implements RowMapper<Performance> {
    @Override
    public Performance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Performance performance = new Performance();
        performance.setPerformanceId(rs.getInt("performance_id"));
        performance.setDate(rs.getDate("performance_date").toLocalDate());
        performance.setTicketPrice(rs.getBigDecimal("ticket_price"));
        performance.setAvailableSeats(rs.getInt("available_seats"));

        // map full objects
        performance.setShow(new ShowMapper().mapRow(rs, rowNum));
        performance.setTheater(new TheaterMapper().mapRow(rs, rowNum));

        return performance;
    }
}
