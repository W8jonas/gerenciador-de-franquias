package useCases.customer;

import domain.model.Customer;
import repository.CustomerRepository;

import java.util.List;

public class CustomerUseCase {

    private final CustomerRepository customerRepository;

    public CustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        List<Customer> customers = findAll();

        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(customer.getId())) {
                throw new IllegalArgumentException("Já existe um cliente com este ID.");
            }
        }

        customers.add(customer);
        customerRepository.saveAll(customers);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(String id) {
        return customerRepository.findById(id);
    }

    public void update(Customer updatedCustomer) {
        List<Customer> customers = findAll();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equalsIgnoreCase(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        customerRepository.saveAll(customers);
    }

    public void deleteById(String id) {
        List<Customer> customers = findAll();
        customers.removeIf(c -> c.getId().equalsIgnoreCase(id));
        customerRepository.saveAll(customers);
    }
}
