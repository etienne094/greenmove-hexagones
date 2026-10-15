package fr.greenmove.location.infrastructure.rest;


import fr.greenmove.location.application.ConsulterLocation;
import fr.greenmove.location.application.DemarrerLocation;
import fr.greenmove.location.application.TerminerLocation;
import fr.greenmove.location.domaine.models.IdLocation;
import fr.greenmove.location.domaine.models.IdUsager;
import fr.greenmove.location.domaine.models.IdVehicule;
import fr.greenmove.location.domaine.models.Location;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/locations")
public class LocationController {



    //ce qui entre et sort en JSON
    public record DemandeDemarrage(String usager, String vehicule) {}

    public record LocationDemarree(String id, String usager, String vehicule, String type,
                                   Instant debut, String statut){}

    public record LocationTerminee(String id, Instant fin, Long dureeMinutes, String statut){}

    public record LocationConsultee(String id, String usager, String vehicule, String type,
                                    Instant debut, Instant fin, Long dureeMinutes, String statut){}

    //Les ports entrants
    private final DemarrerLocation demarrerLocation;

    private final TerminerLocation terminerLocation;

    private final ConsulterLocation consulterLocation;

    //constructeur
    public LocationController(DemarrerLocation demarrerLocation, TerminerLocation terminerLocation, ConsulterLocation consulterLocation) {
        this.demarrerLocation = demarrerLocation;
        this.terminerLocation = terminerLocation;
        this.consulterLocation = consulterLocation;
    }

    //3 routes

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDemarree demarrer(@RequestBody DemandeDemarrage demande){
        Location l = demarrerLocation.demarrer(new IdUsager(demande.usager()), new IdVehicule(demande.vehicule()));
        return new LocationDemarree(l.id().valeur(), l.usager().valeur(), l.vehicule().valeur(),
                l.type().name(), l.debut(), l.statut().name());
    }

    @PostMapping("/{id}/fin")
    public LocationTerminee terminer(@PathVariable String id){
        Location l = terminerLocation.terminer(new IdLocation(id));
        return new LocationTerminee(l.id().valeur(), l.fin().orElse(null), duree(l), l.statut().name());
    }

    @GetMapping("/{id}")
    public LocationConsultee consulter(@PathVariable String id){
        Location l = consulterLocation.consulter(new IdLocation(id));
        return new LocationConsultee(l.id().valeur(), l.usager().valeur(), l.vehicule().valeur(),
                l.type().name(), l.debut(), l.fin().orElse(null), duree(l), l.statut().name());
    }

    private static Long duree(Location l){
        return l.dureeMinutes().isPresent() ? l.dureeMinutes().getAsLong() : null;
    }

}