package tech.steve.essaie.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.steve.essaie.enums.TypeDeRole;
import tech.steve.essaie.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByLibelle(@NotNull TypeDeRole role);
}
