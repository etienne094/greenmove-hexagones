package fr.greenmove.location.domaine;

import fr.greenmove.location.domaine.evenements.LocationTerminee;

public interface PublicationEvenements {

    void publier(LocationTerminee evenement);
}
