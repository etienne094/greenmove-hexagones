package fr.greenmove.facturation.application.ports;

import fr.greenmove.facturation.domaine.*;

public interface EmettreFacture {
    Facture emettre(IdUsager idUsager, IdLocation idLocation, long nbMinutes, TypeVehicule typeVehicule);
}
