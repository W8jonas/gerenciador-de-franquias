package repository;

import java.util.*;

public class InMemoryFranchiseRepository implements FranchiseRepository {

    // Estrutura simples: id -> mapa de campos
    private final Map<String, Map<String, String>> db = new LinkedHashMap<>();

    public InMemoryFranchiseRepository() {
        // seed opcional
        seed("test-1", "Franquia 1",  "Rua 1", "Juiz de Fora", "MG", "email1@franquia.com");
        seed("test-2", "Franquia 2",   "Rua 2",  "Belo Horizonte", "MG", "email2@franquia.com");
    }

    private void seed(String id, String name, String street, String city, String state, String mgr) {
        save(id, name, street, city, state, mgr);
    }

    @Override
    public String[][] findAllAsTable() {
        List<String[]> rows = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> e : db.entrySet()) {
            Map<String, String> f = e.getValue();
            rows.add(new String[] {
                    e.getKey(),
                    f.getOrDefault("name", ""),
                    f.getOrDefault("city", ""),
                    f.getOrDefault("state", ""),
                    f.getOrDefault("managerEmail", "")
            });
        }
        return rows.toArray(new String[0][0]);
    }

    @Override
    public boolean exists(String id) {
        return db.containsKey(id);
    }

    @Override
    public void save(String id, String name, String street, String city, String state, String managerEmail) {
        Map<String, String> f = new HashMap<>();
        f.put("name", name);
        f.put("street", street);
        f.put("city", city);
        f.put("state", state);
        f.put("managerEmail", managerEmail);
        db.put(id, f);
    }

    @Override
    public void update(String id, String name, String street, String city, String state, String managerEmail) {
        if (!db.containsKey(id)) return;
        Map<String, String> f = db.get(id);
        f.put("name", name);
        f.put("street", street);
        f.put("city", city);
        f.put("state", state);
        f.put("managerEmail", managerEmail);
    }

    @Override
    public void delete(String id) {
        db.remove(id);
    }
}
