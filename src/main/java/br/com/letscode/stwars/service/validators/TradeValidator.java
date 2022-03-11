package br.com.letscode.stwars.validators;

import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TradeValidator {
    public void offerExistsValidation(MarketPlaceEntity marketPlaceEntity) {
        if (marketPlaceEntity == null){
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                    .params(List.of(MessageCodeEnum.OFFER_ID_FIELD))
                    .build());
        }
    }

    public void offerByExists(PersonEntity offerBy) {
        if (offerBy == null) {
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                    .params(List.of(MessageCodeEnum.OFFER_REBEL_ID_FIELD))
                    .build());
        }
    }

    public void receiverExistsValidation(PersonEntity receiver) {
        if (receiver == null) {
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                    .params(List.of(MessageCodeEnum.RECEIVER_ID))
                    .build());
        }
    }

    public void sameBaseValidation(PersonEntity receiver, PersonEntity offerBy) {
        if(offerBy.getLocale().getBase() != receiver.getLocale().getBase()){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.NOT_IN_SAME_BASE)
                    .params(List.of(MessageCodeEnum.BASE_FIELD))
                    .build()));
        }
    }

    public void differentRebelsTradingValidation(PersonEntity offerBy, PersonEntity receiver) {
        if (Objects.equals(offerBy.getName(), receiver.getName())){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.SAME_ID)
                    .params(List.of(MessageCodeEnum.PERSON_ID_FIELD))
                    .build()));        }
    }
}
