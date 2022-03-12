package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateLocaleValidator {
    private final PersonRepository personRepository;

  public List<ValidationError> validate(PersonEntity personId) {
      List<ValidationError> validationErrors = new ArrayList<>();
    if (personId == null) {
      return List.of(
          ValidationError.builder()
              .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
              .params(List.of(MessageCodeEnum.PERSON_ID_FIELD))
              .build());
    }
     return validationErrors;
    }
}
