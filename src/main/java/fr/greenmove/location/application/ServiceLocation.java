package fr.greenmove.location.application;

import fr.greenmove.location.domaine.evenements.LocationTerminee;
import fr.greenmove.location.domaine.exceptions.*;
import fr.greenmove.location.domaine.models.*;
import fr.greenmove.location.domaine.ports.*;

public class ServiceLocation implements DemarrerLocation, TerminerLocation, ConsulterLocation {

    //ports sortants
    private final Locations locations;
    private final Flotte flotte;
    private final Usagers usagers;
    private final Impayes impayes;
    private final PublicationEvenements publication;
    private final Horloge horloge;


    public ServiceLocation(Locations locations, Flotte flotte, Usagers usagers, Impayes impayes, PublicationEvenements publication, Horloge horloge) {
        this.locations = locations;
        this.flotte = flotte;
        this.usagers = usagers;
        this.impayes = impayes;
        this.publication = publication;
        this.horloge = horloge;
    }

    @Override
    public Location demarrer(IdUsager usager, IdVehicule idVehicule){
        //vehicule existe?
        Vehicule vehicule = flotte.vehicule(idVehicule)
                .orElseThrow(() -> new VehiculeInconnu(idVehicule));

        //usager existe?
        if(!usagers.existe(usager)){
            throw new UsagerInconnu(usager);
        }

        //n'a pas déjà une location en cours?
        if(locations.aUneLocationEnCours(usager)){
            throw new UsagerDejaEnLoc(usager);
        }

        //N'a pas de facture impayée?
        if(impayes.aUneFactureImpayee(usager)){
            throw new UsagerAvecFactureImpayee(usager);
        }

        //
        Location location = Location.demarrer(locations.prochainIdentifiant(), usager, vehicule, horloge.maintenant());

        //on enregistre
        locations.enregistrer(location);
        return location;
    }

    @Override
    public Location terminer(IdLocation idLocation){
        //on retrouve la location
        Location location = locations.parId(idLocation)
                .orElseThrow(() -> new LocationInconnue(idLocation));

        //le domaine décide
        LocationTerminee evenement = location.terminer(horloge.maintenant());

        //on enregistre
        locations.enregistrer(location);

        //Publie l'événement pour facturation
        publication.publier(evenement);

        return location;
    }

    //Consulter
    @Override
    public Location consulter(IdLocation idLocation){
        return locations.parId(idLocation)
                .orElseThrow(() -> new LocationInconnue(idLocation));
    }
}


