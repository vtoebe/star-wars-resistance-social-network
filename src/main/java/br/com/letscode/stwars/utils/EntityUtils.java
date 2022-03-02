package br.com.letscode.stwars.utils;

import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.model.InventoryEntity;
import br.com.letscode.stwars.model.LocaleEntity;
import br.com.letscode.stwars.model.PersonEntity;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class EntityUtils {

    public static InventoryEntity getInventory(PersonEntity person) {
        InventoryEntity inventory = person.getInventory();
        if (Objects.isNull(inventory)) {
            inventory = new InventoryEntity();
            person.setInventory(inventory);
            inventory.setPerson(person);
        }
        return inventory;
    }

    public static LocaleEntity getLocale(PersonEntity person) {
        LocaleEntity locale = person.getLocale();
        if (Objects.isNull(locale)) {
            locale = new LocaleEntity();
            person.setLocale(locale);
            locale.setPerson(person);
        }
        return locale;
    }
}
