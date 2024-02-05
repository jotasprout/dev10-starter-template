package learn.theater.data;

import learn.theater.models.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findByLastName(String prefix);

    Customer findById(int customerId);

    Customer findByEmailAddress(String emailAddress);

    Customer add(Customer customer);

    boolean update(Customer customer);

    boolean deleteById(int customerId);
}
