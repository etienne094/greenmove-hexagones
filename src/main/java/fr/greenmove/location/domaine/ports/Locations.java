package fr.greenmove.location.domaine.ports;


import fr.greenmove.location.domaine.models.IdLocation;
import fr.greenmove.location.domaine.models.IdUsager;
import fr.greenmove.location.domaine.models.Location;

import java.util.Optional;

public interface Locations {

    void enregistrer(Location location);

    Optional<Location> parId(IdLocation id);

    boolean aUneLocationEnCours(IdUsager usager);

    IdLocation prochainIdentifiant();
}
