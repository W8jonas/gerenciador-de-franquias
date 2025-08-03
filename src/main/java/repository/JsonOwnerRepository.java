package repository;

import com.google.gson.Gson;
import domain.model.Owner;

import java.util.Optional;

public class JsonOwnerRepository implements OwnerRepository {

    private static final String DIRECTORY = "data";
    private static final String PATH = DIRECTORY + java.io.File.separator + "owner.json";

    private final Gson gson = new Gson();

    @Override
    public boolean existsAnyOwner() {
        return loadOwner() != null;
    }

    @Override
    public Optional<Owner> findFirstOwner() {
        return Optional.ofNullable(loadOwner());
    }

    @Override
    public void save(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner cannot be null");
        }
        String json = gson.toJson(owner);
        File.write(PATH, json);
    }


    @Override
    public void deleteAll() {
        java.io.File f = new java.io.File(PATH);
        if (f.exists()) {
            if (!f.delete()) {
                File.write(PATH, "");
            }
        }
    }

    private Owner loadOwner() {
        String json = File.read(PATH);
        if (json == null) return null;
        String trimmed = json.trim();
        if (trimmed.isEmpty()) return null;

        try {
            return gson.fromJson(trimmed, Owner.class);
        } catch (Exception e) {
            return null;
        }
    }
}
