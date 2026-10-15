package fr.greenmove.location.domaine;


import java.util.Optional;

public interface Locations {

    void enregistrer(Location location);

    Optional<Location> parId(IdLocation id);

    boolean aUneLocationEnCours(IdUsager usager);

    IdLocation prochainIdentifiant();
}
