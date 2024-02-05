package learn.theater.data;

import learn.theater.models.Theater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.theater.TestHelper.NEXT_THEATER_ID;
import static learn.theater.TestHelper.makeTheater;
import static learn.theater.data.DataHelper.getJdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;

class TheaterJdbcTemplateRepositoryTest {

    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    TheaterJdbcTemplateRepository repository = new TheaterJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() {
        List<Theater> expected = List.of(
                makeTheater(1),
                makeTheater(2),
                makeTheater(3)
        );
        List<Theater> actual = repository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindId2() {
        Theater expected = makeTheater(2);
        Theater actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindId15() {
        Theater theater = repository.findById(15);
        assertNull(theater);
    }

    @Test
    void shouldAdd() {
        Theater arg = makeTheater(NEXT_THEATER_ID);
        arg.setTheaterId(0);

        Theater expected = makeTheater(NEXT_THEATER_ID);
        Theater actual = repository.add(arg);
        assertEquals(expected, actual);

        actual = repository.findById(NEXT_THEATER_ID);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateId2() {
        Theater expected = makeTheater(1);
        expected.setName("Updated");
        expected.setAddress("Updated");
        expected.setPhone("123-4567");
        expected.setEmailAddress("updated@example.com");

        assertTrue(repository.update(expected));

        Theater actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateId15() {
        Theater arg = makeTheater(15);
        arg.setName("Updated");
        assertFalse(repository.update(arg));
    }

    @Test
    void shouldDeleteId3() {
        assertTrue(repository.deleteById(3));
        assertNull(repository.findById(3));
    }

    @Test
    void shouldNotDeleteId15() {
        assertFalse(repository.deleteById(15));
    }

}