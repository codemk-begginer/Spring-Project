package tech.steve.essaie.dto;



import java.time.LocalDate;
import java.util.UUID;


public record AlerteDto( UUID animalId,

                         UUID fermeId,

                         String type,

                         String message,

                         LocalDate date,

                         boolean lue,

                         String status // Exemple : ACTIF, TRAITÉ, ARCHIVÉ
                          ) { }