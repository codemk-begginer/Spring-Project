package tech.steve.farmer_app.mapper;

import com.porc.dto.StockDto;
import com.porc.model.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDto toDto(Stock stock);
    Stock toEntity(StockDto dto);
    List<StockDto> toDtoList(List<Stock> stocks);
}
