package br.com.letscode.stwars.mapper;

import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.GenreEnum;
import br.com.letscode.stwars.mapper.impl.MapperHelper;
import br.com.letscode.stwars.model.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {MapperHelper.class, GenreEnum.class, FactionEnum.class})
public interface PersonMapper {

    @Mapping(target = "genre", expression = "java( GenreEnum.getEnum(request.getGenre()) )")
    @Mapping(target = "faction", expression = "java( FactionEnum.RESISTANCE )")

    @Mapping(target = "birth", expression = "java( MapperHelper.transformToDate(request.getBirth()) )")

    @Mapping(target = "locale.latitude", source = "request.latitude")
    @Mapping(target = "locale.longitude", source = "request.longitude")

    @Mapping(target = "inventory.items.weapons", source = "request.weapons")
    @Mapping(target = "inventory.items.ammunitions", source = "request.ammunitions")
    @Mapping(target = "inventory.items.waters", source = "request.waters")
    @Mapping(target = "inventory.items.foods", source = "request.foods")
    PersonEntity toEntity(PersonRequestDto request);
}
