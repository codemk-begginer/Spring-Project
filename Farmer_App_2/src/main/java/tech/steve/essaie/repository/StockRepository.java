package tech.steve.essaie.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.steve.essaie.dto.StockDto;
import tech.steve.essaie.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByFermeId(Long fermeId);

    //List<Stock> findByNomContainingIgnoreCaseAndFermeId(String nom, Long fermeId);
    List<Stock> findByProduitIgnoreCase(String nom);
    List<Stock> findByCategorieIgnoreCase(String categorie);
    List<Stock> findByHorodatageBetween(LocalDate debut, LocalDate fin);
    //List<Stock> findByAction(String action);

    // 1. Quantité totale d'un produit en stock
    // Cette requête calcule la somme des entrées moins la somme des sorties
    @Query("SELECT (SUM(CASE WHEN s.typeMouvement = 'ENTREE' THEN s.quantite ELSE 0 END) - SUM(CASE WHEN s.typeMouvement = 'SORTIE' THEN s.quantite ELSE 0 END)) FROM Stock s WHERE s.produit = :produit")
    List<Long> findQuantiteByProduit(@Param("produit") String produit);

    @Query("SELECT (SUM(CASE WHEN s.typeMouvement = 'ENTREE' THEN s.quantite ELSE 0 END) - SUM(CASE WHEN s.typeMouvement = 'SORTIE' THEN s.quantite ELSE 0 END)) FROM Stock s WHERE s.produit = :produit")
    List<Long> findQuantite(@Param("produit") String produit);


//    // 2. Somme des entrées en stock
//    @Query("SELECT SUM(p.quantite) FROM Produit p WHERE p.nomProduit = :nom AND p.typeMouvement = 'ENTREE'")
//    Optional<Long> findTotalEntreeByNomProduit(@Param("nom") String nomProduit);
//
//
//    // 3. Somme des sorties de stock
//    @Query("SELECT SUM(p.quantite) FROM Produit p WHERE p.nomProduit = :nom AND p.typeMouvement = 'SORTIE'")
//    Optional<Long> findTotalSortieByNomProduit(@Param("nom") String nomProduit);


//    Optional<Stock> findByIdAndFermeId(Long id, Long fermeId);
}

