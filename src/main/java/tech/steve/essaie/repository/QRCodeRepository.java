package tech.steve.essaie.repository;

import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    Optional<QRCode> findByAnimal(Animal animal);
}
