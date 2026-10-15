package fr.greenmove.location.domaine.models;

import fr.greenmove.location.domaine.evenements.LocationTerminee;
import fr.greenmove.location.domaine.exceptions.FinAnterieureAuDebut;
import fr.greenmove.location.domaine.exceptions.LocationDejaTerminee;
import fr.greenmove.location.domaine.exceptions.VehiculeIndisponible;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public final class Location {

    private final IdLocation id;
    private final IdUsager usager;
    private final IdVehicule vehicule;
    private final TypeVehicule type;
    private final Instant debut;

    private Instant fin;
    private Statut statut;


    private Location(IdLocation id, IdUsager usager, IdVehicule vehicule, TypeVehicule type, Instant debut,
                    Instant fin, Statut statut) {
        this.id = Objects.requireNonNull(id, "id");
        this.usager = Objects.requireNonNull(usager, "usager");
        this.vehicule = Objects.requireNonNull(vehicule, "vehicule");
        this.type = Objects.requireNonNull(type, "type");
        this.debut = Objects.requireNonNull(debut, "debut");
        this.fin = fin;
        this.statut = Objects.requireNonNull(statut, "statut");
    }

    public static Location demarrer (IdLocation id, IdUsager usager, Vehicule vehicule, Instant debut){
        if(!vehicule.disponible()){
            throw new VehiculeIndisponible(vehicule.id());
        }
        return new Location(id, usager, vehicule.id(), vehicule.type(), debut, null, Statut.EN_COURS);
    }

    public LocationTerminee terminer (Instant fin){
        if(statut == Statut.TERMINEE){
            throw new LocationDejaTerminee(id);
        }
        if(fin.isBefore(debut)){
            throw new FinAnterieureAuDebut(debut, fin);
        }
        this.fin = fin;
        this.statut = Statut.TERMINEE;
        return new LocationTerminee(id.valeur(), usager.valeur(), type.name(), debut, fin);
    }

    public OptionalLong dureeMinutes(){
        if (fin == null){
            return OptionalLong.empty();
        }
        return OptionalLong.of(Duration.between(debut, fin).toMinutes());
    }

    public boolean estEnCours(){
        return statut == Statut.EN_COURS;
    }

    //recharge une location qui existe déjà en base, telle qu'elle a été enregistrée
    public static Location reconstituer(IdLocation id, IdUsager usager, IdVehicule vehicule, TypeVehicule type,
                                        Instant debut, Instant fin, Statut statut){
        return new Location(id, usager, vehicule, type, debut, fin, statut);
    }

    //Getters
    public IdLocation id(){return id;}
    public IdUsager usager(){return usager;}
    public IdVehicule vehicule(){return vehicule;}
    public TypeVehicule type(){return type;}
    public Instant debut(){return debut;}
    public Optional<Instant> fin() {return Optional.ofNullable(fin);}
    public Statut statut() {return statut;}



}
