package tech.steve.essaie.service;


import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.HistoriqueModification;

import java.time.LocalDate;
import java.util.List;

public interface NaissanceService {
    public NaissanceDto create(NaissanceDto dto);

    NaissanceDto update(Long id, NaissanceDto dto);

    void delete(Long id);


    NaissanceDto getById(Long id);
    List<NaissanceDto> getAll();
    List<NaissanceDto> getByMere(Animal mereId);

    List<NaissanceDto> getByPere(Animal pereId);

    List<NaissanceDto> getByCroisement(Long croisementId);
    List<HistoriqueModification> getHistoriqueModifications(Long naissanceId);

    //
    List<NaissanceDto> findByDate(LocalDate date);
}