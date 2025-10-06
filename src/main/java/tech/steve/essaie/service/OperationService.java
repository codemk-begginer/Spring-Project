package tech.steve.essaie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.steve.essaie.dto.OperationDto;
import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.model.HistoriqueModification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OperationService {
    OperationDto create(OperationDto dto);
    OperationDto update(UUID id, OperationDto dto);

    List<OperationDto> searchByKeyword(String keyword);

    void archive(UUID id);
    OperationDto findById(UUID id);

    List<OperationDto> findByAnimal(UUID animalId);


    List<OperationDto> findByType(TypeOperation type);



    List<OperationDto> findByDateRange(LocalDate startDate, LocalDate endDate);

    double getTotalCoutByType(String type);
    List<HistoriqueModification> getHistoriqueModifications(UUID operationId);

    void delete(UUID id);

    //
    //
    //    @Override
    //    public OperationDto getById(UUID id) {
    //        Operation operation = operationRepository.findById(id)
    //                .orElseThrow(() -> new NotFoundException("Opération introuvable avec l'ID " + id));
    //        return operationMapper.toDto(operation);
    //    }
    //
    List<OperationDto> getAll();
}
