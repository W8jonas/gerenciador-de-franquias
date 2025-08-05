package repository;

import java.util.HashMap;
import java.util.Map;

public class InMemorySellerRepository implements SellerRepository {
    private final Map<String,String> db = new HashMap<>();
    public InMemorySellerRepository() { db.put("sell@corp.com","123"); }
    @Override public boolean isValidCredentials(String e,String p){ return p.equals(db.get(e)); }
}
