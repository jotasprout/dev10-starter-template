package learn.theater.data;

import learn.theater.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.theater.TestHelper.NEXT_CUSTOMER_ID;
import static learn.theater.TestHelper.makeCustomer;
import static learn.theater.data.DataHelper.getJdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJdbcTemplateRepositoryTest {

    JdbcTemplate jdbcTemplate = getJdbcTemplate();

    CustomerJdbcTemplateRepository repository = new CustomerJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindCustomerLastNamesStartingWithL() {
        List<Customer> expected = List.of(
                makeCustomer(1),
                makeCustomer(2),
                makeCustomer(3),
                makeCustomer(4)
        );
        List<Customer> actual = repository.findByLastName("L");
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindCustomerLastNamesStartingWithA() {
        List<Customer> customers = repository.findByLastName("A");
        assertEquals(0, customers.size());
    }

    @Test
    void shouldFindById2() {
        Customer actual = repository.findById(2);
        Customer expected = makeCustomer(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindById15() {
        Customer actual = repository.findById(15);
        assertNull(actual);
    }

    @Test
    void shouldFindEmailfl2examplecom() {
        Customer actual = repository.findByEmailAddress("fl2@example.com");
        Customer expected = makeCustomer(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindEmailnopeexamplecom() {
        Customer actual = repository.findByEmailAddress("nope@example.com");
        assertNull(actual);
    }

    @Test
    void shouldAdd() {

        Customer arg = makeCustomer(NEXT_CUSTOMER_ID);
        arg.setCustomerId(0);

        Customer expected = makeCustomer(NEXT_CUSTOMER_ID);
        Customer actual = repository.add(arg);
        assertEquals(expected, actual);

        // fetch again, just to be safe
        actual = repository.findById(NEXT_CUSTOMER_ID);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateId3() {
        Customer expected = makeCustomer(3);
        expected.setFirstName("Updated");
        expected.setLastName("Updated");
        expected.setPhone("");
        expected.setEmailAddress("update@example.com");

        assertTrue(repository.update(expected));

        Customer actual = repository.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateId15() {
        Customer arg = makeCustomer(15);
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