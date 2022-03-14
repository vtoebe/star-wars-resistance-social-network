package br.com.letscode.stwars.exceptions;

import br.com.letscode.stwars.model.ValidationError;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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

}
