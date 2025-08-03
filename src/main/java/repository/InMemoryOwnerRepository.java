package repository;

import domain.model.Owner;
import repository.OwnerRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class InMemoryOwnerRepository implements OwnerRepository {

    private final AtomicReference<Owner> singleton = new AtomicReference<>(null);

    @Override
    public boolean existsAnyOwner() {
        return singleton.get() != null;
    }

    @Override
    public Optional<Owner> findFirstOwner() {
        return Optional.ofNullable(singleton.get());
    }

    @Override
    public void save(Owner owner) {
        singleton.set(owner);
    }

    @Override
    public void deleteAll() {
        singleton.set(null);
    }
}
