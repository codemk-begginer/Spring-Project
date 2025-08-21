package tech.steve.essaie.repository;


import tech.steve.essaie.model.Naissance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NaissanceRepository extends JpaRepository<Naissance, Long> {

//    List<Naissance> findByFermeId(Long fermeId);
//
//    List<Naissance> findByMereIdAndFermeId(Long mereId, Long fermeId);
//
//    List<Naissance> findByPereIdAndFermeId(Long pereId, Long fermeId);
//
//    Optional<Naissance> findByIdAndFermeId(Long id, Long fermeId);
}
