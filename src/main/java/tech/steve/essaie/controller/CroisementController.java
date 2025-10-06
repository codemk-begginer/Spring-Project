package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.CroisementDto;
import tech.steve.essaie.dto.CroisementHistoriqueDto;
import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.service.CroisementService;

import java.util.List;
import java.util.UUID;

/**
 * <b>CRUD endpoints for Croisement class</b>
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("croisement")
public class CroisementController {

    private CroisementService croisementService;

    /**
     * create a croisement
     * @param dto croisementDto
     */
    @PostMapping("createCroisement")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "IL faut supprimee le champ ID lors de l'envoie de donnee car il est generee automatiquement ")
    public void createCroisement (@RequestBody CroisementDto dto){
        this.croisementService.create(dto);
        log.info("createCroisement");
    }

    /**
     * use id property of croisement class to update a croisement
     * @param id croisementId
     * @param dto croisementDto
     */
    @PutMapping(path = "updateCroisement/{id}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "IL faut supprimee le champ ID lors de l'envoie de donnee car il ne seras pas mis a jour ")
    public void updateCroisement(@Parameter(description = "ID du croisement a mettre a jour") @PathVariable UUID id, @RequestBody CroisementDto dto){

        log.info("updateCroisement");
        this.croisementService.update(id,dto);
    }

    /**
     * use id property of croisement class to update an information in a croisement
     * @param id croisementId
     */
    @PostMapping(path = "deleteCroisement/{id}")
    public void deleteCroisement(@Parameter(description = "ID du croisement a supprimee")@PathVariable UUID id){
        this.croisementService.delete(id);
    }


    /**
     * use id property of croisement class to list a croisement
     * @param id croisementId
     * @return list of croisement
     */
    @GetMapping(path = "findCroisementById/{id}")
    public ResponseEntity<CroisementDto> findCroisementById(
            @Parameter(description = "ID du croisement recherchee")@PathVariable UUID id){
        return ResponseEntity.ok(this.croisementService.findById(id));
    }

    /**
     * @return list of all crossing
     */
    @GetMapping(path = "findAllCroisement")
    public List<CroisementDto> findAllCroisement(){
        return this.croisementService.findAll();
    }

    /**
     * use pereId property of crossing class to list a crossing
     * @param pereId pereId
     * @return list of crossing
     */
    @GetMapping(path = "findCroisementByPere")
    public List<CroisementDto> findCroisementByPere(
            @Parameter(description = "ID du pere relier au croisemet rechercher")@RequestParam UUID pereId){
        return this.croisementService.findByPere(pereId);
    }

    /**
     * use mereId property of crossing class to list a crossing
     * @param mereId mereId
     * @return list of crossing
     */
    @GetMapping(path = "findCroisementByMere")
    public List<CroisementDto> findCroisementByMere(
            @Parameter(description = "ID de la mere relier au croisement recherchee")@RequestParam UUID mereId){
        return this.croisementService.findByMere(mereId);
    }

    /**
     * use status property of crossing class to list crossing
     * @param status status
     * @return list of croisement
     */
    @GetMapping(path = "findCroisementByStatus")
    public List<CroisementDto> findCroisementByStatus(
               @Parameter(description = "status du croisement recherchee")@RequestParam StatutGestation status){
        return this.croisementService.findByStatus(status);
    }

    /**
     * use id property of animal class to list historical of animal crossing
     * @param idAnimal idAnimal
     * @return historical of crossing
     */
    @GetMapping(path = "getHistoriqueCroisementsParAnimal/{idAnimal}")
    public List<CroisementHistoriqueDto> getHistoriqueCroisementsParAnimal(
                @Parameter(description = "ID de l'animal dont on veut connaitre l'historique de croisement")@PathVariable UUID idAnimal){
        return this.croisementService.getHistoriqueCroisementsParAnimal(idAnimal);
    }

    /**
     * use id property of animal class to list historical of changes for animal crossing
     * @param idAnimal idAnimal
     * @return historical of changes
     */
    @GetMapping(path = "getHistoriqueModifications/{idAnimal}")
    public List<HistoriqueModification> getHistoriqueModifications(
                  @Parameter(description = "ID de l'animal dont on veut connaitre l'historique de modification")@PathVariable UUID idAnimal){
        return this.croisementService.getHistoriqueModifications(idAnimal);
    }


}
