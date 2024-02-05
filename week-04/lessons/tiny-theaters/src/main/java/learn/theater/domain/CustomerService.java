package learn.theater.domain;

import learn.theater.data.CustomerRepository;
import learn.theater.models.Customer;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findByLastName(String lastNamePrefix) {
        return repository.findByLastName(lastNamePrefix);
    }

    public Result<Customer> add(Customer customer) {
        Result<Customer> result = validate(customer);
        if (!result.isSuccess()) {
            return result;
        }
        customer = repository.add(customer);
        result.setPayload(customer);
        return result;
    }

    private Result<Customer> validate(Customer customer) {
        Result<Customer> result = new Result<>();

        if (customer == null) {
            result.addErrorMessage("customer cannot be null.");
            return result;
        }

        if (isBlank(customer.getFirstName())) {
            result.addErrorMessage("First name cannot be blank.");
        }

        if (isBlank(customer.getLastName())) {
            result.addErrorMessage("Last name cannot be blank.");
        }

        if (isBlank(customer.getEmailAddress())) {
            result.addErrorMessage("Email address cannot be blank.");
        }

        if (!EmailValidator.getInstance().isValid(customer.getEmailAddress())) {
            result.addErrorMessage("Email address is not valid.");
            return result;
        }

        Customer existing = repository.findByEmailAddress(customer.getEmailAddress());
        if (existing != null && existing.getCustomerId() != customer.getCustomerId()) {
            result.addErrorMessage("Duplicate email address.");
        }

        return result;
    }
}
