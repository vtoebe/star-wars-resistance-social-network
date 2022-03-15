package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.model.LocaleEntity;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.model.PersonEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradeValidatorsssTest {

    @InjectMocks
    private TradeValidatorsss tradeValidatorsss;

    BusinessValidationException businessValidationException = new BusinessValidationException("Error Message");

    @Test
    void testOfferExistsValidation() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> tradeValidatorsss
                .offerExistsValidation(getMarketPlaceEntity()));
        assertEquals("Invalid Offer ID", exception.getMessage());
    }

    @Test
    void testOfferByExists() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> tradeValidatorsss
                .offerByExists(getPersonEntity()));
        assertEquals("Invalid Offer's Rebel's ID", exception.getMessage());
    }

    @Test
    void testReceiverExistsValidation() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> tradeValidatorsss
                .receiverExistsValidation(getPersonEntity()));
        assertEquals("Invalid Receiver ID", exception.getMessage());
    }

    @Test
    void testSameBaseValidation() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> tradeValidatorsss
                .sameBaseValidation(getPersonEntityLocal1(), getPersonEntityLocal2()));
        assertEquals("You are not in the same Base! Please go to " + getPersonEntityLocal2().getLocale().getBase() + " and try again!", exception.getMessage());
    }

    @Test
    void testDifferentRebelsTradingValidation() {
        BusinessValidationException exception = assertThrows(businessValidationException
                .getClass(), () -> tradeValidatorsss
                .differentRebelsTradingValidation(getPersonEntityLocal1(), getPersonEntityLocal1()));
        assertEquals("You can't trade with yourself", exception.getMessage());
    }


    private MarketPlaceEntity getMarketPlaceEntity() {
        MarketPlaceEntity marketPlaceEntity = null;
        return marketPlaceEntity;
    }

    private PersonEntity getPersonEntity() {
        PersonEntity personEntity = null;
        return personEntity;
    }

    private PersonEntity getPersonEntityLocal1() {
        PersonEntity personEntity = new PersonEntity();
        LocaleEntity localeEntity = new LocaleEntity();
        BaseEntity base = new BaseEntity();
        base.setName("Base1");
        base.setFaction(FactionEnum.RESISTANCE);
        localeEntity.setBase(base);
        personEntity.setLocale(localeEntity);
        return personEntity;
    }

    private PersonEntity getPersonEntityLocal2() {
        PersonEntity personEntity = new PersonEntity();
        LocaleEntity localeEntity = new LocaleEntity();
        BaseEntity base = new BaseEntity();
        base.setName("Base2");
        base.setFaction(FactionEnum.EMPIRE);
        localeEntity.setBase(base);
        personEntity.setLocale(localeEntity);
        return personEntity;
    }

}