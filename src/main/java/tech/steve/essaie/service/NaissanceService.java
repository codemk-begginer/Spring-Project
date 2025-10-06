package tech.steve.essaie.service;


import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.HistoriqueModification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface NaissanceService {
    public NaissanceDto create(NaissanceDto dto);

    NaissanceDto update(UUID id, NaissanceDto dto);

    void delete(UUID id);


    NaissanceDto getById(UUID id);
    List<NaissanceDto> getAll();
    List<NaissanceDto> getByMere(Animal mereId);

    List<NaissanceDto> getByPere(Animal pereId);

    List<NaissanceDto> getByCroisement(UUID croisementId);
    List<HistoriqueModification> getHistoriqueModifications(UUID naissanceId);

    //
    List<NaissanceDto> findByDate(LocalDate date);
}