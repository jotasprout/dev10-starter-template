package learn.foraging.data;

import learn.foraging.models.Category;
import learn.foraging.models.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class ItemJdbcTemplateRepository implements ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public ItemJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> findByCategory(Category category) {
        final String sql = """
                select item_id, `name`, category, dollars_per_kilogram
                from item
                where category = ?;
                """;
        return jdbcTemplate.query(sql, new ItemMapper(), category.toString());
    }

    @Override
    public Item findById(int id) {
        final String sql = """
                select item_id, `name`, category, dollars_per_kilogram
                from item
                where item_id = ?;
                """;
        return jdbcTemplate.query(sql, new ItemMapper(), id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Item add(Item item) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("item")
                .usingGeneratedKeyColumns("item_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("name", item.getName());
        args.put("category", item.getCategory().toString());
        args.put("dollars_per_kilogram", item.getDollarPerKilogram());

        int itemId = insert.executeAndReturnKey(args).intValue();
        item.setId(itemId);

        return item;
    }
}
