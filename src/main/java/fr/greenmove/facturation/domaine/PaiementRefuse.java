package fr.greenmove.facturation.domaine;

public class PaiementRefuse extends RuntimeException {
    public PaiementRefuse(IdFacture id) {
        super("Le paiement pour la facture " + id.valeur() + " a été refusé");
    }
}
