package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.TransactionDto;
import tech.steve.essaie.enums.TypeTransaction;
import tech.steve.essaie.service.ExportService;
import tech.steve.essaie.service.TransactionService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("transaction")
public class TransactionController {
    private TransactionService transactionService;
    private ExportService exportService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @PostMapping(path = "createTransaction")
    public void createTransaction(@RequestBody TransactionDto transactionDto){
        this.transactionService.create(transactionDto);
        log.info("createTransaction");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @PutMapping(path="updateTransaction/{id}")
    public void updateTransaction
            (@Parameter(description = "ID de la transaction a mettre a jour ") @PathVariable UUID id, @RequestBody TransactionDto transactionDto){
        this.transactionService.update(id,transactionDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @PostMapping(path = "deleteTransaction/{id}")
    public void deleteTransaction (@Parameter(description = "ID de la transaction a supprimee ")@PathVariable UUID id){
        this.transactionService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "findAllTransaction")
    List<TransactionDto> findAllTransaction (){

        return this.transactionService.findAll();
    }



    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "findTransactionById/{id}")
    public TransactionDto findTransactionById(@Parameter(description = "ID de la transaction a rechercher ")@PathVariable UUID id){
        return this.transactionService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "findByAnimalId/{id}")
    public List<TransactionDto> findByAnimalId(@Parameter(description = "ID de l'animal liee a la transaction recherchee")@PathVariable UUID id){

        return this.transactionService.findByAnimalId(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "findTransactionByType")
    public List<TransactionDto> findTransactionByType (@Parameter(description = "Type de transaction rehercher ")@RequestParam TypeTransaction type){

        return this.transactionService.findByType(type);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
    @GetMapping(path = "findByDateRange")
    public List<TransactionDto> findByDateRange
            (@Parameter(description = "intervale de date dans lequel la recherche sera effectuer ")@RequestParam LocalDate start, LocalDate end){
        return this.transactionService.findByDateRange(start,end);
    }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
  @GetMapping(path = "getTotalTransactionByType")
    public double getTotalTransactionByType
          (@Parameter(description = "Type de transaction dont on recherche le total")@RequestParam String type){
        return this.transactionService.getTotalByType(type);
    }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
  @GetMapping(path = "getTotalTransactionForAnimal")
    public double getTotalTransactionForAnimal
          (@Parameter(description = "ID de l'animal pour lequel on recherche le total des transaction")@RequestParam UUID id){
        return this.transactionService.getTotalForAnimal(id);
    }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
  @GetMapping(path = "getTotalGlobal")
    public double getTotalGlobal (){
       return   this.transactionService.getTotalGlobal();
    }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
  @GetMapping(path = "getHistoriqueTransaction/{id}")
  public List<String> getHistoriqueTransaction(@Parameter(description = "ID de la transaction pour la quelle on recherche l'historique")@PathVariable UUID id){
        return this.transactionService.getHistoriqueTransaction(id);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATEUR','ROLE_MANAGER')")
  @GetMapping(path = "findByIntervenant")
  public List<TransactionDto> findByIntervenant(@Parameter(description = "Nom de l'intervenant liee a la transaction ")@RequestParam String nom){
        return this.transactionService.findByIntervenant(nom);
  }



    @GetMapping(path = "exportTransactionToExcel")
    public void exportTransactionToExcel(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=transaction.xlsx");
        this.exportService.exportTransactionsToExcel(response);

        log.info("exportTransactionToExcel");

    }

  @GetMapping(path = "exportTransactionToPdf")
    public void exportTransactionToPdf(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment;filename=transaction.pdf");
        this.exportService.exportTransactionsToPdf(response);

        log.info("exportTransactionToPdf");

    }


}
