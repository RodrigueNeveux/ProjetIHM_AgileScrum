package org.insset.server.romain;

public class RomanConverter {

    // Define the values and their corresponding symbols in descending order
    // This supports combining units (10 + 2) and subtraction rules (9, 4).
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

public String convertIntegerToRoman(int number) {
        // TDD Etape 3 : Implémenter l'algorithme général pour passer TOUS les 7 tests

        // --- Boundary Check (Future enhancement, but good for structure) ---
        if (number <= 0 || number > 2000) {
            // NOTE: We return a string here, but future TDD should verify error messages.
            return null; 
        }

        StringBuilder result = new StringBuilder();
// Greedy algorithm: subtract the largest possible value until the number is 0
        for (int i = 0; i < VALUES.length; i++) {
            while (number >= VALUES[i]) {
                result.append(SYMBOLS[i]);
                number -= VALUES[i];
            }
        }
return result.toString();
    }
}
