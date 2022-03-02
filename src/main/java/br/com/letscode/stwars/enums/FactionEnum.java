package br.com.letscode.stwars.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FactionEnum {
    EMPIRE,
    RESISTANCE;

    public static FactionEnum getEnum(String name) {
        return Arrays.stream(values()).filter(
                e -> e.name().equals(name))
                .findFirst().orElse(null);
    }
}
