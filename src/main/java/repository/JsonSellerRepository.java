package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.model.Seller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonSellerRepository implements SellerRepository {
    private static final String PATH = "data/seller.json";
    private final Gson gson = new Gson();

    @Override
    public boolean isValidCredentials(String email, String password) {
        for (Seller seller : findAll()) {
            if (seller.getEmail().equalsIgnoreCase(email)
                    && seller.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveAll(List<Seller> sellers) {
        String json = gson.toJson(sellers);
        File.write(PATH, json);
    }

    @Override
    public List<Seller> findAll() {
        String content = File.read(PATH);
        if (content == null || content.isEmpty()) return new ArrayList<>();

        Type listType = new TypeToken<List<Seller>>() {}.getType();
        return gson.fromJson(content, listType);
    }

    @Override
    public Seller findByEmail(String email) {
        return findAll().stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
