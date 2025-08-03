package repository;

import domain.model.Owner;

import java.util.Optional;

public interface OwnerRepository {
    boolean existsAnyOwner();
    Optional<Owner> findFirstOwner();
    void save(Owner owner);
    void deleteAll();
}
