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
import java.util.UUID;

/**
 * <b>CRUD endpoints for birth</b>
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("naissance")
public class NaissanceController {
    private final NaissanceService naissanceService;
    private final ExportService exportService;

    /**
     * create a birth
     * @param dto naissaneDto
     */
    @PostMapping(path = "createNaissance")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "IL faut supprimee le champ ID lors de l'envoie de donnee car il est generee automatiquement ")
    public void createNaissance(@RequestBody NaissanceDto dto){
        this.naissanceService.create(dto);
    }

    /**
     * use naissanceId property and naissanceDto to update an information in birth
     * @param naissanceId naissanceId
     * @param naissanceDto naissanceDto
     */
    @PutMapping(path ="updateNaissance/{naissanceId}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "IL faut supprimee le champ ID lors de l'envoie de donnee car il ne doit pas etre modifier")
    public void updateNaissance (@PathVariable UUID naissanceId, @RequestBody NaissanceDto naissanceDto){
        this.naissanceService.update(naissanceId,naissanceDto);
    }

    /**
     * use naissanceId property of naissance class to delete a birth
     * @param naissanceId naissanceId
     */
    @PostMapping(path = "deleteNaissance/{naissanceId}")
    public void deleteNaissance(@PathVariable UUID naissanceId){
        this.naissanceService.delete(naissanceId);
    }

    /**
     * @return list of all naissance
     */
    @GetMapping(path = "getAllNaissance")
    public List<NaissanceDto> getAllNaissance(){
        return this.naissanceService.getAll();
    }


    /**
     * use croisementId of naissance class to list a naissance
     * @param croisementId croisementId
     * @return list of naissance
     */
    @GetMapping(path = "getNaissanceByCroisementId")
    public List<NaissanceDto> getNaissanceByCroisementId(@RequestParam UUID croisementId){
        return this.naissanceService.getByCroisement(croisementId);
    }

    /**
     * use dateNaissance property of naissance class to list a birth
     * @param dateNaissance dateNaissance
     * @return list of naissance
     */
    @GetMapping(path = "getNaissanceByDate")
    public List<NaissanceDto> getNaissanceByDate(@RequestParam LocalDate dateNaissance){
        return this.naissanceService.findByDate(dateNaissance);
    }


    /**
     * use naissanceId property to list historical of changes for a naissance
     * @param naissanceId naissanceId
     * @return historical of change for a naissance
     */
    @GetMapping(path = "getHistoriqueModificationNaissance")
    public List<HistoriqueModification> getHistoriqueModificationNaissance(@RequestParam UUID naissanceId){
        return this.naissanceService.getHistoriqueModifications(naissanceId);
    }

    /**
     * eport births to Excel file
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping(path = "exportNaissanceToExcel")
    public void exportTransactionToExcel(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=naissance.xlsx");
        this.exportService.exportNaissancesToExcel(response);

        log.info("exportNaissanceToExcel");

    }

    /**
     * eport births to PDF file
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping(path = "exportNaissanceToPdf")
    public void exportTransactionToPdf(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=naissance.pdf");
        this.exportService.exportNaissancesToPdf(response);

        log.info("exportNaissanceToPdf");

    }



}
