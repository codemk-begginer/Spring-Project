package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AlerteDto;
import tech.steve.essaie.service.AlerteService;
import tech.steve.essaie.service.impl.AlerteServiceImpl;

import java.util.List;
import java.util.UUID;


/**
 * <b>CRUD ENDPOINTS for Alerte class</b>
 */
@RestController
@AllArgsConstructor
@RequestMapping("alerte")
public class AlerteController {

    private final AlerteService alerteService;

    /**
     * Use the status property of class alerte to list alerte
     * @return list of Alerte
     */

    @GetMapping(path = "findByStatut")
    public List<AlerteDto> findByStatut(
            @Parameter(description = "entrez le status de l'alerte recherchee") @RequestParam String status){
        return this.alerteService.findByStatut(status);
    }


    /**
     * Use the animal property of  alerte class to list alerte
     * @return list of Alerte
     */
     @GetMapping(path = "findAlerteByAnimal")
    public List<AlerteDto> findAlerteByAnimal(
             @Parameter(description = "entrez l'ID de l'animal relier a l'alerte recherchee")@RequestParam UUID animalId){
        return this.alerteService.findByAnimal(animalId);
    }

    /**
     * Use the id  property of  alerte class to mark an alert as read
     */
    @PostMapping(path = "marquerCommeLue/{id}")
    public void marquerCommeLue(
            @Parameter(description = "entrez l'ID de l'alerte que vous voulez marquer comme lue")@PathVariable UUID id){
        this.alerteService.marquerCommeLue(id);
    }

    /**
     * Use the id  property of  alerte class to delete an alert
     */
    @PostMapping(path = "deleteAlerte/{id}")
    public void delete(
            @Parameter(description = "entrez l'ID de l'alerte que vous voulez supprimee")@PathVariable UUID id){
       this.alerteService.supprimer(id);
    }

    /**
     * @return list of all alerte
     */
    @GetMapping(path = "findAllAlerte")
    public List<AlerteDto> findAllAlerte(){
       return this.alerteService.findAll();
    }

    /**
     * @return list of all alerte where boolean property lue is false
     */
    @GetMapping(path = "findNonLue")
    public List<AlerteDto> findNonLue(){
       return this.alerteService.findNonLues();
    }

    /**
     * Use the type  property of  alerte class to list an alert
     * @return List of alert
     */
    @GetMapping(path = "findAlerteByType")
    public List<AlerteDto> findAlerteByType(
            @Parameter(description = "entrez le type de l'alerte recherchee")@RequestParam String type){
       return this.alerteService.findByType(type);
    }

    /**
     * Use the id property of  alerte class to list an alert
     * @return List of alert
     */
    @GetMapping(path = "findAlerteById/{id}")
    public ResponseEntity<AlerteDto> findAlerteById(
            @Parameter(description = "entrez l'ID de l'alerte recherchee")@PathVariable UUID id){
       return ResponseEntity.ok(this.alerteService.findById(id));
    }

    /**
     * Use the id  property of  alerte class to archive an alert
     */

    @PostMapping(path = "archiveAlerte/{id}")
    public void archiveAlerte(
            @Parameter(description = "entrez l'ID de l'alerte a archivee")@PathVariable UUID id){
        this.alerteService.archive(id);
    }

    /**
     * generate an automatic alerte
     */
    @PostMapping(path = "genererAlertesAutomatiques")
    public void genererAlertesAutomatiques(){
        this.alerteService.genererAlertesAutomatiques();
    }







}
