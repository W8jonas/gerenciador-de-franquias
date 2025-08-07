package repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryManagerRepository implements ManagerRepository {

    private static class Row {
        String id;
        String name;
        String email;
        String password;
        boolean active;

        Row(String id, String name, String email, String password, boolean active) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.active = active;
        }
    }

    private final Map<String, Row> db = new LinkedHashMap<>();

    /** Construtor padrão com um seed útil para testes (mgr@corp.com / 123). */
    public InMemoryManagerRepository() {
        this(true);
    }

    /** @param withSeed se true, cria um manager padrão para facilitar testes. */
    public InMemoryManagerRepository(boolean withSeed) {
        if (withSeed) {
            save("mgr-1", "Default Manager", "mgr@corp.com", "123", true);
        }
    }

    @Override
    public String[][] findAllAsTable() {
        String[][] table = new String[db.size()][4];
        int i = 0;
        for (Row r : db.values()) {
            table[i][0] = r.id;
            table[i][1] = r.name;
            table[i][2] = r.email;
            table[i][3] = r.active ? "true" : "false";
            i++;
        }
        return table;
    }

    @Override
    public boolean exists(String id) {
        return db.containsKey(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        for (Row r : db.values()) {
            if (Objects.equals(r.email, email)) return true;
        }
        return false;
    }

    @Override
    public void save(String id, String name, String email, String password, boolean active) {
        db.put(id, new Row(id, name, email, password, active));
    }

    @Override
    public void update(String id, String name, String email, String password, boolean active) {
        Row r = db.get(id);
        if (r != null) {
            r.name = name;
            r.email = email;
            r.password = password;
            r.active = active;
        }
    }

    @Override
    public void delete(String id) {
        db.remove(id);
    }

    @Override
    public boolean isValidCredentials(String email, String password) {
        for (Row r : db.values()) {
            if (r.active && Objects.equals(r.email, email) && Objects.equals(r.password, password)) {
                return true;
            }
        }
        return false;
    }

    /* ===== Métodos auxiliares opcionais (não estão na interface) ===== */

    /** Remove todos os registros (útil em testes). */
    public void clear() {
        db.clear();
    }

    /** Retorna o total de managers em memória (útil para asserts em testes). */
    public int size() {
        return db.size();
    }
}
