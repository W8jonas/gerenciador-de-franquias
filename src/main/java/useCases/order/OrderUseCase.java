package useCases.order;

import domain.model.Order;
import repository.JsonOrderRepository;

import java.util.List;

public class OrderUseCase {
    private final JsonOrderRepository repository;

    public OrderUseCase(JsonOrderRepository jsonOrderRepository) {
        this.repository = new JsonOrderRepository();
    }

    public void save(Order order) {
        repository.save(order);
    }

    public void update(Order order) {
        repository.update(order);
    }

    public void delete(String orderId) {
        repository.delete(orderId);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(String orderId) {
        return repository.findById(orderId);
    }
}