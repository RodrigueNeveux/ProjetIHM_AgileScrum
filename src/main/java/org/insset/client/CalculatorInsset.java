package org.insset.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window; // Ajout pour les Pop-ups
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.insset.client.service.FinancialService;
import org.insset.client.service.FinancialServiceAsync;
import org.insset.client.service.RomanConverterService;
import org.insset.client.service.RomanConverterServiceAsync;

/**
 * Point d'entrée principal de l'application IHM (Méthode Agile Scrum).
 * Implémente la séparation des vues et les Pop-ups pour les résultats.
 */
public class CalculatorInsset implements EntryPoint {

    // -------------------------------------------------------------------------
    // DÉCLARATION DES WIDGETS ET SERVICES
    // -------------------------------------------------------------------------
    
    // Module 1: Conversion Chiffres Romains (US 1, 2, 3, 4, 8)
private final TextBox integerInput = new TextBox();
    private final TextBox romanInput = new TextBox();
    private final Label romanOutput = new Label("Résultat Romain: ");
    private final Label integerOutput = new Label("Résultat Entier: ");
    private final Button clearButton = new Button("Clear"); 
    private final Button convertIntButton = new Button("Convertir Entier"); 
    private final Button convertRomanButton = new Button("Convertir Romain");
private final TextBox dateInput = new TextBox();
private final Button convertDateButton = new Button("Convertir Date");
private final Label dateOutput = new Label("Date Romaine: ");

// Labels d'Erreur (ces erreurs sont résolues en les déclarant correctement)

// Module 2: Calculs Financiers (US 9, 10)
    private final TextBox divisionInput1 = new TextBox();
    private final TextBox divisionInput2 = new TextBox();
    private final Button divideButton = new Button("Diviser");
    private final Label divisionOutput = new Label("Résultat Division: ");
    private final TextBox priceInput = new TextBox();
private final TextBox discountInput = new TextBox();
    private final Button discountButton = new Button("Calculer Remise");
    private final Label discountOutput = new Label("Prix Final: ");
private final TextBox originalPriceInput = new TextBox();
private final TextBox originalDiscountInput = new TextBox();
private final Button originalPriceButton = new Button("Prix Non Remisé");
private final Label originalPriceOutput = new Label("Prix Original: ");
private final Label originalPriceErrorLabel = new Label();

    
    // Zone d'affichage des messages/erreurs (partagée)
    private final Label outputLabel = new Label("Messages/Erreurs: ");
// Services RPC
    private final RomanConverterServiceAsync romanService = GWT.create(RomanConverterService.class);
    private final FinancialServiceAsync financialService = GWT.create(FinancialService.class);

    // NOUVEAU: Labels d'Erreur Spécifiques (pour l'UX locale)
    private final Label errorIntLabel = new Label();
    private final Label errorRomanLabel = new Label();
    private final Label errorDateLabel = new Label(); // Si Date est implémentée
private final Label romanConversionErrorLabel = new Label(); // NOUVEAU!
private final Label romanConversionStatusLabel = new Label(); // NOUVEAU!
private final Label divisionErrorLabel = new Label(); // NOUVEAU!
private final Label discountErrorLabel = new Label(); // NOUVEAU!

// -------------------------------------------------------------------------
    // GESTIONNAIRE DE VUES ET NAVIGATION
    // -------------------------------------------------------------------------
    private void showView(Widget view) {
        // Permet de basculer entre les modules 1 et 2 sans effacer les boutons de navigation
        RootPanel.get().clear();
        RootPanel.get().add(view);
    }
    @Override
public void onModuleLoad() { 
        // METHODE OBLIGATOIRE DE ENTRYPOINT

        // --- Construction des Vues ---
        final VerticalPanel module1 = buildModule1UI();
        final VerticalPanel module2 = buildModule2UI();
// --- BOUTONS DE NAVIGATION (Ajoutés à la racine pour la séparation) ---
        Button btnModule1 = new Button("Module 1: Conversion");
        Button btnModule2 = new Button("Module 2: Finance");
        
        // Ajout des boutons de navigation au RootPanel
        RootPanel.get().add(btnModule1);
        RootPanel.get().add(btnModule2);
        
        // Logique de CLIC : gérer le masquage/affichage
btnModule1.addClickHandler(e -> {
            RootPanel.get().clear();
            RootPanel.get().add(btnModule1); // Rajoute les boutons avant la vue
            RootPanel.get().add(btnModule2);
            RootPanel.get().add(module1);
        });

        btnModule2.addClickHandler(e -> {
            RootPanel.get().clear();
            RootPanel.get().add(btnModule1);
            RootPanel.get().add(btnModule2);
            RootPanel.get().add(module2);
        });
// AFFICHER LE MODULE 1 PAR DÉFAUT
        RootPanel.get().add(module1);
    } 
    
    // -------------------------------------------------------------------------
    // MODULE 1 : ROMAIN (US 1, 2, 3, 4, 8)
    // -------------------------------------------------------------------------

    private VerticalPanel buildModule1UI() {
        VerticalPanel module1Panel = new VerticalPanel();
        module1Panel.setSpacing(5);
module1Panel.add(new Label("--- Module 1: Conversion Chiffres Romains ---"));
        
        // Entier -> Romain
        module1Panel.add(new Label("Entier (1-2000) vers Romain:"));
        module1Panel.add(integerInput);
        module1Panel.add(errorIntLabel); // Label d'erreur local
        module1Panel.add(convertIntButton); 
        module1Panel.add(romanOutput);
// Romain -> Entier
        module1Panel.add(new Label("Chiffre Romain (I-MM) vers Entier:"));
        module1Panel.add(romanInput);
        module1Panel.add(errorRomanLabel); // Label d'erreur local
        module1Panel.add(convertRomanButton); 
        module1Panel.add(integerOutput);
// NOUVEAU: Date de Naissance (Placeholders)
        module1Panel.add(new Label("Date de Naissance (jj/mm/aaaa)"));
        module1Panel.add(dateInput);
        module1Panel.add(errorDateLabel); 
        module1Panel.add(convertDateButton);
        module1Panel.add(dateOutput);
// Bouton Clear et messages généraux
        module1Panel.add(clearButton);
        module1Panel.add(outputLabel);

        // Configuration des handlers
        configureClearButton(); 
        configureRomanIntConversionHandler(); 
        configureRomanToIntegerConversionHandler(); 
configureDateConversionHandler(); // <-- VÉRIFIEZ QUE CETTE LIGNE EST PRÉSENTE !


        return module1Panel;
    }

    // Handler Entier -> Romain (UX Optimisée)
    private void configureRomanIntConversionHandler() {
        convertIntButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String inputStr = integerInput.getText().trim();
                outputLabel.setText("Conversion Entier -> Romain en cours...");
                errorIntLabel.setText(""); // Effacer l'erreur précédente
try {
                    int number = Integer.parseInt(inputStr);

                    romanService.convertArabeToRoman(number, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            // Affichage de l'erreur LOCALISÉE
setErrorStyle(errorIntLabel, "ERREUR: " + caught.getMessage());
                            outputLabel.setText("Échec de la Conversion.");
                            romanOutput.setText("ÉCHEC");
                        }

                        @Override
                        public void onSuccess(String result) {
Window.alert("Conversion réussie :\n" + result); // POP-UP SUCCÈS
                            // SUPPRESSION DE LA DOUBLE AFFICHAGE DANS LE LABEL
                            outputLabel.setText("Conversion réussie.");
                        }
                    });
} catch (NumberFormatException e) {
                    // Affichage de l'erreur LOCALISÉE
setErrorStyle(errorIntLabel, "ERREUR: Entrez un nombre entier valide.");
                    outputLabel.setText("Erreur de format.");
                }
            }
        });
    }


// Handler Date de Naissance (Pop-up)
    private void configureDateConversionHandler() {
        convertDateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
final String inputStr = dateInput.getText().trim();
                outputLabel.setText("Conversion de date en cours...");
                errorDateLabel.setText(""); // Effacer l'erreur précédente

                // Validation de format de date intégrée dans le service
                romanService.convertDateYears(inputStr, new AsyncCallback<String>() {
                    @Override
public void onFailure(Throwable caught) {
                        // Affichage de l'erreur LOCALISÉE
                        errorDateLabel.setText("ERREUR: " + caught.getMessage());
                        outputLabel.setText("Échec de la conversion de date.");
                        dateOutput.setText("ÉCHEC");
                    }
@Override
                    public void onSuccess(String result) {
                        Window.alert("Date Romaine :\n" + result); // POP-UP SUCCÈS
                        outputLabel.setText("Conversion de date réussie.");
                    }
                });
            }
        });
    }


// Handler Romain -> Entier (UX Optimisée)
    private void configureRomanToIntegerConversionHandler() {
        convertRomanButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String inputStr = romanInput.getText().trim();
                outputLabel.setText("Conversion Romain -> Entier en cours...");
                errorRomanLabel.setText(""); // Effacer l'erreur précédente
// L'appel RPC utilise le service pour la conversion Romain -> Entier
                romanService.convertRomanToArabe(inputStr, new AsyncCallback<Integer>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // Affichage de l'erreur LOCALISÉE
errorRomanLabel.setText("ERREUR: " + caught.getMessage());
                        outputLabel.setText("Échec de la Conversion.");
                        integerOutput.setText("ÉCHEC");
                    }

                    @Override
                    public void onSuccess(Integer result) {
Window.alert("Résultat entier :\n" + result); // POP-UP SUCCÈS
                        // SUPPRESSION DE LA DOUBLE AFFICHAGE DANS LE LABEL
                        outputLabel.setText("Conversion Romain -> Entier réussie.");
                    }
                });
            }
        });
    }
// -------------------------------------------------------------------------
    // MODULE 2 : CALCULS FINANCIERS (US 9, US 10, US 11)
    // -------------------------------------------------------------------------

    private VerticalPanel buildModule2UI() {
        VerticalPanel module2Panel = new VerticalPanel();
module2Panel.setSpacing(5);
        module2Panel.add(new Label("--- Module 2: Calculs Financiers ---"));

        // Section Division (US 9)
        module2Panel.add(new Label("Division (Numerateur / Dénominateur)"));
        module2Panel.add(divisionInput1);
        module2Panel.add(divisionInput2);
module2Panel.add(divisionErrorLabel); // AJOUTÉ SOUS LES ENTRÉES
        module2Panel.add(divideButton);
        module2Panel.add(divisionOutput);
module2Panel.add(new Label("--- Remise ---"));

        // Section Remise (US 10)
        module2Panel.add(new Label("Calcul de Remise (Prix / % Remise)"));
        module2Panel.add(priceInput);
        module2Panel.add(discountInput);
module2Panel.add(discountErrorLabel); // AJOUTÉ SOUS LES ENTRÉES
        module2Panel.add(discountButton);
        module2Panel.add(discountOutput);

// NOUVEAU : REMISE INVERSE (Prix Final -> Prix Original)
        module2Panel.add(new Label("--- Remise Inverse ---"));
        module2Panel.add(new Label("Prix Final / % Remise (Trouver Prix de Base)"));
        module2Panel.add(originalPriceInput); // Champ Prix Final
        module2Panel.add(originalDiscountInput); // Champ % Remise
module2Panel.add(originalPriceErrorLabel); // Label d'erreur local
        module2Panel.add(originalPriceButton); // Bouton de calcul
        module2Panel.add(originalPriceOutput); // Résultat

module2Panel.add(clearButton); // <-- AJOUT DU BOUTON CLEAR
        module2Panel.add(outputLabel); // <-- AJOUT DU LABEL D'ÉTAT
configureDivisionHandler();
        configureDiscountHandler();
configureOriginalPriceHandler(); // <-- AJOUTEZ CET APPEL        

        return module2Panel;
    }
    
    // Handler Division (Pop-up - UX Optimisée
private void configureDivisionHandler() {
        divideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                outputLabel.setText("Calcul Division en cours...");
                // Nettoyage de l'erreur précédente
                divisionErrorLabel.setText(""); 
                divisionOutput.setText("");
try {
                    Double num = Double.parseDouble(divisionInput1.getText().trim());
                    Double denom = Double.parseDouble(divisionInput2.getText().trim());

                    financialService.calculateDivision(num, denom, new AsyncCallback<Double>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            // Affichage de l'erreur LOCALISÉE
                            divisionErrorLabel.setText("ERREUR: " + caught.getMessage());
                            outputLabel.setText("Échec de la Division.");
                            divisionOutput.setText("ÉCHEC");
}

                        @Override
                        public void onSuccess(Double result) {
                            Window.alert("Résultat Division: " + result); // POP-UP SUCCÈS
                            divisionOutput.setText("Résultat Division: " + result);
                            outputLabel.setText("Division réussie.");
                        }
                    });
} catch (NumberFormatException e) {
                    // Affichage de l'erreur LOCALISÉE
                    divisionErrorLabel.setText("ERREUR: Entrées de division non valides.");
                    outputLabel.setText("Erreur: Entrées non valides.");
                }
            }
        });
    }
// Handler Remise (Pop-up - UX Optimisée)
    private void configureDiscountHandler() {
        discountButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                outputLabel.setText("Calcul Remise en cours...");
                // Nettoyage de l'erreur précédente
                discountErrorLabel.setText(""); 
                discountOutput.setText("");
try {
                    // 1. Récupération des entrées (pour le calcul des détails côté client)
                    Double price = Double.parseDouble(priceInput.getText().trim());
                    Double discount = Double.parseDouble(discountInput.getText().trim());
// 2. Appel RPC pour obtenir le Prix Final (le serveur gère les contraintes)
                    financialService.calculateDiscount(price, discount, new AsyncCallback<Double>() {
                        @Override
                        public void onFailure(Throwable caught) {
// Affichage de l'erreur LOCALISÉE (contrainte hors-plage)
                            discountErrorLabel.setText("ERREUR: " + caught.getMessage());
                            outputLabel.setText("Échec de la Remise.");
                            discountOutput.setText("ÉCHEC");
                        }
@Override
                        public void onSuccess(Double prixFinal) {
                            // 3. Calcul des détails pour le Pop-up
                            double montantRemise = price * (discount / 100.0);
                            double gain = price - prixFinal;
// CORRECTION : Remplacement de String.format() par la concaténation de base
    String message = "--- Détails de la Remise ---\n" +
                     "Prix de base: " + price + "\n" + 
                     "Pourcentage de remise: " + discount + "%\n" + 
                     "Montant de la remise: " + montantRemise + "\n" + 
                     "Gain client: " + gain + "\n" +
                     "Prix après réduction: " + prixFinal;
                            
                            Window.alert(message); // POP-UP SUCCÈS COMPLET
                            
                            // UX Finale : Afficher le prix final sur la page pour le statut, mais pas les détails
discountOutput.setText("Prix Final: " + prixFinal);
                            outputLabel.setText("Remise calculée avec succès.");
                            // Effacer le message d'erreur si la dernière opération était un succès
                            discountErrorLabel.setText("");
                        }
                    });
                } catch (NumberFormatException e) {
// Affichage de l'erreur LOCALISÉE
                    discountErrorLabel.setText("ERREUR: Entrées de remise non valides.");
                    outputLabel.setText("Erreur de format.");
                }
            }
        });
    }

private void configureOriginalPriceHandler() {
        originalPriceButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                outputLabel.setText("Calcul du prix original en cours...");
                // Nettoyage de l'erreur précédente
                originalPriceErrorLabel.setText(""); 
                originalPriceOutput.setText("");

                try {
Double finalPrice = Double.parseDouble(originalPriceInput.getText().trim());
                    Double discount = Double.parseDouble(originalDiscountInput.getText().trim());

                    financialService.calculateOriginalPrice(finalPrice, discount, new AsyncCallback<Double>() {
                        @Override
                        public void onFailure(Throwable caught) {originalPriceErrorLabel.setText("ERREUR: " + caught.getMessage());
                            outputLabel.setText("Échec du calcul du prix original.");
                            originalPriceOutput.setText("ÉCHEC");
                        }

                        @Override
                        public void onSuccess(Double originalPrice) {
                            // Affichage détaillé dans le Pop-up
String message = "--- Détails de la Remise Inverse ---\n" +
                                             "Prix Final remisé: " + finalPrice + "\n" +
                                             "Pourcentage de remise: " + discount + "%\n" +
                                             "Prix Original (Non remisé): " + originalPrice;
                            
                            Window.alert(message);
originalPriceOutput.setText("Prix Original: " + originalPrice); 
                            outputLabel.setText("Prix original calculé avec succès.");
                            originalPriceErrorLabel.setText("");
                        }
                    });
                } catch (NumberFormatException e) {
originalPriceErrorLabel.setText("ERREUR: Entrées non valides.");
                    outputLabel.setText("Erreur de format.");
                }
            }
        });
    }

// Handler du bouton Clear
    private void configureClearButton() {
        clearButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Nettoyage Module 1
                integerInput.setText(""); 
                romanInput.setText("");
romanOutput.setText("Résultat Romain: ");
                integerOutput.setText("Résultat Entier: ");
                errorIntLabel.setText(""); 
                errorRomanLabel.setText("");
                errorDateLabel.setText("");

                // Nettoyage Module 2
divisionInput1.setText(""); 
                divisionInput2.setText("");
                priceInput.setText("");
                discountInput.setText("");
                
                // AJOUTS POUR LA REMISE INVERSE (Prix Final -> Prix Original)
                originalPriceInput.setText(""); 
                originalDiscountInput.setText("");
                originalPriceOutput.setText("Prix Original: ");
                originalPriceErrorLabel.setText("");
divisionOutput.setText("Résultat Division: ");
                discountOutput.setText("Prix Final: ");
                outputLabel.setText("Champs réinitialisés.");
            }
        });
    }
// Dans CalculatorInsset.java, ajoutez cette méthode à la suite des autres méthodes privées:

    private void setErrorStyle(Label label, String text) {
        label.setText(text);
        if (text.startsWith("ERREUR:")) {
            // Applique une couleur de texte rouge
            label.getElement().getStyle().setColor("red");
        } else {
            // Rétablit le style par défaut (noir) pour les messages de statut
            label.getElement().getStyle().setColor("black");
        }
    }
}
