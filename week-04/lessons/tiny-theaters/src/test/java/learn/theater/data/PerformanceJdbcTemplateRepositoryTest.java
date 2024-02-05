package learn.theater.data;

import learn.theater.models.Performance;
import learn.theater.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static learn.theater.TestHelper.*;
import static learn.theater.data.DataHelper.getJdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceJdbcTemplateRepositoryTest {

    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    PerformanceJdbcTemplateRepository repository = new PerformanceJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void shouldFindByFutureShowId() {
        var expected = List.of(
                makePerformance(1),
                makePerformance(3)
        );
        var actual = repository.findByFutureShowId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByFutureShowId() {
        List<Performance> expected = List.of();
        var actual = repository.findByFutureShowId(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByPerformanceDateBetweenAndShowId() {
        int nextYear = LocalDate.now().getYear() + 1;
        LocalDate start = LocalDate.of(nextYear, 1, 15);
        LocalDate end = LocalDate.of(nextYear, 2, 15);
        var expected = List.of(makePerformance(2));
        var actual = repository.findByPerformanceDateBetweenAndShowId(start, end, 2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByPerformanceDateBetweenAndShowId() {
        int nextYear = LocalDate.now().getYear() + 1;
        LocalDate start = LocalDate.of(nextYear, 6, 15);
        LocalDate end = LocalDate.of(nextYear, 7, 15);
        List<Performance> expected = List.of();
        var actual = repository.findByPerformanceDateBetweenAndShowId(start, end, 2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindId3() {
        Performance expected = makePerformance(3);
        Performance actual = repository.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindId15() {
        Performance actual = repository.findById(15);
        assertNull(actual);
    }

    @Test
    void shouldAdd() {
        Performance arg = makePerformance(NEXT_PERFORMANCE_ID);
        arg.setPerformanceId(0);

        Performance expected = makePerformance(NEXT_PERFORMANCE_ID);
        Performance actual = repository.add(arg);
        assertEquals(expected, actual);

        actual = repository.findById(NEXT_PERFORMANCE_ID);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateId2() {
        Performance expected = makePerformance(2);
        expected.setDate(expected.getDate().withMonth(6));
        expected.setTicketPrice(BigDecimal.valueOf(25.00).setScale(2));
        expected.setShow(new Show(1, "Show 1"));
        expected.setTheater(makeTheater(3));

        assertTrue(repository.update(expected));

        Performance actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateId15() {
        Performance arg = makePerformance(15);
        assertFalse(repository.update(arg));
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