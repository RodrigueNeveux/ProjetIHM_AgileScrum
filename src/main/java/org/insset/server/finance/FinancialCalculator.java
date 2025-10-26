package org.insset.server.finance;

/**
 * Logique métier pour les calculs financiers (Division et Remise).
 */
public class FinancialCalculator {

    /**
     * Effectue une division simple.
     * Gère la division par zéro (US 9).
     */
public double divide(double numerator, double denominator) {
        if (denominator == 0.0) {
            throw new IllegalArgumentException("Erreur: La division par zéro n'est pas autorisée.");
        }
        return numerator / denominator;
    }
/**
     * Calcule le prix après remise.
     * Gère les entrées invalides (pourcentage > 100%).
     */
    public double calculateDiscount(double price, double discountPercentage) {
        if (price < 0) {
            throw new IllegalArgumentException("Erreur: Le prix ne peut pas être négatif.");
        }
if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Erreur: Le pourcentage de remise doit être compris entre 0 et 100.");
        }
        
        double discountAmount = price * (discountPercentage / 100.0);
        
        // Arrondi à deux décimales pour la monnaie (bonne pratique)
        return Math.round((price - discountAmount) * 100.0) / 100.0;
    }
/**
     * Calcule le prix original (non remisé) à partir du prix final et du pourcentage.
     */
    public double calculateOriginalPrice(double finalPrice, double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage >= 100) {
            throw new IllegalArgumentException("Le pourcentage doit être compris entre 0 et 99.99%.");
        }
        if (finalPrice < 0) {
throw new IllegalArgumentException("Le prix final ne peut pas être négatif.");
        }
        
        double multiplier = 1.0 - (discountPercentage / 100.0);
        
        // Arrondi à deux décimales (car les prix sont monétaires)
        return Math.round((finalPrice / multiplier) * 100.0) / 100.0;
    }
}
