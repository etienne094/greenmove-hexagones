package fr.greenmove.location.domaine;

public class VehiculeIndisponible  extends RuntimeException{
    public VehiculeIndisponible(IdVehicule id){
        super("Le vehicule " + id.valeur() + " est indisponible");
    }
}
