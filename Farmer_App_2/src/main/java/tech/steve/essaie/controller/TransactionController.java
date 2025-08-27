package tech.steve.essaie.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.TransactionDto;
import tech.steve.essaie.enums.TypeTransaction;
import tech.steve.essaie.service.ExportService;
import tech.steve.essaie.service.TransactionService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;
    private ExportService exportService;

    @PostMapping(path = "createTransaction")
    public void createTransaction(@RequestBody TransactionDto transactionDto){
        this.transactionService.create(transactionDto);
        log.info("createTransaction");
    }

    @PutMapping(path="updateTransaction/{id}")
    public void updateTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto){
        this.transactionService.update(id,transactionDto);
    }

    @PostMapping(path = "deleteTransaction/{id}")
    public void deleteTransaction (@PathVariable Long id){
        this.transactionService.delete(id);
    }

    @GetMapping(path = "findAllTransaction")
    List<TransactionDto> findAllTransaction (){

        return this.transactionService.findAll();
    }



    @GetMapping(path = "findTransactionById/{id}")
    public TransactionDto findTransactionById(@PathVariable Long id){
        return this.transactionService.findById(id);
    }

    @GetMapping(path = "findByAnimalId/{id}")
    public List<TransactionDto> findByAnimalId(@PathVariable Long id){

        return this.transactionService.findByAnimalId(id);
    }

    @GetMapping(path = "findTransactionByType")
    public List<TransactionDto> findTransactionByType (@RequestParam TypeTransaction type){

        return this.transactionService.findByType(type);
    }

    @GetMapping(path = "findByDateRange")
    public List<TransactionDto> findByDateRange (@RequestParam LocalDate start, LocalDate end){
        return this.transactionService.findByDateRange(start,end);
    }

  @GetMapping(path = "getTotalTransactionByType")
    public double getTotalTransactionByType (@RequestParam String type){
        return this.transactionService.getTotalByType(type);
    }

  @GetMapping(path = "getTotalTransactionForAnimal")
    public double getTotalTransactionForAnimal (@RequestParam Long id){
        return this.transactionService.getTotalForAnimal(id);
    }

  @GetMapping(path = "getTotalGlobal")
    public double getTotalGlobal (){
       return   this.transactionService.getTotalGlobal();
    }

  @GetMapping(path = "getHistoriqueTransaction/{id}")
  public List<String> getHistoriqueTransaction(@PathVariable Long id){
        return this.transactionService.getHistoriqueTransaction(id);
  }

  @GetMapping(path = "findByIntervenant")
  public List<TransactionDto> findByIntervenant(@RequestParam String nom){
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
