package tech.steve.essaie.mapper;

import tech.steve.essaie.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "animal.id", target = "animalId")
     tech.steve.essaie.dto.TransactionDto toDto(Transaction entity);

    @Mapping(source = "animalId", target = "animal.id")
    Transaction toEntity(tech.steve.essaie.dto.TransactionDto dto);


}


