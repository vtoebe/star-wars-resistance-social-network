package br.com.letscode.stwars.mapper;

import br.com.letscode.stwars.dto.BaseDto;
import br.com.letscode.stwars.model.BaseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    BaseDto toDto(BaseEntity entity);
}
