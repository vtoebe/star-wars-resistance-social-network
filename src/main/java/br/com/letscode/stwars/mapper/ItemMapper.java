package br.com.letscode.stwars.mapper;

import br.com.letscode.stwars.dto.ItemsDto;
import br.com.letscode.stwars.model.ItemsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemsEntity toEntity(ItemsDto itemsDto);
}
