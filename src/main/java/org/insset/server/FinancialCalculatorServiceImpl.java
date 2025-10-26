package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.FinancialService; // Implémente l'interface synchrone
import org.insset.server.finance.FinancialCalculator; // Import de votre classe de logique pure

/**
 * Implémentation du service RPC pour les calculs financiers.
 * @author user
 */@SuppressWarnings("serial")
public class FinancialCalculatorServiceImpl extends RemoteServiceServlet implements
        FinancialService {

    private final FinancialCalculator calculator = new FinancialCalculator();

    @Override
    public Double calculateDivision(Double num, Double denom) throws IllegalArgumentException {
        // US 9 : Déléguer à la logique métier (gère la division par zéro)
        if (num == null || denom == null) {
throw new IllegalArgumentException("Les entrées de division ne peuvent pas être nulles.");
        }
        return calculator.divide(num, denom);
    }

@Override
    public Double calculateOriginalPrice(Double finalPrice, Double percentage) throws IllegalArgumentException {
        // C'est l'implémentation RPC qui appelle votre logique pure (validée par TDD)
        if (finalPrice == null || percentage == null) {
            throw new IllegalArgumentException("Les entrées de prix original ne peuvent pas être nulles.");
        }
        return calculator.calculateOriginalPrice(finalPrice, percentage);
    }
@Override
    public Double calculateDiscount(Double price, Double percentage) throws IllegalArgumentException {
        // Appelle la logique métier de remise qui gère la contrainte % > 100
        if (price == null || percentage == null) {
            throw new IllegalArgumentException("Les entrées de remise ne peuvent pas être nulles.");
        }
        return calculator.calculateDiscount(price, percentage);
    }
}
