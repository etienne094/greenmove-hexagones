package fr.greenmove.facturation.infrastructure.paiement;

import fr.greenmove.exterieur.PaiementSimule;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;
import fr.greenmove.facturation.domaine.ports.Paiement;

import java.util.Optional;

public class PaiementSurSimulation implements Paiement {

    private final PaiementSimule simulation;

    public PaiementSurSimulation(PaiementSimule simulation) {
        this.simulation = simulation;
    }

    @Override
    public Optional<String> debiter(IdUsager idUsager, Montant montant) {
        try {
            return Optional.of(simulation.debiter(idUsager.valeur(), montant.centimes()));
        } catch (PaiementSimule.DebitRefuse refus) {
            return Optional.empty();
        }
    }
}
