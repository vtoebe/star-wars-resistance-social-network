package br.com.letscode.stwars.utils;

import br.com.letscode.stwars.model.InventoryEntity;
import br.com.letscode.stwars.model.LocaleEntity;
import br.com.letscode.stwars.model.PersonEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EntityUtilsTest {

    @InjectMocks
    private EntityUtils entityUtils;

    @Test
    void testGetInventory() {
        Assertions.assertNotNull(EntityUtils.getInventory(getPersonEntity()));
        Assertions.assertNotNull(EntityUtils.getInventory(new PersonEntity()));
    }

    @Test
    void testGetLocale() {
        Assertions.assertNotNull(EntityUtils.getLocale(getPersonEntity()));
        Assertions.assertNotNull(EntityUtils.getLocale(new PersonEntity()));
    }

    private PersonEntity getPersonEntity() {
        PersonEntity personEntity = new PersonEntity();

        personEntity.setId(10L);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1L);
        inventoryEntity.setPerson(personEntity);
        personEntity.setInventory(inventoryEntity);

        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setId(1L);
        localeEntity.setPerson(personEntity);
        personEntity.setLocale(localeEntity);

        return personEntity;
    }

}