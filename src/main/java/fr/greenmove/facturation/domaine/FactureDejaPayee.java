package fr.greenmove.facturation.domaine;

public class FactureDejaPayee extends RuntimeException {
    public FactureDejaPayee(IdFacture id) {
        super("La facture " + id.valeur() + " est déjà payée");
    }
}
