package learn.theater.data;

import learn.theater.models.Show;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ShowJdbcTemplateRepository implements ShowRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShowJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Show> findAll() {
        final String sql = """
                select show_id, show_name
                from `show`
                where hidden = false;
                """;
        return jdbcTemplate.query(sql, new ShowMapper());
    }

    @Override
    public List<Show> findByFuturePerformanceDate() {
        final String sql = """
                select distinct s.show_id, s.show_name
                from performance p
                inner join `show` s on p.show_id = s.show_id
                where p.performance_date >= curdate()
                and s.hidden = false
                order by s.show_name;
                """;
        return jdbcTemplate.query(sql, new ShowMapper());
    }

    @Override
    public List<Show> findByPerformanceDateBetween(LocalDate start, LocalDate end) {
        final String sql = """
                select distinct s.show_id, s.show_name
                from performance p
                inner join `show` s on p.show_id = s.show_id
                where p.performance_date between ? and ?
                and s.hidden = false
                order by s.show_name;
                """;
        return jdbcTemplate.query(sql, new ShowMapper(), start, end);
    }

    @Override
    public Show findById(int showId) {
        final String sql = """
                select show_id, show_name
                from `show`
                where hidden = false
                and show_id = ?;
                """;

        return jdbcTemplate.query(sql, new ShowMapper(), showId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Show add(Show show) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("`show`")
                .usingGeneratedKeyColumns("show_id")
                .usingColumns("show_name");

        HashMap<String, Object> args = new HashMap<>();
        args.put("show_name", show.getName());

        int showId = insert.executeAndReturnKey(args).intValue();
        show.setShowId(showId);
        return show;
    }

    @Override
    public boolean update(Show show) {
        final String sql = """
                update `show` set
                    show_name = ?
                where show_id = ?;
                """;

        return jdbcTemplate.update(sql, show.getName(), show.getShowId()) > 0;
    }

    @Override
    public boolean deleteById(int showId) {
        final String sql = """
                update `show` set
                    hidden = true
                where show_id = ?;
                """;

        return jdbcTemplate.update(sql, showId) > 0;
    }
}
