package org.insset.server.romain;

public class RomanConverter {

    public String convertIntegerToRoman(int number) {
        // TDD Etape 3 : Écrire le code minimal pour faire passer TOUS les tests

        if (number == 5) {
            return "V"; // Keeps the existing test for 5 passing
        }
// Handle numbers built from 'I' (1, 2, 3)
        if (number >= 1 && number <= 3) {
            StringBuilder roman = new StringBuilder();
            int remaining = number;
            
            // Repeat 'I' for the number of times (e.g., 2 -> "II")
            while (remaining >= 1) {
                roman.append("I");
                remaining -= 1;
            }
            return roman.toString();
        }
// Handle the case where the number is 0 or greater than 5 (for future tests)
        return null; 
    }
}
