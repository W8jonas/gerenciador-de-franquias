package repository;
import java.util.HashMap;
import java.util.Map;

public class InMemoryManagerRepository implements ManagerRepository {
    private final Map<String,String> db = new HashMap<>();
    public InMemoryManagerRepository() { db.put("mgr@corp.com","123"); }
    @Override public boolean isValidCredentials(String e,String p){ return p.equals(db.get(e)); }
}
