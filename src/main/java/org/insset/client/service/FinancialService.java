package org.insset.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface synchrone pour les calculs financiers (Division et Remise).
 */
@RemoteServiceRelativePath("financialService") // Chemin d'accès du service RPC
public interface FinancialService extends RemoteService {

    /**
     * Effectue une division. (US 9)
     */
    Double calculateDivision(Double num, Double denom) throws IllegalArgumentException;

    /**
     * Calcule le prix après remise. (US 10)
     */
    Double calculateDiscount(Double price, Double percentage) throws IllegalArgumentException;

Double calculateOriginalPrice(Double finalPrice, Double percentage) throws IllegalArgumentException;

}
