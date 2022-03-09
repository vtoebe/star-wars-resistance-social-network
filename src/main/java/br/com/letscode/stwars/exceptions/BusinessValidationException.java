package br.com.letscode.stwars.exceptions;

public class BusinessValidationException extends RuntimeException{

    public BusinessValidationException(String message) {
        super(message.toString());
    }
}
