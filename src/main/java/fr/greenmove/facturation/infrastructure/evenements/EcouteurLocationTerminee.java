package fr.greenmove.facturation.infrastructure.evenements;

import fr.greenmove.facturation.application.ports.EmettreFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.TypeVehicule;
import fr.greenmove.location.domaine.evenements.LocationTerminee;
import org.springframework.context.event.EventListener;

import java.time.Duration;

public class EcouteurLocationTerminee {

    private final EmettreFacture emettreFacture;

    public EcouteurLocationTerminee(EmettreFacture emettreFacture) {
        this.emettreFacture = emettreFacture;
    }

    @EventListener
    public void quandUneLocationEstTerminee(LocationTerminee evenement) {
        emettreFacture.emettre(
                new IdUsager(evenement.idUsager()),
                new IdLocation(evenement.idLocation()),
                // TODO après la fusion : remplacer par evenement.dureeMinutes(), calculée par Location
                Duration.between(evenement.debut(), evenement.fin()).toMinutes(),
                TypeVehicule.valueOf(evenement.typeVehicule()));
    }
}
