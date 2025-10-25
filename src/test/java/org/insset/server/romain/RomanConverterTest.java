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
    public void testConvertRomanToSix() {
        assertEquals(6, converter.convertRomanToInteger("VI"));
    }
    @Test
    public void testConvertRomanToTwelve() {
        assertEquals(12, converter.convertRomanToInteger("XII"));
    }
@Test
    public void testConvertRomanToFour() { // Devient IV -> 4
        assertEquals(4, converter.convertRomanToInteger("IV"));
    }
    @Test
    public void testConvertRomanToNine() { // Devient IX -> 9
        assertEquals(9, converter.convertRomanToInteger("IX"));
    }
@Test
    public void testConvertRomanToFifty() {
        assertEquals(50, converter.convertRomanToInteger("L"));
    }
    @Test
    public void testConvertRomanToForty() {
        assertEquals(40, converter.convertRomanToInteger("XL"));
    }
@Test
    public void testConvertRomanToBigNumber() {
        assertEquals(1994, converter.convertRomanToInteger("MCMXCIV"));
    }
    @Test
    public void testConvertRomanToLimit() { // Test de la limite du projet
        assertEquals(2000, converter.convertRomanToInteger("MM"));
    }
// Test de la plage (US 3 - Échec prévu pour l'IHM)
    @Test(expected = IllegalArgumentException.class)
    public void testConvertRomanToInvalidRange() {
         converter.convertRomanToInteger("MMI"); // 2001
    }


@Test
    public void testConvertRomanToFive() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas 'V'
        int expectedInteger = 5;
        String roman = "V";

        // Le code de production actuel retourne '0' car il ne gère que 'I'.
        int actualInteger = converter.convertRomanToInteger(roman);

        // Assert: Le test doit échouer (expected:<5> but was:<0>)
        assertEquals(expectedInteger, actualInteger);
    }
@Test
    public void testConvertRomanToOne() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas simple ('I')
        int expectedInteger = 1;
        String roman = "I";
int actualInteger = converter.convertRomanToInteger(roman);
assertEquals(expectedInteger, actualInteger);
    }

@Test
    public void testConvertIntegerToTwelve() {
        // TDD Etape 1 : Définir ce que l'on attend pour le cas '12' (X + II)
        String expectedRoman = "XII";
        int number = 12;

        // Le code de production actuel ne gère pas la combinaison et va retourner 'null'.
        String actualRoman = converter.convertIntegerToRoman(number);

        // Assert: Le test doit échouer (expected:<XII> but was:<null>)
        assertEquals(expectedRoman, actualRoman);
    }

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
