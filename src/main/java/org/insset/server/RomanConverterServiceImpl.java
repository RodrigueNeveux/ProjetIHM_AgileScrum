package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.RomanConverterService;
import org.insset.server.romain.RomanConverter; // Import de votre classe de logique pure

/**
 * Implémentation du service RPC pour la conversion des chiffres romains.
 * Gère l'US 8: Liaison entre le client IHM et la logique métier.
 *
 * @author user
 */
@SuppressWarnings("serial")
public class RomanConverterServiceImpl extends RemoteServiceServlet implements
        RomanConverterService {

    // Instanciation de votre classe de logique métier TDD
    private final RomanConverter converter = new RomanConverter();

@Override
public String convertDateYears(String nbr) throws IllegalArgumentException {
    // Appel final à la logique TDD de date (qui gère la validation du format)
    return converter.convertBirthDateToRoman(nbr); 
}

@Override
public Integer convertRomanToArabe(String nbr) throws IllegalArgumentException {
    // CORRECTION : Le nom de la méthode doit être 'convertRomanToInteger'
    return converter.convertRomanToInteger(nbr); 
}
@Override
    public String convertArabeToRoman(Integer nbr) throws IllegalArgumentException {
        // US 1/US 8: Implémentation de la logique de conversion Entier -> Romain (Validée par TDD)

        // 1. Validation de la contrainte de la plage du projet: 1 <= N <= 2000
        if (nbr == null || nbr < 1 || nbr > 2000) {
            throw new IllegalArgumentException("La valeur doit être un nombre compris entre 1 et 2000.");
        }
// 2. Appel à la logique métier (TDD)
        String romanResult = converter.convertIntegerToRoman(nbr);
        
        // Gère le cas où la logique pure ne retourne rien (bien que notre algo glouton ne devrait plus retourner null pour 1-2000)
        if (romanResult == null) {
            throw new IllegalArgumentException("Conversion impossible pour cette valeur.");
        }
return romanResult;
    }
}
