package fr.greenmove.location.domaine.exceptions;

import fr.greenmove.location.domaine.models.IdVehicule;

public class VehiculeIndisponible  extends RuntimeException{
    public VehiculeIndisponible(IdVehicule id){
        super("Le vehicule " + id.valeur() + " est indisponible");
    }
}
