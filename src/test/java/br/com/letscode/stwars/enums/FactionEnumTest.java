package br.com.letscode.stwars.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FactionEnumTest {

    @Test
    void testGetEnum() {
        assertNotNull(FactionEnum.getEnum("RESISTANCE"));
    }

    @Test
    void testGetEnumNull() {
        assertNull(FactionEnum.getEnum("Any"));
    }

}