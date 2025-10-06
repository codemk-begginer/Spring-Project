package tech.steve.essaie.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TypePermission {

    ADMINISTRATEUR_CREATE,
    ADMINISTRATEUR_READ,
    ADMINISTRATEUR_UPDATE,
    ADMINISTRATEUR_DELETE,

     MANAGER_CREATE,
    MANAGER_READ,
    MANAGER_UPDATE,
    MANAGER_DELETE,

     VETERINAIRE_CREATE,
    VETERINAIRE_READ,
    VETERINAIRE_UPDATE,
    VETERINAIRE_DELETE,


    OUVRIER_CREATE,
    OUVRIER_READ,
    OUVRIER_UPDATE,
    OUVRIER_ARCHIVE;

    @Getter
    private String libelle;

}
