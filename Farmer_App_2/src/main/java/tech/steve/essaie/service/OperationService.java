package tech.steve.essaie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.steve.essaie.dto.OperationDto;
import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.model.HistoriqueModification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OperationService {
    OperationDto create(OperationDto dto);
    OperationDto update(Long id, OperationDto dto);

    List<OperationDto> searchByKeyword(String keyword);

    void archive(Long id);
    OperationDto findById(Long id);

    List<OperationDto> findByAnimal(Long animalId);


    List<OperationDto> findByType(TypeOperation type);



    List<OperationDto> findByDateRange(LocalDate startDate, LocalDate endDate);

    double getTotalCoutByType(String type);
    List<HistoriqueModification> getHistoriqueModifications(Long operationId);

    void delete(Long id);

    //
    //
    //    @Override
    //    public OperationDto getById(Long id) {
    //        Operation operation = operationRepository.findById(id)
    //                .orElseThrow(() -> new NotFoundException("Opération introuvable avec l'ID " + id));
    //        return operationMapper.toDto(operation);
    //    }
    //
    List<OperationDto> getAll();
}
