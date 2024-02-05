package learn.theater.data;

import learn.theater.models.Theater;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class TheaterJdbcTemplateRepository implements TheaterRepository {

    private final JdbcTemplate jdbcTemplate;

    public TheaterJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Theater> findAll() {
        final String sql = """
                select theater_id, theater_name, address, phone, email_address
                from theater
                where hidden = false;
                """;
        return jdbcTemplate.query(sql, new TheaterMapper());
    }

    @Override
    public Theater findById(int theaterId) {
        final String sql = """
                select theater_id, theater_name, address, phone, email_address
                from theater
                where hidden = false
                and theater_id = ?;
                """;

        return jdbcTemplate.query(sql, new TheaterMapper(), theaterId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Theater add(Theater theater) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("theater")
                .usingGeneratedKeyColumns("theater_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("theater_name", theater.getName());
        args.put("address", theater.getAddress());
        args.put("phone", theater.getPhone());
        args.put("email_address", theater.getEmailAddress());
        args.put("hidden", false);

        int theaterId = insert.executeAndReturnKey(args).intValue();
        theater.setTheaterId(theaterId);
        return theater;
    }

    @Override
    public boolean update(Theater theater) {

        final String sql = """
                update theater set
                    theater_name = ?,
                    address = ?,
                    phone = ?,
                    email_address = ?
                where theater_id = ?;
                """;

        return jdbcTemplate.update(sql,
                theater.getName(),
                theater.getAddress(),
                theater.getPhone(),
                theater.getEmailAddress(),
                theater.getTheaterId()) > 0;
    }

    @Override
    public boolean deleteById(int theaterId) {

        final String sql = """
                update theater set
                    hidden = true
                where theater_id = ?;
                """;

        return jdbcTemplate.update(sql, theaterId) > 0;
    }
}
