package learn.foraging.data;

import learn.foraging.models.Forager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ForagerJdbcTemplateRepository implements ForagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public ForagerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Forager findById(int id) {
        return null;
    }

    @Override
    public List<Forager> findByLastName(String lastNamePrefix) {
        final String sql = """
                select forager_id, first_name, last_name, state_abbr
                from forager
                where last_name like ?;
                """;
        return jdbcTemplate.query(sql, new ForagerMapper(), lastNamePrefix + "%");
    }

    @Override
    public List<Forager> findByState(String stateAbbr) {
        return null;
    }
}
