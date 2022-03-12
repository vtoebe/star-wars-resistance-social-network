package br.com.letscode.stwars.dto;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

public class DtoTest {

    @Test
    void testDtos() {
        assertThat(BaseDto.class, hasValidGettersAndSetters());
        assertThat(ItemsDto.class, hasValidGettersAndSetters());
        assertThat(LocaleRequestDto.class, hasValidGettersAndSetters());
        assertThat(MarketPlaceDto.class, hasValidGettersAndSetters());
        assertThat(PersonIdDto.class, hasValidGettersAndSetters());
        assertThat(PersonRequestDto.class, hasValidGettersAndSetters());
        assertThat(PersonResponseDto.class, hasValidGettersAndSetters());
        assertThat(ReportRequestDto.class, hasValidGettersAndSetters());
    }
}
