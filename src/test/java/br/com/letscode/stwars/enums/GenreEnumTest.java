package br.com.letscode.stwars.enums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenreEnumTest {

    @Test
    void testGetEnum() {
        assertNotNull(GenreEnum.getEnum("FEMALE"));
    }

    @Test
    void testGetEnumNull() {
        assertNull(GenreEnum.getEnum("Any"));
    }

}