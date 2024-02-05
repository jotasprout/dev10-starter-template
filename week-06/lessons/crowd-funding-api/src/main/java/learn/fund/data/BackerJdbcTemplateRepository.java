package learn.fund.data;

import learn.fund.models.Backer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
public class BackerJdbcTemplateRepository implements BackerRepository {
    private final JdbcTemplate jdbcTemplate;

    public BackerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Backer findById(int backerId) {

        final String sql = """
                select backer_id, `name`, email_address
                from backer
                where backer_id = ?;
                """;

        return jdbcTemplate.query(sql, new BackerMapper(), backerId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Backer> findNameContains(String snippet) {

        final String sql = """
                select backer_id, `name`, email_address
                from backer
                where `name` like ?;
                """;

        return jdbcTemplate.query(sql, new BackerMapper(), "%" + snippet + "%");
    }

    @Override
    public Backer findByEmailAddress(String emailAddress) {

        final String sql = """
                select backer_id, `name`, email_address
                from backer
                where email_address = ?;
                """;

        return jdbcTemplate.query(sql, new BackerMapper(), emailAddress)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Backer add(Backer backer) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("backer")
                .usingGeneratedKeyColumns("backer_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("name", backer.getName());
        args.put("email_address", backer.getEmailAddress());

        int backerId = insert.executeAndReturnKey(args).intValue();
        backer.setBackerId(backerId);
        return backer;
    }

    @Override
    public boolean update(Backer backer) {

        final String sql = """
                update backer set
                    `name` = ?,
                    email_address = ?
                where backer_id = ?;
                """;

        return jdbcTemplate.update(sql,
                backer.getName(),
                backer.getEmailAddress(),
                backer.getBackerId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int backerId) {
        jdbcTemplate.update("delete from pledge where backer_id = ?;", backerId);
        return jdbcTemplate.update("delete from backer where backer_id = ?;", backerId) > 0;
    }
}
