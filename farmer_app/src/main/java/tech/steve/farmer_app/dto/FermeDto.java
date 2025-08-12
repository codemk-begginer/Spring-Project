package tech.steve.farmer_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}

