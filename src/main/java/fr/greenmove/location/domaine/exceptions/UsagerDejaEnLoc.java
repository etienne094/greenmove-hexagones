package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdUsager;

public class UsagerDejaEnLoc extends RuntimeException{

    public UsagerDejaEnLoc(IdUsager id){
        super ("L'utilisateur " + id.valeur() + " est déjà une location en cours");
    }
}
