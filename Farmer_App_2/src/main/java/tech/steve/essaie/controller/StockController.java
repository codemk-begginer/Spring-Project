package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.StockDto;
import tech.steve.essaie.dto.UtilisateurDto;
import tech.steve.essaie.service.StockService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class StockController {
    private StockService stockService;

    @PostMapping(path = "createStock")
    public void create(@RequestBody StockDto stockDto){

        this.stockService.create(stockDto);

        log.info("createStock");
    }

    @PutMapping(path = "/updateStock/{id}")
    public ResponseEntity<StockDto> update(@PathVariable Long id, @RequestBody  StockDto stockDto){
        log.info("updateStock");
        return ResponseEntity.ok(stockService.update(id, stockDto));

    }

    @PostMapping(path = "/deleteStock/{id}")
    public void delete(@PathVariable Long id){

        this.stockService.delete(id);
        log.info("stock deleted");
    }

    @GetMapping(path = "/findStockById/{id}")
    public ResponseEntity<StockDto> findStockById(@PathVariable Long id){
        log.info("findStockById");
        return ResponseEntity.ok(this.stockService.findById(id));
    }

    @GetMapping(path = "findAll")
    public List<StockDto> findAll(){
        log.info("findAll");
        return this.stockService.findAll();
    }

    @GetMapping(path = "findByProduit")
    public List<StockDto> findByProduit(@RequestParam String produit){
        log.info("findByProduit");
        return this.stockService.findByProduit(produit);
    }

        @GetMapping(path = "findByCategorie")
    public List<StockDto> findByCategorie(@RequestParam String categorie){
        log.info("findByCategorie");
        return this.stockService.findByCategorie(categorie);
    }


    @GetMapping(path = "findByDateBetween")
    public List<StockDto> findByDateBetween(@RequestParam LocalDate debut, LocalDate fin){
        log.info("findByDateBetween");
        return this.stockService.findByDateRange(debut,fin);
    }

    @GetMapping(path = "getStockActuel")
    public List<Long> getStockActuel(@RequestParam String produit){
        log.info("getStockActuel");
        return this.stockService.getStockActuel(produit) ;
    }
       @GetMapping(path = "isEnDessousSeuil")
    public boolean isEnDessousSeuil(@RequestParam String produit){
        log.info("isEnDessousSeuil");
        return this.stockService.isEnDessousSeuil(produit) ;
    }

    @GetMapping(path = "getAlertesSeuil")
    public List<StockDto> getAlertesSeuil(){
        log.info("getAlertesSeuil");
        return this.stockService.getAlertesSeuil() ;
    }








}
