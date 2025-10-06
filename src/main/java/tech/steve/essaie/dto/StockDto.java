package tech.steve.essaie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import tech.steve.essaie.enums.CathegorieProduit;
import tech.steve.essaie.model.Ferme;

import java.time.LocalDate;

import java.util.UUID;


public record StockDto(
        @NotBlank(message = "Le nom du produit est requis")
        String produit,

        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 0, message = "La quantité ne peut pas être négative")
        Integer quantite,



        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate Horodatage,


        String typeMouvement,

        String description,

        CathegorieProduit categorie,


        String fournisseur,


        String nomIntervenant,

        String telephoneIntervenant,

        int SeuilAlerte  ,

        UUID fermeId
) {




}