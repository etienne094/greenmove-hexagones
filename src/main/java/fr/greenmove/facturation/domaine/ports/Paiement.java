package fr.greenmove.facturation.domaine.ports;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;

import java.util.Optional;

public interface Paiement {
    Optional<String> debiter(IdUsager idUsager, Montant montant);
}
