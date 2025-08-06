package repository;

import domain.model.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);
    void update(Order order);
    void delete(String orderId);
    List<Order> findAll();
    Order findById(String orderId);
}
