package tech.steve.essaie.dto.utilisateur;

import tech.steve.essaie.enums.TypeDeRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.steve.essaie.model.Role;


import java.util.UUID;


public record UtilisateurDto(
                             @NotBlank
                             String nom,

                             @NotBlank
                             String prenom,

                             @NotBlank
                             String email,

                             @NotBlank
                             String motDePasse,

                             @NotBlank
                             String telephone,

                             @NotNull
                             TypeDeRole role,

                             UUID fermeId) {


}
