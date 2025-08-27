package tech.steve.essaie.service;

import tech.steve.essaie.dto.StockDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockService {
    StockDto create(StockDto dto);
    StockDto update(Long id, StockDto dto);
    void delete(Long id);
    StockDto findById(Long id);
    List<StockDto> findAll();
    List<StockDto> findByType(String type);
    List<StockDto> findByProduit(String produit);
    List<StockDto> getStockCritique();
    int getQuantiteTotaleByProduit(String produit);

    public List<StockDto> findByCategorie(String categorie);
    public List<StockDto> findByDateRange(LocalDate debut, LocalDate fin);
    public List<Long> getStockActuel(String produit);
    public boolean isEnDessousSeuil(String produit);
    public List<StockDto> getAlertesSeuil();
}
