package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AuthentificationDTO;
import tech.steve.essaie.dto.FermeDto;
import tech.steve.essaie.dto.UtilisateurDto;
import tech.steve.essaie.service.FermeService;
import tech.steve.essaie.service.JwtService;
import tech.steve.essaie.service.UtilisateurService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class UtilisateurController {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    @PostMapping(path = "createUser")
    public void inscription(@RequestBody UtilisateurDto utilisateurDto){


        this.utilisateurService.create(utilisateurDto);
        log.info("create");
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation){


        this.utilisateurService.activation(activation);
        log.info("activation");
    }

    @PostMapping(path = "connexion")
    public Map<String , String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
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
    public ResponseEntity<UtilisateurDto> update(@PathVariable Long id, @RequestBody  UtilisateurDto utilisateurDto){
        log.info("update");

        return ResponseEntity.ok(utilisateurService.update(id, utilisateurDto));

    }

    @GetMapping(path = "/findUserById/{id}")
    public ResponseEntity<UtilisateurDto> findById(@PathVariable Long id){
        log.info("findUserById");

        return ResponseEntity.ok(utilisateurService.findById(id));

    }


    @GetMapping(  path = "readAllUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UtilisateurDto> read(){

        log.info("read");
        return this.utilisateurService.findAll();

    }

    @PostMapping(path = "changePassword")
    public void modifierMotDePasse(@RequestBody Map<String, String> activation) {
        this.utilisateurService.modifierMotDePasse(activation);
        log.info("password changed");
    }

    @PostMapping(path = "newPassword")
    public void nouveauMotDePasse(@RequestBody Map<String, String> activation) {
        this.utilisateurService.nouveauMotDePasse(activation);
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion(@RequestBody Map<String, String> activation){

        this.jwtService.deconnexion();

        log.info("deconnexion");

    }

    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String , String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest){
        log.info("Refresh Token");

        return  this.jwtService.refreshToken(refreshTokenRequest);

    }

    @PostMapping(path = "/deleteUser/{id}")
    public void inscription(@PathVariable Long id){


        this.utilisateurService.delete(id);
        log.info("user deleted");
    }

    @GetMapping(  path = "findByActif", produces = MediaType.APPLICATION_JSON_VALUE)
       public  List<UtilisateurDto> findByStatut(@RequestParam(required = false) boolean actif ){
        log.info("findByActif");
        return this.utilisateurService.findByActif(actif);
    }

    @GetMapping(path = "/findByFermeId/{id}")
    public List<UtilisateurDto> findByFermeId(@PathVariable Long id){
        log.info("findByFermeId");
        return this.utilisateurService.findByFerme(id);
    }

    @GetMapping(  path = "findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> findByEmail(@RequestParam(required = false) String email ){
        log.info("findByEmail");
        return ResponseEntity.ok(utilisateurService.findByEmail(email));
    }



}
