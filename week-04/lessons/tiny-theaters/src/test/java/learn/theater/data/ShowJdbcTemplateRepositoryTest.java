package learn.theater.data;

import learn.theater.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static learn.theater.TestHelper.NEXT_SHOW_ID;
import static learn.theater.data.DataHelper.getJdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;

class ShowJdbcTemplateRepositoryTest {

    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    ShowJdbcTemplateRepository repository = new ShowJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() {
        List<Show> expected = List.of(
                new Show(1, "Show 1"),
                new Show(2, "Show 2")
        );
        List<Show> actual = repository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByFuturePerformanceDate() {
        List<Show> expected = List.of(
                new Show(1, "Show 1"),
                new Show(2, "Show 2")
        );
        List<Show> actual = repository.findByFuturePerformanceDate();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByPerformanceDateBetween() {

        int nextYear = LocalDate.now().getYear() + 1;
        LocalDate start = LocalDate.of(nextYear, 1, 15);
        LocalDate end = LocalDate.of(nextYear, 2, 15);

        List<Show> expected = List.of(new Show(1, "Show 1"));

        List<Show> actual = repository.findByPerformanceDateBetween(start, end);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindId2() {
        Show expected = new Show(2, "Show 2");
        Show actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindId15() {
        Show actual = repository.findById(15);
        assertNull(actual);
    }

    @Test
    void shouldAdd() {
        Show arg = new Show(0, "New Show");
        Show expected = new Show(NEXT_SHOW_ID, "New Show");
        Show actual = repository.add(arg);
        assertEquals(expected, actual);

        actual = repository.findById(NEXT_SHOW_ID);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateId1() {
        Show expected = new Show(1, "Updated");
        assertTrue(repository.update(expected));

        Show actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateId15() {
        Show expected = new Show(15, "Show 15");
        expected.setName("Updated");
        assertFalse(repository.update(expected));
    }

    @Test
    void shouldDeleteId1() {
        assertTrue(repository.deleteById(1));
        assertNull(repository.findById(1));
    }

    @Test
    void shouldNotDeleteId15() {
        assertFalse(repository.deleteById(15));
    }

}