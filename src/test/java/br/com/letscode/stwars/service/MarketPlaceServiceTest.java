package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.ItemsDto;
import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.dto.PersonIdDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.mapper.ItemMapper;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.*;
import br.com.letscode.stwars.service.validators.OfferValidatorsss;
import br.com.letscode.stwars.service.validators.TradeValidatorsss;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MarketPlaceServiceTest {

    public static final long ID = 1L;
    @InjectMocks
    private MarketPlaceService marketPlaceService;

    @Mock
    private PersonRepository personRepository;
    @Mock
    private BaseRepository baseRepository;
    @Mock
    private ItemsRepository itemsRepository;
    @Mock
    private OfferValidatorsss offerValidator;
    @Mock
    private ItemMapper mapper;
    @Mock
    private PersonService personService;
    @Mock
    private MarketPlaceRepository marketPlaceRepository;
    @Mock
    private TradeValidatorsss tradeValidatorsss;
    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @Mock
    MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();

    public static final String NAME = "BaseName";
    public static final FactionEnum FACTION = FactionEnum.RESISTANCE;

    PersonEntity offer = new PersonEntity();

    @BeforeEach
    void setUp() {
        offer.setId(77L);
        offer.setName("Person1");
        offer.setFaction(FactionEnum.RESISTANCE);
    }

    @Test
    void testInsertNewOffer() {
        when(personRepository.findById(anyLong())).thenReturn(getOptionalPersonEntity());

        doNothing().when(offerValidator).factionValidation(getOptionalPersonEntity());

        when(mapper.toEntity(Mockito.any())).thenReturn(getItemsEntity());
        doNothing().when(offerValidator).itemQuantityValidation(getOptionalPersonEntity(), getItemsEntity());

        when(offerValidator.pointsValidation(any(), any())).thenReturn(1);

        when(baseRepository.findById(Mockito.anyString())).thenReturn(getOptionalBaseEntity());
        doNothing().when(offerValidator).baseExistsValidation(getMarketPlaceEntity(), getBaseEntity());

        when(itemsRepository.save(any())).thenReturn(getItemsEntity());
        when(personService.removeItemFromInventory(any(), any())).thenReturn(getPersonEntity());
        when(marketPlaceRepository.save(any())).thenReturn(getMarketPlaceEntity());

        marketPlaceService.insertNewOffer(getMarketPlaceDto());
    }

    @Test
    void testGetListMarketPlace() {
        List<MarketPlaceEntity> listMarketPlace = new ArrayList<>();
        listMarketPlace.add(getMarketPlaceEntity());
        when(marketPlaceRepository.findAll()).thenReturn(listMarketPlace);

        List<MarketPlaceEntity> response = marketPlaceService.getListByMarketPlace();

        Assertions.assertNotNull(response);
        assertEquals(MarketPlaceEntity.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
    }

    @Test
    void testTradeItems() {
        when(marketPlaceRepository.getById(anyLong())).thenReturn(getMarketPlaceEntity());
        doNothing().when(tradeValidatorsss).offerExistsValidation(getMarketPlaceEntity());

        when(personRepository.getById(any())).thenReturn(getPersonEntity());
        doNothing().when(tradeValidatorsss).offerByExists(getPersonEntity());

        when(personService.getPersonById(anyLong())).thenReturn(getPersonEntity());
        doNothing().when(tradeValidatorsss).receiverExistsValidation(getPersonEntity());

        doNothing().when(offerValidator).factionValidation(getOptionalPersonEntity());
        doNothing().when(offerValidator).itemQuantityValidation(getOptionalPersonEntity(), getItemsEntity());

        doNothing().when(tradeValidatorsss).differentRebelsTradingValidation(getPersonEntity(), getPersonEntity());
        doNothing().when(tradeValidatorsss).sameBaseValidation(getPersonEntity(), getPersonEntity());

        when(personService.addItemToInventory(any(), any())).thenReturn(getPersonEntity());
        when(personService.removeItemFromInventory(any(), any())).thenReturn(getPersonEntity());

        when(transactionHistoryRepository.save(any())).thenReturn(new TransactionHistoryEntity());

        when(personRepository.save(any())).thenReturn(getPersonEntity());
        doNothing().when(marketPlaceRepository).delete(any());

        marketPlaceService.tradeItems(getPersonIdDto(), 2L);
    }


    private PersonIdDto getPersonIdDto() {
        PersonIdDto personIdDto = new PersonIdDto();
        personIdDto.setId(55L);
        return personIdDto;
    }

    private MarketPlaceDto getMarketPlaceDto(){
        MarketPlaceDto marketPlaceDto = new MarketPlaceDto();

        ItemsDto offer = new ItemsDto();
        marketPlaceDto.setOffer(offer);

        ItemsDto receive = new ItemsDto();
        marketPlaceDto.setReceive(receive);

        marketPlaceDto.setIdPerson(getPersonEntity().getId());
        marketPlaceDto.setBase("");

        return marketPlaceDto;
    }

    private MarketPlaceEntity getMarketPlaceEntity() {
        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();

        marketPlaceEntity.setId(ID);

        marketPlaceEntity.setPoints(5);
        marketPlaceEntity.setBase(getBaseEntity());

        ItemsEntity offer = new ItemsEntity();
        offer.setId(5L);
        marketPlaceEntity.setOffer(offer);

        ItemsEntity receive = new ItemsEntity();
        receive.setId(20L);
        marketPlaceEntity.setReceive(receive);

        marketPlaceEntity.setOfferedBy(getPersonEntity());

        return marketPlaceEntity;
    }


    private PersonEntity getPersonEntity() {
        PersonEntity personEntity = new PersonEntity();

        personEntity.setId(10L);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(ID);
        inventoryEntity.setPerson(personEntity);
        personEntity.setInventory(inventoryEntity);

        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setId(ID);
        localeEntity.setPerson(personEntity);
        personEntity.setLocale(localeEntity);

        return personEntity;
    }

    private Optional<PersonEntity> getOptionalPersonEntity() {
        Optional<PersonEntity> optionalPersonEntity = Optional.of(new PersonEntity());
        optionalPersonEntity.get().setName(NAME);
        optionalPersonEntity.get().setFaction(FACTION);
        optionalPersonEntity.get().setId(ID);

        return optionalPersonEntity;
    }

    private ItemsEntity getItemsEntity(){
        ItemsEntity itemsEntity = new ItemsEntity();
        itemsEntity.setId(ID);
        return itemsEntity;
    }

    private BaseEntity getBaseEntity() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setName(NAME);
        baseEntity.setFaction(FACTION);
        return baseEntity;
    }

    private Optional<BaseEntity> getOptionalBaseEntity() {
        Optional<BaseEntity> optionalBaseEntity = Optional.of(new BaseEntity());
        optionalBaseEntity.get().setName(NAME);
        optionalBaseEntity.get().setFaction(FACTION);
        return optionalBaseEntity;
    }



}