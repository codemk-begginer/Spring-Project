package tech.steve.essaie.repository;

import org.springframework.data.repository.CrudRepository;
import tech.steve.essaie.model.Validation;

import java.time.Instant;
import java.util.Optional;

public interface ValidationsRepository extends CrudRepository<Validation,Integer> {

    Optional<Validation> findByCode(String code);

    void deleteAllByExpirationBefore(Instant now);
}
