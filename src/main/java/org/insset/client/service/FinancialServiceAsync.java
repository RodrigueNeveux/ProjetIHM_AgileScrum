package org.insset.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface asynchrone utilisée par le client GWT pour les appels RPC.
 */
public interface FinancialServiceAsync {/**
     * Effectue une division. (US 9)
     */
    void calculateDivision(Double num, Double denom, AsyncCallback<Double> callback);

    /**
     * Calcule le prix après remise. (US 10)
     */
void calculateDiscount(Double price, Double percentage, AsyncCallback<Double> callback);
    
    /** NOUVEAU : Calcule le prix original (Inverse Remise) */
void calculateOriginalPrice(Double finalPrice, Double percentage, AsyncCallback<Double> callback);
}
