package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.FermeDto;
import tech.steve.essaie.service.AnimalService;
import tech.steve.essaie.service.FermeService;

@Slf4j
@AllArgsConstructor
@RestController
public class AnimalController {

    private AnimalService  animalService;

    @PostMapping(path = "createAnimal")
    public void create(@RequestBody AnimalDto animalDto){
        log.info("create");

        this.animalService.create(animalDto);

    }
}
