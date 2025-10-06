package tech.steve.essaie.dto;



import java.util.List;
import java.util.UUID;


public record AnimalGenealogieDto( UUID id,
                                   String nom,
                                   String code,
                                   String sexe,
                                   String statut,
                                   String qrCodeUrl,
                                   Integer generation,
                                   String dateNaissance,

                                   // Parents (peuvent être null si inconnus)
                                   AnimalGenealogieDto pere,
                                   AnimalGenealogieDto mere,

                                   // Descendance directe
                                   List<AnimalGenealogieDto> enfants) {


}
