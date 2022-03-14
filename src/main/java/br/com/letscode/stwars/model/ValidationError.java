package br.com.letscode.stwars.model;

import br.com.letscode.stwars.enums.MessageCodeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class ValidationError {
    private final MessageCodeEnum keyMessage;
    private final List<Object> params;
}
