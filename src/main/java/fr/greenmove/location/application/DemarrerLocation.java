package fr.greenmove.location.application;

import fr.greenmove.location.domaine.models.IdUsager;
import fr.greenmove.location.domaine.models.IdVehicule;
import fr.greenmove.location.domaine.models.Location;

public interface DemarrerLocation {

    Location demarrer(IdUsager usager, IdVehicule vehicule);

}
