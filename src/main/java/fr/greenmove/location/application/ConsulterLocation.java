package fr.greenmove.location.application;

import fr.greenmove.location.domaine.models.IdLocation;
import fr.greenmove.location.domaine.models.Location;

public interface ConsulterLocation {

    Location consulter(IdLocation id);

}
