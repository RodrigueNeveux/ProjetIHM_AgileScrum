package org.insset.server.romain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires TDD pour la logique de conversion (US 1).
 */
// C'est la SEULE classe publique qui doit être présente dans ce fichier.
public class RomanConverterTest {
// Initialisation de la classe de production à tester
    private RomanConverter converter = new RomanConverter();

    @Test
    public void testConvertIntegerToOne() {
        // Test vérifiant la conversion 1 -> I
        String expectedRoman = "I";
        int number = 1;
        String actualRoman = converter.convertIntegerToRoman(number);
        assertEquals(expectedRoman, actualRoman); // Doit passer (VERT)
    }@Test
    public void testConvertIntegerToFive() { 
        // Test vérifiant la conversion 5 -> V
        String expectedRoman = "V";
        int number = 5;
        String actualRoman = converter.convertIntegerToRoman(number);
        assertEquals(expectedRoman, actualRoman); // Doit passer (VERT)
    }
@Test
    public void testConvertIntegerToTwo() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas '2'
        String expectedRoman = "II";
        int number = 2;

        // Le code de production actuel ne gère que 1 et 5, donc il va retourner null.
        String actualRoman = converter.convertIntegerToRoman(number);

        // Assert: Le test doit échouer (expected:<II> but was:<null>)
        assertEquals(expectedRoman, actualRoman);
    }
}
