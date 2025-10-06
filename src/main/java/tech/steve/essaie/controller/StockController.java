package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.StockDto;
import tech.steve.essaie.service.StockService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *<b> CRUD endpoints of stocks class</b>
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("stock")
public class StockController {
    private StockService stockService;

    /**
     * use stockDto to create a stock
     * @param stockDto stockDto
     */
    @PostMapping(path = "createStock")
    public void create(@RequestBody StockDto stockDto){

        this.stockService.create(stockDto);

        log.info("createStock");
    }

    /**
     * use id property of stock class and stockDto to update a stock
     * @param id stockId
     * @param stockDto stockDto
     * @return  updated stock
     */
    @PutMapping(path = "/updateStock/{id}")
    public ResponseEntity<StockDto> update(@Parameter(description = "ID du stock a mettre a jour ") @PathVariable UUID id, @RequestBody  StockDto stockDto){
        log.info("updateStock");
        return ResponseEntity.ok(stockService.update(id, stockDto));

    }

    /**
     * use id property of stock class to delete a stock
     * @param id stockId
     */
    @PostMapping(path = "/deleteStock/{id}")
    public void delete(@Parameter(description = "ID du stock a supprimer ") @PathVariable UUID id){

        this.stockService.delete(id);
        log.info("stock deleted");
    }


    /**
     * use id property of stock class to list a stock who have this id
     * @param id stockId
     * @return list of a stock who have this id
     */
    @GetMapping(path = "/findStockById/{id}")
    public ResponseEntity<StockDto> findStockById(@Parameter(description = "ID du stock recherchee ") @PathVariable UUID id){
        log.info("findStockById");
        return ResponseEntity.ok(this.stockService.findById(id));
    }

    /**
     * @return list of all stock
     */
    @GetMapping(path = "findAll")
    public List<StockDto> findAll(){
        log.info("findAll");
        return this.stockService.findAll();
    }


    /**
     * use product property of stock class to list stock
     * @param produit product
     * @return list of stock who contain this product
     */
    @GetMapping(path = "findByProduit")
    public List<StockDto> findByProduit(@Parameter(description = "nom du produit pour rechercher le stock ") @RequestParam String produit){
        log.info("findByProduit");
        return this.stockService.findByProduit(produit);
    }

    /**
     * use categorie property of stock class to list stocks who have this categorie
     * @param categorie categorie
     * @return list of stock who have this cathegorie
     */
    @GetMapping(path = "findByCategorie")
    public List<StockDto> findByCategorie(@Parameter(description = "nom de la cathegorie pour rechercher le stock  ") @RequestParam String categorie){
        log.info("findByCategorie");
        return this.stockService.findByCategorie(categorie);
    }


    /**
     * use date property of stock class to list stocks
     * @param debut dateStart
     * @param fin dateEnd
     * @return list of stock
     */
    @GetMapping(path = "findByDateBetween")
    public List<StockDto> findByDateBetween(@Parameter(description = "Intervale de date pour effectuer la recherche du stock ") @RequestParam LocalDate debut, LocalDate fin){
        log.info("findByDateBetween");
        return this.stockService.findByDateRange(debut,fin);
    }

    /**
     * list Quantity available for the product whose name is passed as a parameter
     * @param produit productName
     * @return Quantity available for the product whose name is passed as a parameter
     */
    @GetMapping(path = "getStockActuel")
    public List<Long> getStockActuel(@Parameter(description = "nom du produit dont on veut connaitre le stock actuel ") @RequestParam String produit){
        log.info("getStockActuel");
        return this.stockService.getStockActuel(produit) ;
    }

    /**
     * Check if a product available in stock is below the threshold or not
     * @param produit productName
     * @return true or false
     */
    @GetMapping(path = "isEnDessousSeuil")
    public boolean isEnDessousSeuil(@Parameter(description = "nom du produit que l'on veut verifier ") @RequestParam String produit){
        log.info("isEnDessousSeuil");
        return this.stockService.isEnDessousSeuil(produit) ;
    }

    /**
     * displays the alert threshold of a product in stock
     * @return the alert threshold of a product in stock
     */
    @GetMapping(path = "getAlertesSeuil")
    public List<StockDto> getAlertesSeuil(){
        log.info("getAlertesSeuil");
        return this.stockService.getAlertesSeuil() ;
    }








}
