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
    public void testConvertIntegerToNine() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas '9' (soustraction)
        String expectedRoman = "IX";
        int number = 9;

        // Le code de production actuel va retourner 'null' car il ne gère pas '9' explicitement.
        String actualRoman = converter.convertIntegerToRoman(number);

        // Assert: Le test doit échouer (expected:<IX> but was:<null>)
        assertEquals(expectedRoman, actualRoman);
    }

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
    public void testConvertIntegerToTen() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas '10'
        String expectedRoman = "X";
        int number = 10;

        // Le code de production actuel ne gère que 1-5, donc il va retourner 'null'.
        String actualRoman = converter.convertIntegerToRoman(number);

        // Assert: Le test doit échouer (expected:<X> but was:<null>)
        assertEquals(expectedRoman, actualRoman);
    }

@Test
    public void testConvertIntegerToFour() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas '4' (soustraction)
        String expectedRoman = "IV";
        int number = 4;
// Echec attendu : le code actuel va retourner 'null' (ou 'IIII' si on enlève le 'if (number == 5)')
        // L'implémentation actuelle retourne 'null' si l'entrée est 4.
        String actualRoman = converter.convertIntegerToRoman(number);

        // Assert: Le test doit échouer (expected:<IV> but was:<null>)
        assertEquals(expectedRoman, actualRoman);
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
