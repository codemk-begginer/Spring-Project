package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.service.NaissanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class NaissanceController {
    private final NaissanceService naissanceService;

    @PostMapping(path = "createNaissance")
    public void createNaissance(@RequestBody NaissanceDto dto){
        this.naissanceService.create(dto);
    }

    @PutMapping(path ="updateNaissance/{naissanceId}")
    public void updateNaissance (@PathVariable Long naissanceId, @RequestBody NaissanceDto naissanceDto){
        this.naissanceService.update(naissanceId,naissanceDto);
    }

    @PostMapping(path = "deleteNaissance/{naissanceId}")
    public void deleteNaissance(@PathVariable Long naissanceId){
        this.naissanceService.delete(naissanceId);
    }

    @GetMapping(path = "getAllNaissance")
    public List<NaissanceDto> getAllNaissance(){
        return this.naissanceService.getAll();
    }



    @GetMapping(path = "getNaissanceByCroisementId")
    public List<NaissanceDto> getNaissanceByCroisementId(@RequestParam Long croisementId){
        return this.naissanceService.getByCroisement(croisementId);
    }

    @GetMapping(path = "getNaissanceByDate")
    public List<NaissanceDto> getNaissanceByDate(@RequestParam LocalDate dateNaissance){
        return this.naissanceService.findByDate(dateNaissance);
    }



    @GetMapping(path = "getHistoriqueModificationNaissance")
    public List<HistoriqueModification> getHistoriqueModificationNaissance(@RequestParam Long naissanceId){
        return this.naissanceService.getHistoriqueModifications(naissanceId);
    }



}
