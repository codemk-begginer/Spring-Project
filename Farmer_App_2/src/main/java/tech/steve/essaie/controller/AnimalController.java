package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.FermeDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.service.AnimalService;
import tech.steve.essaie.service.FermeService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class AnimalController {

    private AnimalService  animalService;

    @PostMapping(path = "createAnimal")
    public void create(@RequestBody AnimalDto animalDto){

        this.animalService.create(animalDto);
        log.info("createAnimal");

    }

    @GetMapping(path = "findAnimalById/{id}")
    public ResponseEntity<AnimalDto> findAnimalById(@PathVariable Long id){
        return ResponseEntity.ok(animalService.findById(id));
    }


    @PostMapping(path = "archiveAnimal/{id}")
    public void archiveAnimal(@PathVariable Long id){
        this.animalService.archive(id);
    }

    @GetMapping(path = "findAllByFerme/{fermeId}")
    public List<AnimalDto> findAllByFerme(@PathVariable Long fermeId){

        return this.animalService.findAllByFerme(fermeId);
    }

    @GetMapping(path = "findByMere/{mereId}")
    public List<AnimalDto> findByMere(@PathVariable Animal mereId){
        return this.animalService.findByMere(mereId);
    }

    @GetMapping(path = "findByPere/{pereId}")
    public List<AnimalDto> findByPere(@PathVariable Animal pereId){
        return this.animalService.findByPere(pereId);
    }

    @GetMapping(path = "findBySexe")
    public List<AnimalDto> findBySexe(@RequestParam Sexe sexe){
        return this.animalService.findBySexe(sexe);
    }










}
