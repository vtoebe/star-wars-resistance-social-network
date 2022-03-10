package br.com.letscode.stwars.service.validators;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BaseServiceValidator {
    private final BaseRepository baseRepository;
  public List<ValidationError> validate(String base) {
    List<ValidationError> validationErrors = new ArrayList<>();
       if (!baseRepository.findById(base).isPresent()) {
           validationErrors.add(
                   ValidationError.builder()
                           .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                           .params(List.of(MessageCodeEnum.BASE_FIELD))
                           .build());
       }
    return validationErrors;
  }
}
