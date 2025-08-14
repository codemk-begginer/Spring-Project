package tech.chilo.avis.repository;

import org.springframework.data.repository.CrudRepository;
import tech.chilo.avis.entite.Validation;

import java.time.Instant;
import java.util.Optional;

public interface ValidationsRepository extends CrudRepository<Validation,Integer> {

    Optional<Validation> findByCode(String code);

    void deleteAllByExpirationBefore(Instant now);
}
