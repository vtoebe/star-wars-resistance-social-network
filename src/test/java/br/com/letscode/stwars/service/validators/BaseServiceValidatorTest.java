package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.repository.BaseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class BaseServiceValidatorTest {

    @InjectMocks
    private BaseServiceValidator baseServiceValidator;
    @Mock
    private BaseRepository baseRepository;

    @Test
    void testValidate() {
        when(baseRepository.findById(Mockito.any())).thenReturn(getOptionalBaseEntity());
        baseServiceValidator.validate("any");
    }


    Optional<BaseEntity> getOptionalBaseEntity() {
        Optional<BaseEntity> optionalBaseEntity = Optional.of(new BaseEntity());
        optionalBaseEntity.get().setName("any");
        return optionalBaseEntity;
    }


}