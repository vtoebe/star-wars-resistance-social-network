package br.com.letscode.stwars.exceptions;

import br.com.letscode.stwars.model.ValidationError;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BusinessValidationException extends RuntimeException{
    @Getter
    private final List<ValidationError> validationErrors;

    public BusinessValidationException(List<ValidationError> validationErrors) {
        super(validationErrors.stream().map(ValidationError::toString).collect(Collectors.joining(";")));
        this.validationErrors = validationErrors;
    }

    public BusinessValidationException(ValidationError validationErrorMessage) {
        super(validationErrorMessage.toString());
        validationErrors = List.of(validationErrorMessage);
    }

    public BusinessValidationException(String message) {
        super(message.toString());
        validationErrors = null;
    }
}
