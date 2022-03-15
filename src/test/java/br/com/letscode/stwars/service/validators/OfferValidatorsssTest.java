package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class OfferValidatorsssTest {

    @InjectMocks
    private OfferValidatorsss offerValidator;

    BusinessValidationException businessValidationException = new BusinessValidationException("Error Message");

    @Test
    void whenFactionEmpire_thenThrowException() {
       BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> offerValidator
                                    .factionValidation(getOptionalPersonEntity()));
       assertEquals(" You are not welcome in out MarketPlace (Person is a member from EMPIRE faction)", exception.getMessage());
    }

//    @Test //NÃO FUNCIONA // TODO
//    void when_PointsReceive_Greater_ThenPointsOffer() {
//    when(mocked.getTotalPoints((ItemsEntity) any())).thenReturn(10);
//            BusinessValidationException exception = assertThrows(businessValidationException
//                    .getClass(), () -> offerValidator
//                    .pointsValidation(getGreaterPoint(), getSmallerPoint()));
//    }


    @Test
    void testBaseExistsValidation() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> offerValidator.baseExistsValidation(getMarketPlaceEntity(), getBaseEntity()));

        assertEquals("This base does not exists", exception.getMessage());
    }

    @Test
    void when_ItemQuantityValidation_and_OfferIsBiggerThanItemsInventory() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> offerValidator
                .itemQuantityValidation(getOptionalPersonEntity(), getItemsEntity()));

        assertEquals("You don't have enough points to do this transaction", exception.getMessage());
    }

    @Test
    void when_ItemQuantityValidation_and_OfferIsEmpty() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> offerValidator
                .itemQuantityValidation(getOptionalPersonEntity(), getEmptyItemsEntity()));

        assertEquals("Your offer is empty!", exception.getMessage());
    }


    public Optional<PersonEntity> getOptionalPersonEntity() {
        Optional<PersonEntity> optionalPersonEntity = Optional.of(new PersonEntity());
        optionalPersonEntity.get().setFaction(FactionEnum.EMPIRE);

        InventoryEntity inventory = new InventoryEntity();
        ItemsEntity items = new ItemsEntity();
        items.setWeapons(1);
        items.setFoods(1);
        items.setWaters(1);
        items.setAmmunitions(1);
        inventory.setItems(items);
        optionalPersonEntity.get().setInventory(inventory);

        return optionalPersonEntity;
    }

    public ItemsEntity getItemsEntity() {
        ItemsEntity items = new ItemsEntity();
        items.setAmmunitions(2);
        items.setFoods(2);
        items.setWeapons(2);
        items.setWaters(2);
        return items;
    }

    public ItemsEntity getEmptyItemsEntity() {
        ItemsEntity items = new ItemsEntity();
        items.setAmmunitions(0);
        items.setFoods(0);
        items.setWeapons(0);
        items.setWaters(0);
        return items;
    }

    public Optional<PersonEntity> getOptionalPersonEntityResistence() {
        Optional<PersonEntity> optionalPersonEntity = Optional.of(new PersonEntity());
        optionalPersonEntity.get().setFaction(FactionEnum.RESISTANCE);
        return optionalPersonEntity;
    }

    public ItemsEntity getGreaterPoint() {
        ItemsEntity itemsGreater = new ItemsEntity();
        itemsGreater.setAmmunitions(2);
        itemsGreater.setFoods(2);
        itemsGreater.setWaters(2);
        itemsGreater.setWeapons(2);
        return itemsGreater;
    }

    public ItemsEntity getSmallerPoint() {
        ItemsEntity itemsSmaller = new ItemsEntity();
        itemsSmaller.setAmmunitions(1);
        itemsSmaller.setFoods(1);
        itemsSmaller.setWaters(1);
        itemsSmaller.setWeapons(1);
        return itemsSmaller;
    }

    public BaseEntity getBaseEntity() {
        BaseEntity base = null;
        return base;
    }

    public MarketPlaceEntity getMarketPlaceEntity() {
        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();
        marketPlaceEntity.setId(1L);
        return marketPlaceEntity;
    }

}