package tech.steve.essaie.controller;

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

@RestController
@AllArgsConstructor
@Slf4j
public class CroisementController {

    private CroisementService croisementService;

    @PostMapping("createCroisement")
    public void createCroisement (@RequestBody CroisementDto dto){
        this.croisementService.create(dto);
        log.info("createCroisement");
    }

    @PutMapping(path = "updateCroisement/{id}")
    public void updateCroisement(@PathVariable Long id, @RequestBody CroisementDto dto){

        log.info("updateCroisement");
        this.croisementService.update(id,dto);
    }

    @PostMapping(path = "deleteCroisement/{id}")
    public void deleteCroisement(@PathVariable Long id){
        this.croisementService.delete(id);
    }


    @GetMapping(path = "findCroisementById/{id}")
    public ResponseEntity<CroisementDto> findCroisementById(@PathVariable Long id){
        return ResponseEntity.ok(this.croisementService.findById(id));
    }

    @GetMapping(path = "findAllCroisement")
    public List<CroisementDto> findAllCroisement(){
        return this.croisementService.findAll();
    }

    @GetMapping(path = "findCroisementByPere")
    public List<CroisementDto> findCroisementByPere(@RequestParam Long pereId){
        return this.croisementService.findByPere(pereId);
    }

    @GetMapping(path = "findCroisementByMere")
    public List<CroisementDto> findCroisementByMere(@RequestParam Long mereId){
        return this.croisementService.findByMere(mereId);
    }

       @GetMapping(path = "findCroisementByStatus")
    public List<CroisementDto> findCroisementByStatus(@RequestParam StatutGestation status){
        return this.croisementService.findByStatus(status);
    }

        @GetMapping(path = "getHistoriqueCroisementsParAnimal/{idAnimal}")
    public List<CroisementHistoriqueDto> getHistoriqueCroisementsParAnimal(@PathVariable Long idAnimal){
        return this.croisementService.getHistoriqueCroisementsParAnimal(idAnimal);
    }

          @GetMapping(path = "getHistoriqueModifications/{idAnimal}")
    public List<HistoriqueModification> getHistoriqueModifications(@PathVariable Long idAnimal){
        return this.croisementService.getHistoriqueModifications(idAnimal);
    }


}
