package br.com.letscode.stwars.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageCodeEnum {
    REQUIRED_FIELD("required.field"),
    FACTION_FIELD("faction.field"),
    RESOURCE_ALREADY_EXISTS("resource.already.exists"),
    PERSON_FIELD("person.field"),
    RESOURCE_NOT_FOUND("resource.not.found"),
    NAME_FIELD("name.field"),
    GENRE_FIELD("genre.field"),
    BASE_FIELD("base.field"),
    INVENTORY_NAME("inventory.name"),
    PERSON_ID_FIELD("person.id.field"),
    BIRTH_FIELD("birth.field"),
    INVENTORY_ID_FIELD("inventory.id.field"),
    ITEMS_FIELD("items.field"),
    NOT_ALLOWED(" You are not welcome in our MarketPlace (Person is a member from EMPIRE faction)"),
    NOT_ENOUGH_POINTS("You don't have enough points to do this transaction"),
    MORE_THAN_NEEDED("Your total offer has more points than needed to receive these items"),
    LESS_THAN_NEEDED("Your total offer has less points than needed to receive these items"),
    NOT_IN_SAME_BASE("You are not in the marketPlace offer Base!"),
    SAME_ID("You cannot report yourself!");
    private final String code;
}
