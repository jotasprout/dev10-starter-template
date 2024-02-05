package learn.theater.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static learn.theater.data.DataHelper.getJdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;

class TicketJdbcTemplateRepositoryTest {
    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    TicketJdbcTemplateRepository repository = new TicketJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void findByPerformanceId2() {

    }
}