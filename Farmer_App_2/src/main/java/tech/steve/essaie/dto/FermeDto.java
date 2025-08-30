package tech.steve.essaie.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ValueGenerationType;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeDto {


    private Long id;


    @NotBlank
    private String nom;

    private String adresse;

    private String telephone;

    private String email;

    private Instant dateCreation = Instant.now();
    private LocalDateTime dateModification;
}

