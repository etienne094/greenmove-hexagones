package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdUsager;

public class UsagerInconnu extends RuntimeException{

    public UsagerInconnu(IdUsager id){
        super ("L'utilisateur " + id.valeur() + " est inconnu");
    }
}
