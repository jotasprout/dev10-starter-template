package learn.theater.data;

import learn.theater.models.Seat;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SeatJdbcTemplateRepository implements SeatRepository {
    private final JdbcTemplate jdbcTemplate;

    public SeatJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Seat> findByPerformance(int performanceId) {
        final String sql = """
                select s.seat_id, s.label, s.theater_id,
                    tk.ticket_id booked
                from performance p
                inner join theater t on p.theater_id = t.theater_id
                inner join seat s on t.theater_id = s.theater_id
                left outer join ticket tk on p.performance_id = tk.performance_id
                    and s.seat_id = tk.seat_id
                where p.performance_id = ?
                order by s.label;
                """;
        return jdbcTemplate.query(sql, new SeatMapper(), performanceId);
    }
}
