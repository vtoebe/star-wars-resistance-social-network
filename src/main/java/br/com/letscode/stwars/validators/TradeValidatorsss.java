package br.com.letscode.stwars.validators;

import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.model.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TradeValidatorsss {
    public void offerExistsValidation(MarketPlaceEntity marketPlaceEntity) {
        if (marketPlaceEntity == null){
            throw new BusinessValidationException("Invalid Offer ID");
        }
    }

    public void offerByExists(PersonEntity offerBy) {
        if (offerBy == null) {
            throw new BusinessValidationException("Invalid Offer's Rebel's ID");
        }
    }

    public void receiverExistsValidation(PersonEntity receiver) {
        if (receiver == null) {
            throw new BusinessValidationException("Invalid Receiver ID");
        }
    }

    public void sameBaseValidation(PersonEntity receiver, PersonEntity offerBy) {
        if(offerBy.getLocale().getBase() != receiver.getLocale().getBase()){
            throw new BusinessValidationException("You are not in the same Base! Please go to " + offerBy.getLocale().getBase() + " and try again!");
        }
    }

    public void differentRebelsTradingValidation(PersonEntity offerBy, PersonEntity receiver) {
        if (Objects.equals(offerBy.getName(), receiver.getName())){
            throw new BusinessValidationException("You can't trade with yourself");
        }
    }
}
