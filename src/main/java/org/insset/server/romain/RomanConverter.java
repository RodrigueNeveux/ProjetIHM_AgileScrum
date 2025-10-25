package org.insset.server.romain;

import java.util.HashMap; // Ajout nécessaire
import java.util.Map;     // Ajout nécessaire
import java.util.NoSuchElementException; // Utile pour les erreurs futures

/**
 * Logique métier pour la conversion de nombres Romains et Arabes.
 */
public class RomanConverter {

    // -------------------------------------------------------------------------
    // DECLARATIONS STATIQUES ET MAPS (UTILISÉES PAR LES DEUX MÉTHODES)
    // -------------------------------------------------------------------------
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
// Map statique pour la conversion inverse (Romain -> Entier)
    private static final Map<Character, Integer> ROMAN_MAP = new HashMap<>();

    static {
        // Initialisation des valeurs (US 2)
ROMAN_MAP.put('I', 1);
        ROMAN_MAP.put('V', 5);
        ROMAN_MAP.put('X', 10);
        ROMAN_MAP.put('L', 50);
        ROMAN_MAP.put('C', 100);
        ROMAN_MAP.put('D', 500);
        ROMAN_MAP.put('M', 1000);
    }
    // -------------------------------------------------------------------------
/**
     * Convertit un nombre arabe (entier) en chiffre romain. (US 1)
     * (Logique Algorithme Glouton complète et validée).
     */
    public String convertIntegerToRoman(int number) {
        if (number <= 0 || number > 2000) {
            throw new IllegalArgumentException("La valeur doit être un nombre compris entre 1 et 2000.");
        }
StringBuilder result = new StringBuilder();
        // Algorithme glouton : soustraire la plus grande valeur possible
        for (int i = 0; i < VALUES.length; i++) {
            while (number >= VALUES[i]) {
                result.append(SYMBOLS[i]);
                number -= VALUES[i];
            }
        }
        return result.toString();
} 

    /**
     * Convertit un chiffre romain en entier. (US 2)
     * (Phase ROUGE/VERT pour la composition 'VI').
     */
    public int convertRomanToInteger(String roman) {
        // Le test 'VI' va passer avec cet algorithme (V+I = 6)
        if (roman == null || roman.isEmpty()) {
            return 0; // Gère les cas vides pour le moment
        }
// TDD Phase ROUGE : Implémentation algorithmique
        int result = 0;
        int previousValue = 0;

        // Parcourir la chaîne romaine de droite à gauche
        for (int i = roman.length() - 1; i >= 0; i--) {
            // Utiliser getOrDefault (romain est en Majuscule, donc pas de souci)
            int currentValue = ROMAN_MAP.getOrDefault(roman.charAt(i), 0);
if (currentValue < previousValue) {
                // Règle de soustraction (ex: I avant V ou X)
                result -= currentValue;
            } else {
                // Règle d'addition (standard)
                result += currentValue;
            }
previousValue = currentValue;
        }
if (result < 1 || result > 2000) {
            // Fait passer le test 'testConvertRomanToInvalidRange'
            throw new IllegalArgumentException("La valeur convertie est hors de la plage valide [1-2000].");
        }
        
        return result; 
    }
}
