package fr.greenmove.facturation.infrastructure.abonnements;

import fr.greenmove.exterieur.UsagersEnMemoire;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.ports.Abonnements;

public class AbonnementsSurSimulation implements Abonnements {

    private final UsagersEnMemoire simulation;

    public AbonnementsSurSimulation(UsagersEnMemoire simulation) {
        this.simulation = simulation;
    }

    @Override
    public boolean estAbonne(IdUsager idUsager) {
        return simulation.parId(idUsager.valeur())
                .map(UsagersEnMemoire.EntreeUsager::abonne)
                .orElse(false);
    }
}
