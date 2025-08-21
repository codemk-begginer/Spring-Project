package tech.steve.essaie.service.impl;

import tech.steve.essaie.dto.TransactionDto;
import tech.steve.essaie.enums.TypeTransaction;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.TransactionMapper;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.Transaction;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.repository.TransactionRepository;
import tech.steve.essaie.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final FermeRepository fermeRepository;
    private final AnimalRepository animalRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionDto create(TransactionDto dto) {
        Transaction transaction = transactionMapper.toEntity(dto);

        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new NotFoundException("Animal", dto.getAnimalId()));
            transaction.setAnimal(animal);
        }else {
            transaction.setAnimal(null);
        }

        // Gestion de la ferme
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec id : " + dto.getFermeId()));

        transaction.setFerme(ferme);
        transaction.setDate(LocalDate.now());


        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDto update(Long id, TransactionDto dto) {
                Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction", id));

        existing.setType(dto.getType());
        existing.setCategorie(dto.getCategorie());
        existing.setMontant(dto.getMontant());
        existing.setDescription(dto.getDescription());
        existing.setDate(LocalDate.now());
        existing.setNomIntervenant(dto.getNomIntervenant());
        existing.setTelephoneIntervenant(dto.getTelephoneIntervenant());

        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new NotFoundException("Animal", dto.getAnimalId()));
            existing.setAnimal(animal);
        } else {
            existing.setAnimal(null);
        }

        return transactionMapper.toDto(transactionRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionDto> findAll() {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .map(transactionMapper::toDto)
                .toList();
    }



    @Override
    public TransactionDto findById(Long id) {
                return transactionRepository.findById(id)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Transaction", id));
    }



    @Override
    public List<TransactionDto> findByAnimalId(Long animalId) {
                return transactionRepository.findByAnimalId(animalId).stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    @Override
    public List<TransactionDto> findByType(TypeTransaction type) {
                return transactionRepository.findByType(type).stream()
                .map(transactionMapper::toDto)
                .toList();
    }

        @Override
    public List<TransactionDto> findByDateRange(LocalDate startDate, LocalDate endDate) {

        return transactionRepository.findByDateBetween(startDate, endDate).stream()
                .map(transactionMapper::toDto)
                .toList();
    }

        @Override
    public double getTotalByType(String type) {
                return transactionRepository.findByType(TypeTransaction.valueOf(type.toUpperCase())).stream()
                .map(Transaction::getMontant)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }

        @Override
    public double getTotalForAnimal(Long animalId) {
                return transactionRepository.findByAnimalId(animalId).stream()
                .map(Transaction::getMontant)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }


    @Override
    public double getTotalGlobal() {
                return transactionRepository.findAll().stream()
                .map(Transaction::getMontant)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }

    @Override
    public List<String> getHistoriqueTransaction(Long transactionId) {
                Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction", transactionId));
        return List.of("Créée par : " + transaction.getNomIntervenant(),
                "Montant : " + transaction.getMontant() + "€",
                "Date : " + transaction.getDate(),
                "Catégorie : " + transaction.getCategorie());
    }

    @Override
    public List<TransactionDto> findByIntervenant(String intervenant) {
        return transactionRepository.findByNomIntervenantIgnoreCase(intervenant).stream()
                .map(transactionMapper::toDto)
                .toList();
    }


}