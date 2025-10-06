package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.OperationDto;
import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.service.OperationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * <b>CRUD endPoints for operations class</b>
 */
@RestController
@AllArgsConstructor
@RequestMapping("operation")
public class OperationController {
    private final OperationService operationService;

    /**
     * create an operation
     * @param dto operationDto
     */
    @PostMapping(path = "createOperation")
    public void createOperation(@RequestBody OperationDto dto){
        this.operationService.create(dto);
    }

    /**
     * use id property of operation class and operationDto to update an information in an operation
     * @param id operationId
     * @param dto operationDto
     */
    @PutMapping(path = "updateOperation/{id}")
    public void updateOperation(@Parameter(description = "ID de l'operation a mettre a jour") @PathVariable UUID id, @RequestBody OperationDto dto){
        this.operationService.update(id,dto);
    }

    /**
     * use animalId property of operation class to list operations
     * @param id animalId
     * @return list of operation
     */
    @GetMapping(path = "findOperationByAnimalId/{id}")
    public List<OperationDto> findOperationByAnimalId(
            @Parameter(description = "ID de l'animal liee a l'operation")@PathVariable UUID id){
        return this.operationService.findByAnimal(id);
    }

    /**
     * @return list of all operations
     */
    @GetMapping(path = "findAllOperation")
    public List<OperationDto> findAllOperation(){
        return this.operationService.getAll();
    }

    /**
     * use id property of operation class to delete an operation
     * @param id operationId
     */
    @PostMapping(path = "deleteOperation/{id}")
    public void deleteOperation(@Parameter(description = "ID de l'operation a supprimee")@PathVariable UUID id){
        this.operationService.delete(id);
    }

    /**
     * use date property of operation class to list operations
     * @param start dateStart
     * @param end dateEnd
     * @return list of operation
     */
    @GetMapping(path = "findOperationByDateRange")
    public List<OperationDto> findOperationByDateRange(
            @Parameter(description = "intervale de date dans lequel la recherche sera effectuer")
            @RequestParam LocalDate start, LocalDate end){
        return this.operationService.findByDateRange(start,end);
    }

    /**
     * use type property of operation class to list operations who have this type
     * @param type type
     * @return list of operation
     */
     @GetMapping(path = "findOperationByType")
    public List<OperationDto> findOperationByType(
            @Parameter(description = "Type de l'operation rechercher")@RequestParam TypeOperation type){
        return this.operationService.findByType(type);
    }

    /**
     * use id of operation class to list an operation
     * @param id operationId
     * @return an operation
     */
    @GetMapping(path = "findOperationById/{id}")
    public ResponseEntity<OperationDto> findOperationById(
            @Parameter(description = "ID de l'operation recherchee")@PathVariable UUID id){
        return ResponseEntity.ok(this.operationService.findById(id));
    }


    /**
     * use a keyword to list operations who contain this keyword
     * @param keyword operationKeyword
     * @return list of operation who contains this keyword
     */
    @GetMapping(path = "searchOperationByKeyword")
    public List<OperationDto> searchOperationByKeyword(
            @Parameter(description = "Mot cle du produit dont on recherche l'operation")@RequestParam String keyword){
       return this.operationService.searchByKeyword(keyword);
    }


    /**
     * use id property of operations class to list historical changes of operations
     * @param id operationId
     * @return list of historical changes of operations
     */
    @GetMapping(path = "getHistoriqueOfOperation/{id}")
    public List<HistoriqueModification> getHistoriqueOfOperation(
            @Parameter(description = "ID de l'operation dont on veut obtenir l'historique")@PathVariable UUID id){
       return this.operationService.getHistoriqueModifications(id);
    }



}
