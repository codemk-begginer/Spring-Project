package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.animal.AnimalDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.service.AnimalService;
import tech.steve.essaie.service.ExportService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <b>CRUD endpoints for Animal class</b>
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("animal")
public class AnimalController {

    private AnimalService  animalService;
    private final ExportService exportService;

    /**
     * create an animal
     * @param animalDto animalDto
     */
    @PostMapping(path = "createAnimal")
    public void create(@RequestBody AnimalDto animalDto){

        this.animalService.create(animalDto);
        log.info("createAnimal");

    }

    /**
     *use id property of animals class to list animals
     * @param id id
     * @return list of animals
     */
    @GetMapping(path = "findAnimalById/{id}")
    public ResponseEntity<AnimalDto> findAnimalById(@Parameter(description = "ID de l'animal rechercher ") @PathVariable UUID id){
        return ResponseEntity.ok(animalService.findById(id));
    }


    /**
     * use id property of animal class to archive animals
     * @param id id
     */
    @PostMapping(path = "archiveAnimal/{id}")
    public void archiveAnimal(
            @Parameter(description = "ID de l'animal a archivee ")@PathVariable UUID id){
        this.animalService.archive(id);
    }

    /**
     * use fermeId property of animal class to list animal
     * @param fermeId fermeId
     * @return list of animal
     */
    @GetMapping(path = "findAllByFerme/{fermeId}")
    public List<AnimalDto> findAllByFerme(
            @Parameter(description = "ID de la ferme dont on veut recuperee les animaux")@PathVariable UUID fermeId){

        return this.animalService.findAllByFerme(fermeId);
    }

    /**
     * use mereId property of animal class to list animal
     * @param mereId mereId
     * @return list of animal
     */
    @GetMapping(path = "findByMere/{mereId}")
    public List<AnimalDto> findByMere(
            @Parameter(description = "ID de la mere dont on veut recuperee les animaux")@PathVariable Animal mereId){
        return this.animalService.findByMere(mereId);
    }

    /**
     * use pereId property of animal class to list animal
     * @param pereId pereId
     * @return list of animal
     */
    @GetMapping(path = "findByPere/{pereId}")
    public List<AnimalDto> findByPere(
            @Parameter(description = "ID du pere dont on veut recupere les animaux ")@PathVariable Animal pereId){
        return this.animalService.findByPere(pereId);
    }

    /**
     * use sex property animals class to list animal
     * @param sexe sex
     * @return list of animal
     */
    @GetMapping(path = "findBySexe")
    public List<AnimalDto> findBySexe(
            @Parameter(description = "sexe des animaux que l'on veut recuperee")@RequestParam Sexe sexe){
        return this.animalService.findBySexe(sexe);
    }

    /**
     * @return list of all animals
     */
    @GetMapping(path = "findAllAnimal")
    public List<AnimalDto> findAll(){
        return this.animalService.findAll();
    }


    /**
     * use animalId property of animal class and animalDto to update the information of an animal
     * @param animalId animalId
     * @param dto animalDto
     */
    @PutMapping(path = "updateAnimal/{animalId}")
    public void updateAnimal(
            @Parameter(description = "ID de l'animal que l'on veut mettre a jour ")@PathVariable UUID animalId, @RequestBody AnimalDto dto){
        this.animalService.update(animalId,dto);
    }

    /**
     * export animal to Excel file
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping(path = "exportAnimauxToExcel")
    public void exportTransactionToExcel(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=animaux.xlsx");
        this.exportService.exportAnimauxToExcel(response);

        log.info("exportAnimauxToExcel");

    }

    /**
     * export animal to PDF file
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping(path = "exportAnimauxToPdf")
    public void exportTransactionToPdf(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=animaux.pdf");
        this.exportService.exportAnimauxToPdf(response);

        log.info("exportAnimauxToPdf");

    }









}
