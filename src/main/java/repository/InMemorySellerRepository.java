package repository;

import domain.model.Owner;
import domain.model.Seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class InMemorySellerRepository implements SellerRepository {
    private final AtomicReference<Seller> singleton = new AtomicReference<>(null);
    private final Map<String,String> db = new HashMap<>();
    public InMemorySellerRepository() { db.put("sell@corp.com","123"); }
    @Override public boolean isValidCredentials(String e,String p){ return p.equals(db.get(e)); }

    @Override
    public void saveAll(List<Seller> sellers) {

    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public Seller findByEmail(String email) {
        return null;
    }


}
