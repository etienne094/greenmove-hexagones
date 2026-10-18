package fr.greenmove.facturation.domaine;

public class FactureIntrouvable extends RuntimeException {
    public FactureIntrouvable(IdFacture idFacture) {
        super("Facture " + idFacture.valeur() + " introuvable");
    }
}
