package org.insset.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.History; 
import org.insset.client.HistoryListener;
/**
 * Point d'entrée de l'application IHM (Méthode Agile Scrum).
 * Implémentation de l'US 4 : Bouton Clear.
 */
public class CalculatorInsset implements EntryPoint {

    // -------------------------------------------------------------------------
    // 1. DÉCLARATION DES WIDGETS (ATTRIBUTS DE CLASSE)
    // -------------------------------------------------------------------------
private final TextBox integerInput = new TextBox();
    private final TextBox romanInput = new TextBox();
    private final Label outputLabel = new Label("Messages/Erreurs: ");
    private final Button clearButton = new Button("Clear"); 
    
    private final VerticalPanel module1Panel = new VerticalPanel();
/**
     * Point d'entrée principal de l'application GWT.
     * Cette méthode est appelée lorsque le module est chargé.
     */
    @Override
    public void onModuleLoad() {

        // --- 2. CONSTRUCTION DE L'IHM (Appel de la méthode de construction) ---
        buildModule1UI();
        
        // --- 3. GESTION DE L'HISTORIQUE (Code d'origine conservé) ---
        // Ce code gère la navigation et ne doit pas être modifié pour l'US 4.
        String initToken = History.getToken();
if (initToken.length() == 0) {
            History.newItem("exemple");
        }

        History.addValueChangeHandler(new HistoryListener());
        History.fireCurrentHistoryState();

//       RootPanel.get().add(new CalculatorPresenter());
    }

// -------------------------------------------------------------------------
    // 4. MÉTHODES PRIVÉES DE L'IHM (Logique et Construction)
    // -------------------------------------------------------------------------

    /**
     * Construit et configure l'interface utilisateur pour le Module 1 (Conversion).
     * Inclut l'attachement du bouton Clear.
     */
private void buildModule1UI() {
        
        module1Panel.setSpacing(5); 
        
        module1Panel.add(new Label("--- Module 1: Conversion Chiffres Romains ---"));
        
        // Entier -> Romain
        module1Panel.add(new Label("Entier (1-2000) vers Romain:"));
        module1Panel.add(integerInput);
// Romain -> Entier
        module1Panel.add(new Label("Chiffre Romain (I-MM) vers Entier:"));
        module1Panel.add(romanInput);
        
        // Affichage des résultats / erreurs
        module1Panel.add(outputLabel);
        
        // Ajout du bouton Clear (US 4)
        module1Panel.add(clearButton);
// Configuration du ClickHandler (Logique Clear)
        configureClearButton(); 

        // Attachement au HTML (Affichage du panneau)
        RootPanel.get().add(module1Panel);
    }
    
    /**
     * Configure le gestionnaire d'événements pour le bouton Clear.
     */
private void configureClearButton() {
        clearButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Logique de réinitialisation (US 4)
// Entrées
                integerInput.setText(""); 
                romanInput.setText("");
                
                // Sorties/Messages
                outputLabel.setText("Champs réinitialisés.");
            }
        });
    }
}
