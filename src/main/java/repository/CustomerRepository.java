package repository;

import domain.model.Customer;
import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
    void saveAll(List<Customer> customers);
    Customer findById(String id);
}
