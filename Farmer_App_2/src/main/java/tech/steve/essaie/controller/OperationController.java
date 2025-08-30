package tech.steve.essaie.controller;

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

@RestController
@AllArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @PostMapping(path = "createOperation")
    public void createOperation(@RequestBody OperationDto dto){
        this.operationService.create(dto);
    }

    @PutMapping(path = "updateOperation/{id}")
    public void updateOperation(@PathVariable Long id, @RequestBody OperationDto dto){
        this.operationService.update(id,dto);
    }

    @GetMapping(path = "findOperationByAnimalId/{id}")
    public List<OperationDto> findOperationByAnimalId(@PathVariable Long id){
        return this.operationService.findByAnimal(id);
    }

    @GetMapping(path = "findAllOperation")
    public List<OperationDto> findAllOperation(){
        return this.operationService.getAll();
    }

    @PostMapping(path = "deleteOperation/{id}")
    public void deleteOperation(@PathVariable Long id){
        this.operationService.delete(id);
    }

    @GetMapping(path = "findOperationByDateRange")
    public List<OperationDto> findOperationByDateRange(@RequestParam LocalDate start, LocalDate end){
        return this.operationService.findByDateRange(start,end);
    }

     @GetMapping(path = "findOperationByType")
    public List<OperationDto> findOperationByType(@RequestParam TypeOperation type){
        return this.operationService.findByType(type);
    }

    @GetMapping(path = "findOperationById/{id}")
    public ResponseEntity<OperationDto> findOperationById(@PathVariable Long id){
        return ResponseEntity.ok(this.operationService.findById(id));
    }

    @GetMapping(path = "searchOperationByKeyword")
    public List<OperationDto> searchOperationByKeyword(@RequestParam String keyword){
       return this.operationService.searchByKeyword(keyword);
    }

    @GetMapping(path = "getHistoriqueOfOperation/{id}")
    public List<HistoriqueModification> getHistoriqueOfOperation(@PathVariable Long id){
       return this.operationService.getHistoriqueModifications(id);
    }



}
