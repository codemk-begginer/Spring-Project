package tech.steve.essaie.dto.utilisateur;



import tech.steve.essaie.enums.TypeDeRole;
import tech.steve.essaie.model.Role;

import java.util.UUID;


public record UtilisateurUpdateDto(
                                   String nom,


                                   String prenom,


                                   String email,


                                   String telephone,


                                   TypeDeRole role,

                                   UUID fermeId) {

}
