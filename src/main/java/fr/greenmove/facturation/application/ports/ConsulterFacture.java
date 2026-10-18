package fr.greenmove.facturation.application.ports;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import java.util.Optional;

public interface ConsulterFacture {
    Facture consulter(IdFacture idFacture);
    Optional<Facture> consulterParLocation(IdLocation idLocation);
}
