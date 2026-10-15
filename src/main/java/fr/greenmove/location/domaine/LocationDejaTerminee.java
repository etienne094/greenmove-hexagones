package fr.greenmove.location.domaine;

public class LocationDejaTerminee extends RuntimeException{
    public LocationDejaTerminee(IdLocation id){
        super("La location " + id.valeur() + " est terminée");
    }
}
