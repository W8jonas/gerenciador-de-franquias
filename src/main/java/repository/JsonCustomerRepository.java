package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.model.Customer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonCustomerRepository implements CustomerRepository {
    private static final String PATH = "data/customer.json";
    private final Gson gson = new Gson();

    @Override
    public List<Customer> findAll() {
        String content = File.read(PATH);
        if (content == null || content.isEmpty()) return new ArrayList<>();

        Type listType = new TypeToken<List<Customer>>() {}.getType();
        return gson.fromJson(content, listType);
    }

    @Override
    public void saveAll(List<Customer> customers) {
        String json = gson.toJson(customers);
        File.write(PATH, json);
    }

    @Override
    public Customer findById(String id) {
        return findAll().stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}
