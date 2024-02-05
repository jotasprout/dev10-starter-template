package learn.theater.data;

import learn.theater.models.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.theater.data.DataHelper.getJdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatJdbcTemplateRepositoryTest {

    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    SeatJdbcTemplateRepository repository = new SeatJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void shouldFindByPerformanceId1() {
        var expected = List.of(
                new Seat(1, "A1", 1, true),
                new Seat(2, "A2", 1, true),
                new Seat(3, "A3", 1, true),
                new Seat(4, "A4", 1, false)
        );
        var actual = repository.findByPerformance(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByPerformanceId4() {
        var expected = List.of();
        var actual = repository.findByPerformance(4);
        assertEquals(expected, actual);
    }

}