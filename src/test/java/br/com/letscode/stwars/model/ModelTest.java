package br.com.letscode.stwars.model;

import com.google.code.beanmatchers.BeanMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class ModelTest {

    @BeforeAll
    static void setUp() {
        BeanMatchers.registerValueGenerator( () -> LocalDateTime.now(), LocalDateTime.class);
        BeanMatchers.registerValueGenerator( () -> LocalDate.now(), LocalDate.class);
    }

    @Test
    void testModel() {
        assertThat(BaseEntity.class, hasValidGettersAndSetters());
        assertThat(InventoryEntity.class, hasValidGettersAndSetters());
        assertThat(ItemsEntity.class, hasValidGettersAndSetters());
        assertThat(LocaleEntity.class, hasValidGettersAndSetters());
        assertThat(LocationHistoryEntity.class, hasValidGettersAndSetters());
        assertThat(MarketPlaceEntity.class, hasValidGettersAndSetters());
        assertThat(PersonEntity.class, hasValidGettersAndSetters());
        assertThat(RebellionEntity.class, hasValidGettersAndSetters());
        assertThat(RebellionId.class, hasValidGettersAndSetters());
        assertThat(TransactionHistoryEntity.class, hasValidGettersAndSetters());
    }

}