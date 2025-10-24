package org.insset.server.romain;

public class RomanConverter {

    public String convertIntegerToRoman(int number) {
        // TDD Etape 3 : Écrire le code minimal pour faire passer TOUS les tests (1, 2, 4, 5)

        // 1. Gère le cas de soustraction '4' (testConvertIntegerToFour)
        if (number == 4) {
            return "IV";
        }
// 2. Gère le cas de mapping '5' (testConvertIntegerToFive)
        if (number == 5) {
            return "V";
        }
        
        // 3. Gère les cas de répétition '1', '2', '3' (testConvertIntegerToOne, testConvertIntegerToTwo)
        if (number >= 1 && number <= 3) {
            StringBuilder roman = new StringBuilder();
            int remaining = number;
// Répète 'I' pour le nombre de fois (e.g., 2 -> "II")
            while (remaining >= 1) {
                roman.append("I");
                remaining -= 1;
            }
            return roman.toString();
        }

        // Cas non gérés (nombre trop grand ou 0 / négatif)
        return null; 
    }
}
