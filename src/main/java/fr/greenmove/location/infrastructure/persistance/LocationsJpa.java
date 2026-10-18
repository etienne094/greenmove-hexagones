package fr.greenmove.location.infrastructure.persistance;

import fr.greenmove.location.domaine.models.*;
import fr.greenmove.location.domaine.ports.Locations;

import java.util.Optional;

public class LocationsJpa implements Locations {

    private final LocationJpaRepository jpa;

    public LocationsJpa(LocationJpaRepository jpa){
        this.jpa = jpa;
    }

    @Override
    public IdLocation prochainIdentifiant(){
        return new IdLocation(String.format("L-%04d", jpa.count() +1));
    }

    @Override
    public void enregistrer(fr.greenmove.location.domaine.models.Location location){
        jpa.save(versEntite(location));
    }

    @Override
    public Optional<fr.greenmove.location.domaine.models.Location> parId(IdLocation id){
        return jpa.findById(id.valeur()).map(this::versDomaine);
    }

    @Override
    public boolean aUneLocationEnCours(IdUsager usager){
        return jpa.existsByUsagerAndStatut(usager.valeur(), Statut.EN_COURS.name());
    }

    //le mapper

    private LocationEntity versEntite(Location location){
        LocationEntity e = new LocationEntity();
        e.id = location.id().valeur();
        e.usager = location.usager().valeur();
        e.vehicule = location.vehicule().valeur();
        e.type = location.type().name();
        e.debut = location.debut();
        e.fin = location.fin().orElse(null);
        e.statut = location.statut().name();
        return e;
    }

    private Location versDomaine(LocationEntity e){
        return Location.reconstituer(
                new IdLocation(e.id),
                new IdUsager(e.usager),
                new IdVehicule(e.vehicule),
                TypeVehicule.valueOf(e.type),
                e.debut,
                e.fin,
                Statut.valueOf(e.statut)
        );

    }
}
