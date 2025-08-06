package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.model.Order;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonOrderRepository implements OrderRepository {
    private static final String PATH = "data/order.json";
    private final Gson gson = new Gson();

    public void save(Order order) {
        List<Order> allOrders = findAll();
        allOrders.removeIf(o -> o.getId().equals(order.getId()));
        allOrders.add(order);
        write(allOrders);
    }

    public void update(Order updated) {
        List<Order> orders = findAll();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(updated.getId())) {
                orders.set(i, updated);
                break;
            }
        }
        write(orders);
    }

    public void delete(String orderId) {
        List<Order> orders = findAll();
        orders.removeIf(o -> o.getId().equals(orderId));
        write(orders);
    }

    public List<Order> findAll() {
        String content = File.read(PATH);
        if (content == null || content.isEmpty()) return new ArrayList<>();
        Type listType = new TypeToken<List<Order>>() {}.getType();
        return gson.fromJson(content, listType);
    }

    private void write(List<Order> orders) {
        String json = gson.toJson(orders);
        File.write(PATH, json);
    }

    @Override
    public Order findById(String orderId) {
        return findAll().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
