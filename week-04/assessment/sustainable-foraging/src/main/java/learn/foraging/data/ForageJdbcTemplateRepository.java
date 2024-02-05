package learn.foraging.data;

import learn.foraging.models.Forage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ForageJdbcTemplateRepository implements ForageRepository {

    private final JdbcTemplate jdbcTemplate;

    public ForageJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Forage findById(int forageId) {
        final String sql = """
                select f.forage_id, f.`date`, f.amount,
                    fr.forager_id, fr.first_name, fr.last_name, fr.state_abbr,
                    i.item_id, i.`name`, i.category, i.dollars_per_kilogram
                from forage f
                inner join forager fr on f.forager_id = fr.forager_id
                inner join item i on f.item_id = i.item_id
                where f.forage_id = ?
                order by i.`name` asc, fr.last_name asc;
                """;
        return jdbcTemplate.query(sql, new ForageMapper(), forageId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Forage> findByDate(LocalDate date) {
        final String sql = """
                select f.forage_id, f.`date`, f.amount,
                    fr.forager_id, fr.first_name, fr.last_name, fr.state_abbr,
                    i.item_id, i.`name`, i.category, i.dollars_per_kilogram
                from forage f
                inner join forager fr on f.forager_id = fr.forager_id
                inner join item i on f.item_id = i.item_id
                where f.date = ?
                order by i.`name` asc, fr.last_name asc;
                """;
        return jdbcTemplate.query(sql, new ForageMapper(), date);
    }

    @Override
    public Forage add(Forage forage) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("forage")
                .usingGeneratedKeyColumns("forage_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("date", forage.getDate());
        args.put("amount", forage.getKilograms());
        args.put("forager_id", forage.getForager().getId());
        args.put("item_id", forage.getItem().getId());

        int forageId = insert.executeAndReturnKey(args).intValue();
        forage.setId(forageId);

        return forage;
    }

    @Override
    public boolean update(Forage forage) {
        return false;
    }
}
