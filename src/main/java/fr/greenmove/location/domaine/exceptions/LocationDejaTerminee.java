package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdLocation;

public class LocationDejaTerminee extends RuntimeException{
    public LocationDejaTerminee(IdLocation id){
        super("La location " + id.valeur() + " est terminée");
    }
}
