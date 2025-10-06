package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AuthentificationDTO;
import tech.steve.essaie.dto.utilisateur.UtilisateurDto;
import tech.steve.essaie.dto.utilisateur.UtilisateurUpdateDto;
import tech.steve.essaie.service.JwtService;
import tech.steve.essaie.service.UtilisateurService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("utilisateur")
public class UtilisateurController {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;



    @PostMapping(path = "createUser")
    public void inscription(   @org.springframework.web.bind.annotation.RequestBody UtilisateurDto utilisateurDto){
        this.utilisateurService.create(utilisateurDto);
    }


    @PostMapping(path = "activation")
    @RequestBody(
            description = "Contient le code d'activation du compte d'un utilisateur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Map.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemple de requête valide",
                                    summary = "Requête pour activer le compte d'un utilisateur",
                                    description = "Les clés doivent correspondre aux noms de champs attendus par l'API.",
                                    value = "{\"code\": \"votre_code_d'activation_valide_ici\"}"
                            )
                    }
            )
    )
    public void activation(  @org.springframework.web.bind.annotation.RequestBody Map<String, String> activation){
        this.utilisateurService.activation(activation);
        log.info("activation");
    }



    @PostMapping(path = "connexion")
    @RequestBody(
            description = "Contient les informations de connections d'un utilisateur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Map.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemple de requête valide",
                                    summary = "Requête pour connecter un utilisateur utilisateur",
                                    description = "Les clés doivent correspondre aux noms de champs attendus par l'API.",
                                    value = "{\"username\": \"l'email_de_l'utilisateur_ici\",\"password\": \"le_mot_de_passe_de_l'utilisateur_ici\"}"
                            )
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur connecter avec succes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemple succès",
                                            value = "{ \"Bearer\": \"token_jwt\", \"refresh\": \"refresh_token\" }"
                                    )
                            }
                    )
            )

    })
    public Map<String , String> connexion(  @org.springframework.web.bind.annotation.RequestBody AuthentificationDTO authentificationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if(authenticate.isAuthenticated()){
            return this.jwtService.generate(authentificationDTO.username());
        }

        log.info("conexion");

        return null;

    }



    @PutMapping(path = "/updateUser/{id}")
    public ResponseEntity<UtilisateurUpdateDto> update(@Parameter(description = "ID de l'utilisateur a mettre a jour")@PathVariable UUID id, @org.springframework.web.bind.annotation.RequestBody UtilisateurUpdateDto utilisateurDto){
        log.info("update");

        return ResponseEntity.ok(utilisateurService.update(id, utilisateurDto));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "/findUserById/{id}")
    public ResponseEntity<UtilisateurUpdateDto> findById(@Parameter(description = "ID de l'utilisateur à rechercher") @PathVariable UUID id){
        log.info("findUserById");

        return ResponseEntity.ok(utilisateurService.findById(id));

    }



    @GetMapping(  path = "readAllUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UtilisateurUpdateDto> read(){

        log.info("read");
        return this.utilisateurService.findAll();

    }




    @PostMapping(path = "changePassword")
    @RequestBody(
            description = "Contient l'informations pour changer le mot de pass d'un utilisateur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Map.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemple de requête valide",
                                    summary = "Requête pour changer le mot de passe d'un utilisateur",
                                    description = "Les clés doivent correspondre aux noms de champs attendus par l'API.",
                                    value = "{\"email\": \"email_de_l'utilisateur_qui_veut_changer_de_mot_de_passe\"}"
                            )
                    }
            )
    )
    public void modifierMotDePasse(@org.springframework.web.bind.annotation.RequestBody  Map<String, String> activation) {
        this.utilisateurService.modifierMotDePasse(activation);
        log.info("password changed");
    }

// @Operation(summary = "Récupère tous les produits", description = "Retourne une liste de tous les produits disponibles")
//   @ApiResponses(value = {
//           @ApiResponse(responseCode = "200", description = "Produit trouvé et retourné"),
//           @ApiResponse(responseCode = "404", description = "Produit non trouvé")
//   })


    @PostMapping(path = "newPassword")
    @RequestBody(
            description = "Contient les informations pour changer le mot de pass d'un utilisateur",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Map.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemple de requête valide",
                                    summary = "Requête pour changer le mot de passe d'un utilisateur",
                                    description = "Les clés doivent correspondre aux noms de champs attendus par l'API.",
                                    value = "{\"email\": \"email_de_l'utilisateur_ici\",\"code\": \"code_recus_par_mail_ici\",\"password\": \"nouveau_mot_de_passe_de_l'utilisateur_ici\"}"
                            )
                    }
            )
    )
    public void nouveauMotDePasse(@org.springframework.web.bind.annotation.RequestBody Map<String, String> activation) {
        this.utilisateurService.nouveauMotDePasse(activation);
    }


    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
        log.info("deconnexion");
    }



    @PostMapping(path = "refresh-token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Jeton rafraîchi avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemple succès",
                                            value = "{ \"Bearer\": \"nouveau_token_jwt\", \"refresh\": \"nouveau_refresh_token\" }"
                                    )
                            }
                    )
            )

    })
    @RequestBody(
            description = "Contient le jeton de rafraîchissement",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Map.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemple de requête valide",
                                    summary = "Requête pour rafraîchir un jeton",
                                    description = "Les clés doivent correspondre aux noms de champs attendus par l'API.",
                                    value = "{\"refresh\": \"votre_jeton_de_rafraichissement_valide_ici\"}"
                            )
                    }
            )
    )
    public @ResponseBody Map<String , String> refreshToken(@org.springframework.web.bind.annotation.RequestBody Map<String, String> refreshTokenRequest) {
        log.info("Refresh Token");
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @PostMapping(path = "/deleteUser/{id}")
    public void inscription(@Parameter(description = "ID de l'utilisateur a supprimer")@PathVariable UUID id){
        this.utilisateurService.delete(id);
        log.info("user deleted");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(  path = "findByActif", produces = MediaType.APPLICATION_JSON_VALUE)
       public  List<UtilisateurUpdateDto> findByStatut(@Parameter(description = "type de l'utilisateur rechercher")@RequestParam(required = false) boolean actif ){
        log.info("findByActif");
        return this.utilisateurService.findByActif(actif);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "/findByFermeId/{id}")
    public List<UtilisateurUpdateDto> findByFermeId(@Parameter(description = "ID de la ferme de l'utilisateur rechercher")@PathVariable UUID id){
        log.info("findByFermeId");
        return this.utilisateurService.findByFerme(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(  path = "findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurUpdateDto> findByEmail(@Parameter(description = "email de l'utilisateur rechercher")@RequestParam(required = false) String email ){
        log.info("findByEmail");
        return ResponseEntity.ok(utilisateurService.findByEmail(email));
    }



}
