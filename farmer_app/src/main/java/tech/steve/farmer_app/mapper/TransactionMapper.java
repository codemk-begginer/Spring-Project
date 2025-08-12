package tech.steve.farmer_app.mapper;

import com.porc.dto.TransactionDto;
import com.porc.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "animal.id", target = "animalId")
    TransactionDto toDto(Transaction entity);

    @Mapping(source = "animalId", target = "animal.id")
    Transaction toEntity(TransactionDto dto);
}


