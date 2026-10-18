package fr.greenmove.facturation.domaine.ports;

import fr.greenmove.facturation.domaine.*;

import java.util.Optional;

public interface Factures {
    Optional<Facture> facture(IdFacture idFacture);
    void enregistrer(Facture facture);
    Optional<Facture> facturePourLocation(IdLocation location);
    boolean aUneFactureImpayee(IdUsager idUsager);
}
