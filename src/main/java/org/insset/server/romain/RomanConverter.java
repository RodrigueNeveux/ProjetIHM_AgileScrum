package org.insset.server.romain;

public class RomanConverter {

    public String convertIntegerToRoman(int number) {
        // TDD Etape 3 : Écrire le code minimal pour faire passer TOUS les tests

        // NEW: Handles the new mapping case '10' (testConvertIntegerToTen)
        if (number == 10) {
            return "X"; 
        }
// Handles subtraction case '4' (existing test)
        if (number == 4) {
            return "IV";
        }

        // Handles mapping case '5' (existing test)
        if (number == 5) {
            return "V";
        }
// Handles repetition cases '1', '2', '3' (existing test logic)
        if (number >= 1 && number <= 3) {
            StringBuilder roman = new StringBuilder();
            int remaining = number;
            
            while (remaining >= 1) {
                roman.append("I");
                remaining -= 1;
            }
            return roman.toString();
        }
// Cas non gérés
        return null; 
    }
}
