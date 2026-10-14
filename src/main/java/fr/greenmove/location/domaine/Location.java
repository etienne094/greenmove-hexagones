package fr.greenmove.location.domaine;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

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

    public IdLocation id(){return id;}
    public IdUsager usager(){return usager;}
    public IdVehicule vehicule(){return vehicule;}
    public TypeVehicule type(){return type;}
    public Instant debut(){return debut;}
    public Optional<Instant> fin() {return Optional.ofNullable(fin);}
    public Statut statut() {return statut;}

}
