package br.com.letscode.stwars.enums;

import br.com.letscode.stwars.dto.ItemsDto;
import br.com.letscode.stwars.model.ItemsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemsEnumTest {

    @Test
    void testGetTotalPointsItemsEntity() {
        Integer response = ItemsEnum.getTotalPoints(getItemsEntity());
        assertNotNull(response);
        assertEquals(10, response);
    }

    @Test
    void testGetTotalPointsItemsDto() {
        Integer response = ItemsEnum.getTotalPoints(getItemsDto());
        assertNotNull(response);
        assertEquals(10, response);
    }


    ItemsEntity getItemsEntity() {
        ItemsEntity itemsEntity = new ItemsEntity();
        itemsEntity.setWaters(1);
        itemsEntity.setFoods(1);
        itemsEntity.setAmmunitions(1);
        itemsEntity.setWeapons(1);
        return itemsEntity;
    }

    ItemsDto getItemsDto() {
        ItemsDto itemsEntity = new ItemsDto();
        itemsEntity.setWaters(1);
        itemsEntity.setFoods(1);
        itemsEntity.setAmmunitions(1);
        itemsEntity.setWeapons(1);
        return itemsEntity;
    }

}