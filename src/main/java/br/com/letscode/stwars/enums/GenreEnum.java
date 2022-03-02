package br.com.letscode.stwars.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GenreEnum {
    FEMALE, MALE, NON_BINARY, NO_ANSWER;

    public static GenreEnum getEnum(String name) {
        return Arrays.stream(values()).filter(
                        e -> e.name().equals(name))
                .findFirst().orElse(null);
    }
}
