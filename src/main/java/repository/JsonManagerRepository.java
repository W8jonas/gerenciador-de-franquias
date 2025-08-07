package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class JsonManagerRepository implements ManagerRepository {

    private static final String DIRECTORY = "data";
    private static final String PATH = DIRECTORY + java.io.File.separator + "managers.json";

    private final Gson gson = new Gson();
    private final Type listType = new TypeToken<List<Row>>() {}.getType();

    private static class Row {
        String id;
        String name;
        String email;
        String password;
        boolean active;

        Row(String id, String name, String email, String password, boolean active) {
            this.id = id; this.name = name; this.email = email; this.password = password; this.active = active;
        }
    }

    @Override
    public String[][] findAllAsTable() {
        List<Row> rows = loadAll();
        String[][] table = new String[rows.size()][4];
        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            table[i][0] = r.id;
            table[i][1] = r.name;
            table[i][2] = r.email;
            table[i][3] = r.active ? "true" : "false";
        }
        return table;
    }

    @Override
    public boolean exists(String id) {
        return loadAll().stream().anyMatch(r -> Objects.equals(r.id, id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return loadAll().stream().anyMatch(r -> Objects.equals(r.email, email));
    }

    @Override
    public void save(String id, String name, String email, String password, boolean active) {
        ensureDirectory();
        List<Row> rows = loadAll();
        rows.add(new Row(id, name, email, password, active));
        persist(rows);
    }

    @Override
    public void update(String id, String name, String email, String password, boolean active) {
        ensureDirectory();
        List<Row> rows = loadAll();
        for (Row r : rows) {
            if (Objects.equals(r.id, id)) {
                r.name = name;
                r.email = email;
                r.password = password;
                r.active = active;
                persist(rows);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        ensureDirectory();
        List<Row> rows = loadAll();
        rows.removeIf(r -> Objects.equals(r.id, id));
        persist(rows);
    }

    @Override
    public boolean isValidCredentials(String email, String password) {
        return loadAll().stream()
                .anyMatch(r -> r.active && Objects.equals(r.email, email) && Objects.equals(r.password, password));
    }

    private List<Row> loadAll() {
        String json = File.read(PATH);
        String trimmed = json == null ? "" : json.trim();
        if (trimmed.isEmpty()) return new ArrayList<>();
        try {
            List<Row> rows = gson.fromJson(trimmed, listType);
            return (rows == null) ? new ArrayList<>() : rows;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void persist(List<Row> rows) {
        String json = gson.toJson(rows, listType);
        File.write(PATH, json);
    }

    private void ensureDirectory() {
        java.io.File dir = new java.io.File(DIRECTORY);
        if (!dir.exists()) dir.mkdirs();
    }
}
