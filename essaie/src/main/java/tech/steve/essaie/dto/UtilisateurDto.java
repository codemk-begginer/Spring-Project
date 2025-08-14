package com.porc.dto;

import com.porc.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String email;

    @NotBlank
    private String motDePasse;

    @NotNull
    private Role role;

    private Long fermeId;
}
