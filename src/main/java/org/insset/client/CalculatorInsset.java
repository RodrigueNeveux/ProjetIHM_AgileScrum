package org.insset.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window; // Ajout pour les Pop-ups
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;import com.google.gwt.user.client.ui.Label;
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
 * Implémente la séparation des vues, les Pop-ups, les erreurs en rouge et les placeholders.
 */
public class CalculatorInsset implements EntryPoint {

    // -------------------------------------------------------------------------
    // DÉCLARATION DES WIDGETS ET SERVICES
    // -------------------------------------------------------------------------
// Module 1: Conversion Chiffres Romains
    private final TextBox integerInput = new TextBox();
    private final TextBox romanInput = new TextBox();
    private final Label romanOutput = new Label();
    private final Label integerOutput = new Label();
    private final Button clearButton = new Button("Clear");
private final Button convertIntButton = new Button("Convertir Entier"); 
    private final Button convertRomanButton = new Button("Convertir Romain");
    private final TextBox dateInput = new TextBox();
    private final Button convertDateButton = new Button("Convertir Date");
    private final Label dateOutput = new Label();
private final TextBox divisionInput1 = new TextBox();
    private final TextBox divisionInput2 = new TextBox();
    private final Button divideButton = new Button("Diviser");
    private final Label divisionOutput = new Label();
    private final TextBox priceInput = new TextBox();
    private final TextBox discountInput = new TextBox();
private final Button discountButton = new Button("Calculer Remise");
    private final Label discountOutput = new Label();
    private final TextBox originalPriceInput = new TextBox();
    private final TextBox originalDiscountInput = new TextBox();
    private final Button originalPriceButton = new Button("Prix Non Remisé");
    private final Label originalPriceOutput = new Label();
// Zone d'affichage des messages/erreurs (partagée)
    private final Label outputLabel = new Label("Messages/Statut: ");
    
    // Services RPC
    private final RomanConverterServiceAsync romanService = GWT.create(RomanConverterService.class);
    private final FinancialServiceAsync financialService = GWT.create(FinancialService.class);
// Labels d'Erreur Spécifiques (pour l'UX locale)
    private final Label errorIntLabel = new Label();
    private final Label errorRomanLabel = new Label();
    private final Label errorDateLabel = new Label();
    private final Label divisionErrorLabel = new Label();
    private final Label discountErrorLabel = new Label();
    private final Label originalPriceErrorLabel = new Label();
// -------------------------------------------------------------------------
    // GESTIONNAIRE DE VUES ET NAVIGATION
    // -------------------------------------------------------------------------
    private void showView(Widget view) {
        // Permet de basculer entre les modules 1 et 2
        RootPanel.get().clear();
        RootPanel.get().add(view);
    }
@Override
    public void onModuleLoad() { 
        // METHODE OBLIGATOIRE DE ENTRYPOINT

        // --- Construction des Vues ---
        final VerticalPanel module1 = buildModule1UI();
        final VerticalPanel module2 = buildModule2UI();
// --- BOUTONS DE NAVIGATION (Pour séparation des vues) ---
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
        integerInput.getElement().setAttribute("placeholder", "ex: 1984");
        module1Panel.add(new Label("Entier (1-2000) vers Romain:"));
        module1Panel.add(integerInput);
        module1Panel.add(errorIntLabel); // Label d'erreur local
        module1Panel.add(convertIntButton); 
        module1Panel.add(romanOutput);
// Romain -> Entier
        romanInput.getElement().setAttribute("placeholder", "ex: MCMXCIV");
        module1Panel.add(new Label("Chiffre Romain (I-MM) vers Entier:"));
        module1Panel.add(romanInput);
        module1Panel.add(errorRomanLabel); // Label d'erreur local
        module1Panel.add(convertRomanButton); 
        module1Panel.add(integerOutput);
// Date de Naissance
        dateInput.getElement().setAttribute("placeholder", "jj/mm/aaaa");
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
        configureDateConversionHandler(); // Handler pour la date

        return module1Panel;
}
    
    // Handler du bouton Clear
    private void configureClearButton() {
        clearButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Nettoyage Module 1
                integerInput.setText(""); 
                romanInput.setText("");
                dateInput.setText("");
romanOutput.setText("");
                integerOutput.setText("");
                dateOutput.setText("");
                
                // Nettoyage Module 2
                divisionInput1.setText(""); 
                divisionInput2.setText("");
                priceInput.setText("");
                discountInput.setText("");
originalPriceInput.setText(""); 
                originalDiscountInput.setText("");
                divisionOutput.setText("");
                discountOutput.setText("");
                originalPriceOutput.setText("");

                // Reset des erreurs (STYLE ROUGE)
                setErrorStyle(errorIntLabel, "");
setErrorStyle(errorRomanLabel, "");
                setErrorStyle(errorDateLabel, "");
                setErrorStyle(divisionErrorLabel, "");
                setErrorStyle(discountErrorLabel, "");
                setErrorStyle(originalPriceErrorLabel, "");
                
                outputLabel.setText("Champs réinitialisés.");
            }
        });
    }

// Handler Entier -> Romain (UX Optimisée)
    private void configureRomanIntConversionHandler() {
        convertIntButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String inputStr = integerInput.getText().trim();
                outputLabel.setText("Conversion Entier -> Romain en cours...");
                setErrorStyle(errorIntLabel, ""); // Effacer l'erreur précédente

try {
                    int number = Integer.parseInt(inputStr);

                    romanService.convertArabeToRoman(number, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            setErrorStyle(errorIntLabel, "ERREUR: " + caught.getMessage()); // STYLE ROUGE
                            outputLabel.setText("Échec de la Conversion.");
romanOutput.setText("");
                        }

                        @Override
                        public void onSuccess(String result) {
                            Window.alert("Conversion réussie :\n" + result); // POP-UP SUCCÈS
                            romanOutput.setText(""); 
                            outputLabel.setText("Conversion réussie.");
                        }
                    });
} catch (NumberFormatException e) {
                    setErrorStyle(errorIntLabel, "ERREUR: Entrez un nombre entier valide."); // STYLE ROUGE
                    outputLabel.setText("Erreur de format.");
                }
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
                setErrorStyle(errorRomanLabel, ""); // Effacer l'erreur précédente

                romanService.convertRomanToArabe(inputStr, new AsyncCallback<Integer>() {
                    @Override
public void onFailure(Throwable caught) {
                        setErrorStyle(errorRomanLabel, "ERREUR: " + caught.getMessage()); // STYLE ROUGE
                        outputLabel.setText("Échec de la Conversion.");
                        integerOutput.setText("");
                    }

                    @Override
public void onSuccess(Integer result) {
                        Window.alert("Résultat entier :\n" + result); // POP-UP SUCCÈS
                        integerOutput.setText("");
                        outputLabel.setText("Conversion Romain -> Entier réussie.");
                    }
                });
            }
        });
    }

    // Handler Date de Naissance (UX Optimisée)
private void configureDateConversionHandler() {
        convertDateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String inputStr = dateInput.getText().trim();
                outputLabel.setText("Conversion de date en cours...");
                setErrorStyle(errorDateLabel, ""); // Effacer l'erreur précédente
romanService.convertDateYears(inputStr, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        setErrorStyle(errorDateLabel, "ERREUR: " + caught.getMessage()); // STYLE ROUGE
                        outputLabel.setText("Échec de la conversion de date.");
                        dateOutput.setText("");
                    }
@Override
                    public void onSuccess(String result) {
                        Window.alert("Date Romaine :\n" + result); // POP-UP SUCCÈS
                        dateOutput.setText("");
                        outputLabel.setText("Conversion de date réussie.");
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
// Section Division
        divisionInput1.getElement().setAttribute("placeholder", "Numerateur");
        divisionInput2.getElement().setAttribute("placeholder", "Dénominateur");
        module2Panel.add(new Label("Division (Numerateur / Dénominateur)"));
        module2Panel.add(divisionInput1);
        module2Panel.add(divisionInput2);
        module2Panel.add(divisionErrorLabel); // Label d'erreur local
module2Panel.add(divideButton);
        module2Panel.add(divisionOutput);

        module2Panel.add(new Label("--- Remise ---"));

        // Section Remise
        priceInput.getElement().setAttribute("placeholder", "Prix de base");
        discountInput.getElement().setAttribute("placeholder", "% Remise");
        module2Panel.add(new Label("Calcul de Remise (Prix / % Remise)"));
        module2Panel.add(priceInput);
module2Panel.add(discountInput);
        module2Panel.add(discountErrorLabel); // Label d'erreur local
        module2Panel.add(discountButton);
        module2Panel.add(discountOutput);
        
        // Section Remise Inverse
originalPriceInput.getElement().setAttribute("placeholder", "Prix final payé");
        originalDiscountInput.getElement().setAttribute("placeholder", "% Remise");
        module2Panel.add(new Label("--- Remise Inverse ---"));
        module2Panel.add(new Label("Prix Final / % Remise (Trouver Prix de Base)"));
        module2Panel.add(originalPriceInput);
        module2Panel.add(originalDiscountInput);
module2Panel.add(originalPriceErrorLabel); 
        module2Panel.add(originalPriceButton);
        module2Panel.add(originalPriceOutput);
        
        // Ajout du Clear Button au Module 2
        module2Panel.add(clearButton);
        
        configureDivisionHandler();
        configureDiscountHandler();
        configureOriginalPriceHandler();
return module2Panel;
    }
    
    // Handler Division (UX Optimisée)
    private void configureDivisionHandler() {
        divideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                outputLabel.setText("Calcul Division en cours...");
                setErrorStyle(divisionErrorLabel, ""); 
                divisionOutput.setText("");

try {
                    Double num = Double.parseDouble(divisionInput1.getText().trim());
                    Double denom = Double.parseDouble(divisionInput2.getText().trim());

                    financialService.calculateDivision(num, denom, new AsyncCallback<Double>() {
                        @Override
public void onFailure(Throwable caught) {
                            setErrorStyle(divisionErrorLabel, "ERREUR: " + caught.getMessage()); // STYLE ROUGE
                            outputLabel.setText("Échec de la Division.");
                            divisionOutput.setText("");
                        }

                        @Override

public void onSuccess(Double result) {
                            Window.alert("Résultat Division: " + result); // POP-UP SUCCÈS
                            divisionOutput.setText("");
                            outputLabel.setText("Division réussie.");
                        }
                    });
} catch (NumberFormatException e) {
                    setErrorStyle(divisionErrorLabel, "ERREUR: Entrées de division non valides."); // STYLE ROUGE
                    outputLabel.setText("Erreur: Entrées non valides.");
                }
            }
        });
    }

    // Handler Remise (UX Optimisée)
private void configureDiscountHandler() {
        discountButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                outputLabel.setText("Calcul Remise en cours...");
                setErrorStyle(discountErrorLabel, ""); 
                discountOutput.setText("");

                try {
// 1. Récupération des entrées (pour le calcul des détails côté client)
                    Double price = Double.parseDouble(priceInput.getText().trim());
                    Double discount = Double.parseDouble(discountInput.getText().trim());
                    
                    // 2. Appel RPC
                    financialService.calculateDiscount(price, discount, new AsyncCallback<Double>() {
                        @Override
public void onFailure(Throwable caught) {
                            setErrorStyle(discountErrorLabel, "ERREUR: " + caught.getMessage()); // STYLE ROUGE
                            outputLabel.setText("Échec de la Remise.");
                            discountOutput.setText("");
                        }

                        @Override
                        public void onSuccess(Double prixFinal) {

// 3. Calcul des détails pour le Pop-up
                            double montantRemise = price * (discount / 100.0);
                            double gain = price - prixFinal;
                            
                            String message = "--- Détails de la Remise ---\n" +
                                             "Prix de base: " + price + "\n" +
"Pourcentage de remise: " + discount + "%\n" + 
                                             "Montant de la remise: " + montantRemise + "\n" + 
                                             "Gain client: " + gain + "\n" +
                                             "Prix après réduction: " + prixFinal;
                            
                            Window.alert(message); // POP-UP SUCCÈS COMPLET
                            
                            discountOutput.setText("");
outputLabel.setText("Remise calculée avec succès.");
                            discountErrorLabel.setText("");
                        }
                    });
                } catch (NumberFormatException e) {
                    setErrorStyle(discountErrorLabel, "ERREUR: Entrées de remise non valides."); // STYLE ROUGE
                    outputLabel.setText("Erreur de format.");
                }
            }
        });
    }
// Handler Remise Inverse (UX Optimisée)
    private void configureOriginalPriceHandler() {
        originalPriceButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                outputLabel.setText("Calcul du prix original en cours...");
                setErrorStyle(originalPriceErrorLabel, ""); 
                originalPriceOutput.setText("");

                try {
Double finalPrice = Double.parseDouble(originalPriceInput.getText().trim());
                    Double discount = Double.parseDouble(originalDiscountInput.getText().trim());

                    financialService.calculateOriginalPrice(finalPrice, discount, new AsyncCallback<Double>() {
                        @Override
                        public void onFailure(Throwable caught) {
setErrorStyle(originalPriceErrorLabel, "ERREUR: " + caught.getMessage()); // STYLE ROUGE
                            outputLabel.setText("Échec du calcul du prix original.");
                            originalPriceOutput.setText("");
                        }

                        @Override
                        public void onSuccess(Double originalPrice) {
                            // Affichage détaillé dans le Pop-up
String message = "--- Détails de la Remise Inverse ---\n" +
                                             "Prix Final remisé: " + finalPrice + "\n" +
                                             "Pourcentage de remise: " + discount + "%\n" +
                                             "Prix Original (Non remisé): " + originalPrice;
                            
                            Window.alert(message); // POP-UP SUCCÈS
originalPriceOutput.setText(""); 
                            outputLabel.setText("Prix original calculé avec succès.");
                            originalPriceErrorLabel.setText("");
                        }
                    });
                } catch (NumberFormatException e) {
setErrorStyle(originalPriceErrorLabel, "ERREUR: Entrées non valides."); // STYLE ROUGE
                    outputLabel.setText("Erreur de format.");
                }
            }
        });
    }
    
    /**
     * Méthode utilitaire pour appliquer le style rouge aux erreurs.
     */
private void setErrorStyle(Label label, String text) {
        label.setText(text);
        if (text != null && text.startsWith("ERREUR:")) {
            // Applique une couleur de texte rouge
            label.getElement().getStyle().setColor("red");
        } else {
            // Rétablit le style par défaut (noir)
            label.getElement().getStyle().clearColor();
        }
    }
}
