package learn.foraging.data;

import learn.foraging.DataHelper;
import learn.foraging.models.Category;
import learn.foraging.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static learn.foraging.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemJdbcTemplateRepositoryTest {

    static final int NEXT_ID = 5;
    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    ItemJdbcTemplateRepository repository = new ItemJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindByCategoryINEDIBLE() {
        List<Item> expected = List.of(INEDIBLE);
        List<Item> actual = repository.findByCategory(Category.INEDIBLE);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByCategoryMEDICINAL() {
        List<Item> expected = List.of(MEDICINAL);
        List<Item> actual = repository.findByCategory(Category.MEDICINAL);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById1() {
        Item expected = EDIBLE;
        Item actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindById15() {
        Item actual = repository.findById(15);
        assertNull(actual);
    }

    @Test
    void shouldAdd() {
        Item arg = new Item(0, "New", Category.EDIBLE, new BigDecimal("5.00"));
        Item expected = new Item(NEXT_ID, "New", Category.EDIBLE, new BigDecimal("5.00"));
        Item actual = repository.add(arg);
        assertEquals(expected, actual);

        actual = repository.findById(NEXT_ID);
        assertEquals(expected, actual);
    }

}