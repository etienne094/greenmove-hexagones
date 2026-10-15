package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdVehicule;

public class VehiculeInconnu extends RuntimeException{
    public VehiculeInconnu(IdVehicule id){
        super("Le vehicule " + id.valeur() + " est inconnu");
    }
}
