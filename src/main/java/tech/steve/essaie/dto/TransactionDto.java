package tech.steve.essaie.dto;

import tech.steve.essaie.enums.CathegorieTransaction;
import tech.steve.essaie.enums.TypeTransaction;


import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.UUID;


public record TransactionDto( TypeTransaction type,

                              CathegorieTransaction categorie,

                              BigDecimal montant,

                              String description,

                              UUID animalId,

                              String nomIntervenant,

                              LocalDate date,

                              String telephoneIntervenant,


                              UUID fermeId) {


}