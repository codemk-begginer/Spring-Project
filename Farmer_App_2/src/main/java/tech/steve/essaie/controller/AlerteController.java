package tech.steve.essaie.controller;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AlerteDto;
import tech.steve.essaie.service.AlerteService;
import tech.steve.essaie.service.impl.AlerteServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
public class AlerteController {

    private final AlerteService alerteService;

    @GetMapping(path = "findByStatut")
    public List<AlerteDto> findByStatut(@RequestParam String status){
        return this.alerteService.findByStatut(status);
    }



     @GetMapping(path = "findAlerteByAnimal")
    public List<AlerteDto> findAlerteByAnimal(@RequestParam Long animalId){
        return this.alerteService.findByAnimal(animalId);
    }

    @PostMapping(path = "marquerCommeLue/{id}")
    public void marquerCommeLue(@PathVariable Long id){
        this.alerteService.marquerCommeLue(id);
    }

    @PostMapping(path = "deleteAlerte/{id}")
    public void delete(@PathVariable Long id){
       this.alerteService.supprimer(id);
    }

    @GetMapping(path = "findAllAlerte")
    public List<AlerteDto> findAllAlerte(){
       return this.alerteService.findAll();
    }

    @GetMapping(path = "findNonLue")
    public List<AlerteDto> findNonLue(){
       return this.alerteService.findNonLues();
    }

    @GetMapping(path = "findAlerteByType")
    public List<AlerteDto> findAlerteByType(@RequestParam String type){
       return this.alerteService.findByType(type);
    }

    @GetMapping(path = "findAlerteById/{id}")
    public ResponseEntity<AlerteDto> findAlerteById(@PathVariable Long id){
       return ResponseEntity.ok(this.alerteService.findById(id));
    }

    @PostMapping(path = "archiveAlerte/{id}")
    public void archiveAlerte(@PathVariable Long id){
        this.alerteService.archive(id);
    }

    @PostMapping(path = "genererAlertesAutomatiques")
    public void genererAlertesAutomatiques(){
        this.alerteService.genererAlertesAutomatiques();
    }







}
