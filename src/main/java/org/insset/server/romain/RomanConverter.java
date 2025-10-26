package org.insset.server.romain;

import java.util.HashMap;
import java.util.Map;

public class RomanConverter {

    // DECLARATIONS STATIQUES (Algorithme Glouton)
private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    
    private static final Map<Character, Integer> ROMAN_MAP = new HashMap<>();
static {
        // Initialisation des valeurs pour la conversion inverse
        ROMAN_MAP.put('I', 1);
        ROMAN_MAP.put('V', 5);
        ROMAN_MAP.put('X', 10);
        ROMAN_MAP.put('L', 50);
        ROMAN_MAP.put('C', 100);
        ROMAN_MAP.put('D', 500);
        ROMAN_MAP.put('M', 1000);
    }
    
    /**
* Convertit un nombre arabe (entier) en chiffre romain. (US 1)
     */
    public String convertIntegerToRoman(int number) {
        if (number <= 0 || number > 2000) {
            throw new IllegalArgumentException("La valeur doit être un nombre compris entre 1 et 2000.");
        }
StringBuilder result = new StringBuilder();
        int remaining = number;
        for (int i = 0; i < VALUES.length; i++) {
            while (remaining >= VALUES[i]) {
                result.append(SYMBOLS[i]);
                remaining -= VALUES[i];
            }
        }
return result.toString();
    } 

    /**
     * Convertit un chiffre romain en entier. (US 2)
     */
    public int convertRomanToInteger(String roman) {
        if (roman == null|| roman.isEmpty()) {
            return 0;
        }

        int result = 0;
        int previousValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
int currentValue = ROMAN_MAP.getOrDefault(roman.charAt(i), 0);
            
            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            previousValue = currentValue;
        }
// US 3: Validation de la plage après conversion
        if (result < 1 || result > 2000) {
            throw new IllegalArgumentException("La valeur convertie est hors de la plage valide [1-2000].");
        }
        return result; 
    } 
    
    /**
     * Valide le format de la date (jj/mm/aaaa) et la convertit. (Portée finale)
     */
public String convertBirthDateToRoman(String dateStr) throws IllegalArgumentException {
        // Validation stricte du format jj/mm/aaaa (Exigence finale)
        if (!dateStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("Le format de la date doit être jj/mm/aaaa.");
        }

        try {
            String[] parts = dateStr.split("/");
String dayRoman = convertIntegerToRoman(Integer.parseInt(parts[0]));
            String monthRoman = convertIntegerToRoman(Integer.parseInt(parts[1]));
            String yearRoman = convertIntegerToRoman(Integer.parseInt(parts[2]));
            
            return dayRoman + "/" + monthRoman + "/" + yearRoman;
        } catch (NumberFormatException e) {
             throw new IllegalArgumentException("La date contient des valeurs numériques invalides.");
        }
    }
}
