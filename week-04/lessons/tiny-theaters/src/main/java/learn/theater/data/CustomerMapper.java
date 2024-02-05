package learn.theater.data;

import learn.theater.models.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer result = new Customer();
        result.setCustomerId(rs.getInt("customer_id"));
        result.setFirstName(rs.getString("first_name"));
        result.setLastName(rs.getString("last_name"));
        result.setPhone(rs.getString("phone"));
        result.setEmailAddress(rs.getString("email_address"));
        return result;
    }
}
