package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdLocation;

public class LocationInconnue extends RuntimeException{

    public LocationInconnue(IdLocation id){
        super ("La location " + id.valeur() + " est inconnue");
    }
}
