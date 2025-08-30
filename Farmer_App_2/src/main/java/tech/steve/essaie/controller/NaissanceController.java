package tech.steve.essaie.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.service.ExportService;
import tech.steve.essaie.service.NaissanceService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class NaissanceController {
    private final NaissanceService naissanceService;
    private final ExportService exportService;

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

    @GetMapping(path = "exportNaissanceToExcel")
    public void exportTransactionToExcel(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=naissance.xlsx");
        this.exportService.exportNaissancesToExcel(response);

        log.info("exportNaissanceToExcel");

    }

    @GetMapping(path = "exportNaissanceToPdf")
    public void exportTransactionToPdf(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=naissance.pdf");
        this.exportService.exportNaissancesToPdf(response);

        log.info("exportNaissanceToPdf");

    }



}
