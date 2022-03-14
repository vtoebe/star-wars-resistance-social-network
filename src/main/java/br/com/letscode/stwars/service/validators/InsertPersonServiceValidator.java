package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertPersonServiceValidator {

    public List<ValidationError> validate(PersonRequestDto rebel) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (rebel == null) {
            return List.of(
                    ValidationError.builder()
                            .keyMessage(MessageCodeEnum.REQUIRED_FIELD)
                            .params(List.of(MessageCodeEnum.PERSON_FIELD))
                            .build());
        }

        if (!StringUtils.hasText(rebel.getName())) {
            validationErrors.add(
                    ValidationError.builder()
                            .keyMessage(MessageCodeEnum.REQUIRED_FIELD)
                            .params(List.of(MessageCodeEnum.NAME_FIELD))
                            .build());
        }

        if (!StringUtils.hasText(rebel.getGenre().toString())) {
            validationErrors.add(
                    ValidationError.builder()
                            .keyMessage(MessageCodeEnum.REQUIRED_FIELD)
                            .params(List.of(MessageCodeEnum.GENRE_FIELD))
                            .build());
        }

        if (CollectionUtils.isEmpty(Collections.singleton(rebel.getBirth().toString()))) {
            validationErrors.add(
                    ValidationError.builder()
                            .keyMessage(MessageCodeEnum.REQUIRED_FIELD)
                            .params(List.of(MessageCodeEnum.BIRTH_FIELD))
                            .build());
        }

        return validationErrors;
    }
}

