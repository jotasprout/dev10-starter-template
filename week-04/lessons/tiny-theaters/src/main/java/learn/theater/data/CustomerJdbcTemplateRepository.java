package learn.theater.data;

import learn.theater.models.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class CustomerJdbcTemplateRepository implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findByLastName(String prefix) {
        final String sql = """
                select customer_id, first_name, last_name, phone, email_address
                from customer
                where last_name like ?;""";

        return jdbcTemplate.query(sql, new CustomerMapper(), prefix + "%");
    }

    @Override
    public Customer findById(int customerId) {
        final String sql = """
                select customer_id, first_name, last_name, phone, email_address
                from customer
                where customer_id = ?;""";

        return jdbcTemplate.query(sql, new CustomerMapper(), customerId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Customer findByEmailAddress(String emailAddress) {
        final String sql = """
                select customer_id, first_name, last_name, phone, email_address
                from customer
                where email_address = ?;""";

        return jdbcTemplate.query(sql, new CustomerMapper(), emailAddress)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Customer add(Customer customer) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("customer")
                .usingGeneratedKeyColumns("customer_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("first_name", customer.getFirstName());
        args.put("last_name", customer.getLastName());
        args.put("phone", customer.getPhone());
        args.put("email_address", customer.getEmailAddress());

        int customerId = insert.executeAndReturnKey(args).intValue();
        customer.setCustomerId(customerId);
        return customer;
    }

    @Override
    public boolean update(Customer customer) {

        final String sql = """
                update customer set
                    first_name = ?,
                    last_name = ?,
                    phone = ?,
                    email_address = ?
                where customer_id = ?;
                """;

        return jdbcTemplate.update(sql,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getEmailAddress(),
                customer.getCustomerId()) > 0;
    }

    @Override
    public boolean deleteById(int customerId) {
        jdbcTemplate.update("delete from ticket where customer_id = ?;", customerId);
        return jdbcTemplate.update("delete from customer where customer_id = ?;", customerId) > 0;
    }
}
