package tech.steve.farmer_app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.steve.farmer_app.enums.TypeDeRole;
import tech.steve.farmer_app.model.Utilisateur;
import tech.steve.farmer_app.repository.UtilisateurRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service


public class UtilisateurService implements UserDetailsService {


    private final UtilisateurRepository utilisateurRepository;

    private final ValidationService validationService;

    private final BCryptPasswordEncoder passwordEncoder;



    public UtilisateurService(UtilisateurRepository utilisateurRepository, ValidationService validationService, BCryptPasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    public void inscription(Utilisateur utilisateur){
        if(!utilisateur.getEmail().contains("@")){
            throw new RuntimeException("Votre email est invalide");
        }

        if(!utilisateur.getEmail().contains(".")){
            throw new RuntimeException("Votre email est invalide");
        }



        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());

        if(utilisateurOptional.isPresent()){
            throw new RuntimeException("Votre email est déjas utiliser ");
        }

        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
        utilisateur.setRole(roleUtilisateur);
        utilisateur =  this.utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(utilisateur);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));

        if(Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Votre code a expirer , <br/> demandez un nouveau !!!");
        }

        Utilisateur utilisateurActiver =  this.utilisateurRepository.findById(validation.getUtilisateur()
                .getId()).orElseThrow(()-> new RuntimeException("Utilisateur inconue"));
        utilisateurActiver.setActif(true);

        this.utilisateurRepository.save(utilisateurActiver);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository.findByEmail(username)
                .orElseThrow(
                        () -> new
                                UsernameNotFoundException("aucun utilisateur ne correspond a cette identifiant ") );

    }

    public void modifierMotDePasse(Map<String, String> parametres) {
        Utilisateur utilisateur = (Utilisateur) this.loadUserByUsername(parametres.get("email"));
        this.validationService.enregistrer(utilisateur);
    }

    public void nouveauMotDePasse(Map<String, String> parametres) {
        Utilisateur utilisateur = (Utilisateur) this.loadUserByUsername(parametres.get("email"));
        final Validation validation = validationService.lireEnFonctionDuCode(parametres.get("code"));
        if(validation.getUtilisateur().getEmail().equals(utilisateur.getEmail())) {
            String mdpCrypte = this.passwordEncoder.encode(parametres.get("password"));
            utilisateur.setMdp(mdpCrypte);
            this.utilisateurRepository.save(utilisateur);
        }
    }


    public List<Utilisateur> liste() {
        final Iterable<Utilisateur> utilisateurIterable = this.utilisateurRepository.findAll();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        for (Utilisateur utilisateur:utilisateurIterable){
            utilisateurs.add(utilisateur);
        }
        return utilisateurs;

    }
}
