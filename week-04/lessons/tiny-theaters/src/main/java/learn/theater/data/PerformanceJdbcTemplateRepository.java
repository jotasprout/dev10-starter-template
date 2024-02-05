package learn.theater.data;

import learn.theater.models.Performance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class PerformanceJdbcTemplateRepository implements PerformanceRepository {
    private final JdbcTemplate jdbcTemplate;

    public PerformanceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Performance> findByFutureShowId(int showId) {
        final String sql = """
                select p.performance_id, p.performance_date, p.ticket_price,
                    s.show_id, s.show_name,
                    t.theater_id, t.theater_name, t.address, t.phone, t.email_address,
                    (select count(*) from seat where theater_id = t.theater_id) - count(tk.ticket_id) available_seats
                from performance p
                inner join `show` s on p.show_id = s.show_id
                inner join theater t on p.theater_id = t.theater_id
                left outer join ticket tk on p.performance_id = tk.performance_id
                where p.performance_date >= curdate()
                and s.hidden = false
                and s.show_id = ?
                group by p.performance_id, p.performance_date, p.ticket_price,
                    s.show_id, s.show_name,
                    t.theater_id, t.theater_name, t.address, t.phone, t.email_address
                order by p.performance_date, s.show_name, t.theater_name;
                """;

        return jdbcTemplate.query(sql, new PerformanceMapper(), showId);
    }

    @Override
    public List<Performance> findByPerformanceDateBetweenAndShowId(LocalDate start, LocalDate end, int showId) {
        final String sql = """
                select p.performance_id, p.performance_date, p.ticket_price,
                    s.show_id, s.show_name,
                    t.theater_id, t.theater_name, t.address, t.phone, t.email_address,
                    (select count(*) from seat where theater_id = t.theater_id) - count(tk.ticket_id) available_seats
                from performance p
                inner join `show` s on p.show_id = s.show_id
                inner join theater t on p.theater_id = t.theater_id
                left outer join ticket tk on p.performance_id = tk.performance_id
                where p.performance_date between ? and ?
                and s.hidden = false
                and s.show_id = ?
                group by p.performance_id, p.performance_date, p.ticket_price,
                    s.show_id, s.show_name,
                    t.theater_id, t.theater_name, t.address, t.phone, t.email_address
                order by p.performance_date, s.show_name, t.theater_name;
                """;

        return jdbcTemplate.query(sql, new PerformanceMapper(), start, end, showId);
    }

    @Override
    public Performance findById(int performanceId) {
        final String sql = """
                select p.performance_id, p.performance_date, p.ticket_price,
                    s.show_id, s.show_name,
                    t.theater_id, t.theater_name, t.address, t.phone, t.email_address,
                    0 available_seats
                from performance p
                inner join `show` s on p.show_id = s.show_id
                inner join theater t on p.theater_id = t.theater_id
                where p.performance_id = ?
                and s.hidden = false
                """;

        return jdbcTemplate.query(sql, new PerformanceMapper(), performanceId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Performance add(Performance performance) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("performance")
                .usingGeneratedKeyColumns("performance_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("performance_date", performance.getDate());
        args.put("ticket_price", performance.getTicketPrice());
        args.put("show_id", performance.getShow().getShowId());
        args.put("theater_id", performance.getTheater().getTheaterId());

        int performanceId = insert.executeAndReturnKey(args).intValue();
        performance.setPerformanceId(performanceId);
        return performance;
    }

    @Override
    public boolean update(Performance performance) {
        final String sql = """
                update performance set
                    performance_date = ?,
                    ticket_price = ?,
                    show_id = ?,
                    theater_id = ?
                where performance_id = ?;
                """;

        return jdbcTemplate.update(sql,
                performance.getDate(),
                performance.getTicketPrice(),
                performance.getShow().getShowId(),
                performance.getTheater().getTheaterId(),
                performance.getPerformanceId()) > 0;
    }

    @Override
    public boolean deleteById(int performanceId) {
        jdbcTemplate.update("delete from ticket where performance_id = ?", performanceId);
        return jdbcTemplate.update("delete from performance where performance_id = ?;", performanceId) > 0;
    }
}
