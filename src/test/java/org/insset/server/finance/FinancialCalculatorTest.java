package org.insset.server.finance;

import org.junit.Test;
import static org.junit.Assert.*;

public class FinancialCalculatorTest {

    private FinancialCalculator calculator = new FinancialCalculator();
// -------------------------------------------------------------------------
    // TESTS US 9 : DIVISION (Incluant Contrainte Division par Zéro)
    // -------------------------------------------------------------------------

    // Test qui était précédemment ROUGE (maintenant attendu VERT)
    @Test
    public void testDivideBasic() {
        assertEquals(5.0, calculator.divide(10.0, 2.0), 0.001);
    }
// Test de la contrainte : division par zéro (état ROUGE initial, maintenant attendu VERT)
    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calculator.divide(10.0, 0.0);
    }
// Test de la division d'un négatif
    @Test
    public void testDivideNegative() {
        assertEquals(-2.5, calculator.divide(5.0, -2.0), 0.001);
    }
// -------------------------------------------------------------------------
    // TESTS US 10 : REMISE (Incluant Contraintes)
    // -------------------------------------------------------------------------
    
    // Test de remise de base (100 avec 20% = 80)
    @Test
    public void testDiscountBasic() {
        assertEquals(80.0, calculator.calculateDiscount(100.0, 20.0), 0.001);
    }
// Test de remise avec décimales (Arrondi)
    @Test
    public void testDiscountWithDecimals() {
        assertEquals(9.9, calculator.calculateDiscount(10.0, 1.0), 0.001);
    }

    // Test de la contrainte : Pourcentage > 100% (état ROUGE attendu)
    @Test(expected = IllegalArgumentException.class)
    public void testDiscountPercentageTooHigh() {
        calculator.calculateDiscount(100.0, 101.0);
    }
// Test de la contrainte : Prix négatif
    @Test(expected = IllegalArgumentException.class)
    public void testDiscountPriceNegative() {
        calculator.calculateDiscount(-100.0, 10.0);
    }
// Test qui était précédemment ROUGE (maintenant attendu VERT)
    @Test
    public void testCalculateOriginalPriceBasic() {
        // Si le prix final est 80.0 avec une remise de 20%, le prix original est 100.0
        assertEquals(100.0, calculator.calculateOriginalPrice(80.0, 20.0), 0.001);
    }
// Test de la contrainte : Remise à 100% (Division par zéro ou erreur)
    @Test(expected = IllegalArgumentException.class)
    public void testOriginalPriceDiscountAt100Percent() {
        calculator.calculateOriginalPrice(100.0, 100.0);
    }
}
