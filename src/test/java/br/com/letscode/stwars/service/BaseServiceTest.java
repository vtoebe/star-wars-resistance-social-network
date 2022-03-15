package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.BaseDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.mapper.BaseMapper;
import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.BaseRepository;
import br.com.letscode.stwars.service.validators.BaseServiceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class BaseServiceTest {

    private BaseService baseService;

    @Mock
    private BaseServiceValidator baseServiceValidator;
    @Mock
    private BaseRepository baseRepository;
    @Mock
    private BaseMapper mapper;

    public static final String NAME = "BaseName";
    public static final FactionEnum FACTION = FactionEnum.RESISTANCE;

    BaseDto baseDto;

    List<ValidationError> listErrors = new ArrayList<>();

    @BeforeEach
    void setUp(){
        baseService = new BaseService(baseRepository, mapper, baseServiceValidator);
        baseDto = new BaseDto();
    }

    @Test // // NÃO ESTA SENDO UTILIZADO -> REMOVER??
    void shouldReturn_BaseEntity_getById(){
        when(baseServiceValidator.validate(anyString())).thenReturn(listErrors);
        when(baseRepository.getById(Mockito.anyString()))
                .thenReturn(getBaseEntity());

        BaseEntity response = baseService.getBase(NAME);
        assertNotNull(response);
        assertEquals(BaseEntity.class, response.getClass());
        assertEquals(NAME, response.getName());
        assertEquals(FACTION, response.getFaction());
    }


    @Test
    void when_getAllBases_thenReturn_listOfBaseDto(){
        List<BaseEntity> listBase = new ArrayList<>();
        listBase.add(getBaseEntity());

        when(baseRepository.findAll()).thenReturn(listBase);
        when(mapper.toDto(Mockito.any())).thenReturn(getBaseDto());

        List<BaseDto> response = baseService.getAllBases();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(BaseDto.class, response.get(0).getClass());
        assertEquals(NAME, response.get(0).getName());
    }

    private BaseEntity getBaseEntity(){
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setName(NAME);
        baseEntity.setFaction(FACTION);
        return baseEntity;
    }

    private BaseDto getBaseDto() {
        BaseDto baseDto = new BaseDto();
        baseDto.setName(NAME);
        baseDto.setFaction("RESISTENCE");
        return baseDto;
    }


    private Optional<BaseEntity> getOptionalBaseEntity() {
        Optional<BaseEntity> optionalBaseEntity = Optional.of(new BaseEntity());
        optionalBaseEntity.get().setName(NAME);
        optionalBaseEntity.get().setFaction(FACTION);
        return optionalBaseEntity;
    }


}