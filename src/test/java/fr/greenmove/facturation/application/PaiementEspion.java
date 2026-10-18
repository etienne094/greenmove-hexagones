package fr.greenmove.facturation.application;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;
import fr.greenmove.facturation.domaine.ports.Paiement;

import java.util.Optional;

class PaiementEspion implements Paiement {

    private boolean accepte = true;
    int nombreDeDebits = 0;
    Montant dernierMontantDebite;

    void refuserLesDebits() {accepte = false;}
    void accepterLesDebits() {accepte = true;}

    @Override
    public Optional<String> debiter(IdUsager idUsager, Montant montant) {
        nombreDeDebits++;
        dernierMontantDebite = montant;
        return accepte ? Optional.of("REF-" + nombreDeDebits) : Optional.empty();
    }
}
