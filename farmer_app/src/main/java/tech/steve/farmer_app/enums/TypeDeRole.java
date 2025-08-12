package tech.steve.farmer_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static tech.steve.farmer_app.enums.TypePermission.*;

@AllArgsConstructor
public enum TypeDeRole {

    UTILISATEUR(
            Set.of(TypePermission.UTILISATEUR_CREATE_AVIS)
    ),


    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE_AVIS
            )
    ),






    ADMINISTRATEUR(
            Set.of(
                    ADMINISTRATEUR_CREATE,
                    ADMINISTRATEUR_READ,
                    ADMINISTRATEUR_UPDATE,
                    ADMINISTRATEUR_DELETE,

                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE_AVIS
            )
    );



    @Getter
    Set<TypePermission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions().stream().map(
                permissions -> new SimpleGrantedAuthority(permissions.name())
        ).collect(Collectors.toList());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
