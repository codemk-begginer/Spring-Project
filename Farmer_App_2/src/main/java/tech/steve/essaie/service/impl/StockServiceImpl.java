package tech.steve.essaie.service.impl;

import tech.steve.essaie.dto.StockDto;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.StockMapper;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.Stock;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.repository.StockRepository;
import tech.steve.essaie.service.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {

    private final FermeRepository fermeRepository;
    private final StockRepository repository;
    private final StockMapper mapper;

    @Override
    public StockDto create(StockDto dto) {

        // Gestion de la ferme
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec id : " + dto.getFermeId()));


        Stock stock = mapper.toEntity(dto);
        stock.setHorodatage(LocalDate.now());
        stock.setSeuilAlerte(1);
        stock.setTypeMouvement("ENTREE");
        stock.setFerme(ferme);
        return mapper.toDto(repository.save(stock));
    }

    @Override
    public StockDto update(Long id, StockDto dto) {
        Stock existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Stock non trouver avec id", id));

        existing.setHorodatage(LocalDate.now());
        existing.setTypeMouvement(dto.getTypeMouvement());
        existing.setProduit(dto.getProduit());
        existing.setDescription(dto.getDescription());
        existing.setQuantite(dto.getQuantite());
        existing.setFournisseur(dto.getFournisseur());
        existing.setUnite(dto.getUnite());
        existing.setCategorie(dto.getCategorie());
        existing.setNomIntervenant(dto.getNomIntervenant());
        dto.setTelephoneIntervenant(dto.getTelephoneIntervenant());

        if (dto.getFermeId() != null) {
            Ferme ferme = fermeRepository.findById(dto.getFermeId())
                    .orElseThrow(() -> new NotFoundException("ferme non trouvé : " + dto.getFermeId()));
            existing.setFerme(ferme);
        }
        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Stock", id);
        repository.deleteById(id);

    }

    @Override
    public StockDto findById(Long id) {
                return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Stock", id));

    }

    @Override
    public List<StockDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public List<StockDto> findByType(String type) {
        return List.of();
    }

    @Override
    public List<StockDto> findByProduit(String produit) {
        return mapper.toDtoList(repository.findByProduitIgnoreCase(produit));
    }

    @Override
    public List<StockDto> getStockCritique() {
        return List.of();
    }

    @Override
    public int getQuantiteTotaleByProduit(String produit) {
        return 0;
    }

    @Override
    public List<StockDto> findByCategorie(String categorie) {
        return mapper.toDtoList(repository.findByCategorieIgnoreCase(categorie));
    }

    @Override
    public List<StockDto> findByDateRange(LocalDate debut, LocalDate fin) {
        return mapper.toDtoList(repository.findByHorodatageBetween(debut, fin));
    }

    @Override
    public List<Long> getStockActuel(String produit) {

        return repository.findQuantiteByProduit(produit);
    }

    @Override
    public boolean isEnDessousSeuil(String produit) {
        List<Stock> entries = repository.findByProduitIgnoreCase(produit);
        if (entries.isEmpty()) return false;
        Integer seuil = entries.get(0).getSeuilAlerte();
        return getStockActuel(produit).get(0) < seuil;
    }


    @Override
    public List<StockDto> getAlertesSeuil() {
        return repository.findAll().stream()
                .filter(stock -> stock.getSeuilAlerte() != 0 && getStockActuel(stock.getProduit()).get(0) < stock.getSeuilAlerte())
                .map(mapper::toDto)
                .toList();
    }

//    @Override
//    public boolean isEnDessousSeuil(String produit) {
//        List<Long> entries = repository.findQuantite(produit);
//        if (entries.equals(0L)) return false;
//        Integer seuil = entries.get(0).getSeuilAlerte();
//        return seuil != null && getStockActuel(produit) < seuil;
//    }











//

//

//
//    @Override
//    public List<StockDto> getStockCritique() {
//        return List.of();
//    }
//




//

//

//

}
