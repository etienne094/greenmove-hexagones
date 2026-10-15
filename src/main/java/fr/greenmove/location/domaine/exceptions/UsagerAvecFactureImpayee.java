package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdUsager;

public class UsagerAvecFactureImpayee extends RuntimeException{

    public UsagerAvecFactureImpayee(IdUsager id){
        super ("L'utilisateur " + id.valeur() + " n'a pas payée la facture");
    }
}
