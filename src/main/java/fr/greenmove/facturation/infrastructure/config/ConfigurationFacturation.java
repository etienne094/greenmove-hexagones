package fr.greenmove.facturation.infrastructure.config;

import fr.greenmove.exterieur.PaiementSimule;
import fr.greenmove.exterieur.UsagersEnMemoire;
import fr.greenmove.facturation.application.ports.EmettreFacture;
import fr.greenmove.facturation.infrastructure.abonnements.AbonnementsSurSimulation;
import fr.greenmove.facturation.infrastructure.evenements.EcouteurLocationTerminee;
import fr.greenmove.facturation.infrastructure.paiement.PaiementSurSimulation;
import fr.greenmove.facturation.infrastructure.persistance.FacturesEnMemoire;
import fr.greenmove.facturation.application.ServiceFacturation;
import fr.greenmove.facturation.domaine.GrilleTarifaire;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.facturation.domaine.ports.Factures;
import fr.greenmove.facturation.domaine.ports.Paiement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Le câblage de l'hexagone Facturation.
 *
 * <p>À compléter : vos adaptateurs comme beans des ports du domaine ({@code Factures}, {@code Paiement} sur
 * {@code PaiementSimule}, {@code Abonnements} sur {@code UsagersEnMemoire}), puis {@code ServiceFacturation}
 * construit avec ces ports et la grille tarifaire. Déclarez-le sous le type de ses ports entrants : Location a
 * besoin d'un bean {@code ConsulterImpayes}.</p>
 */
@Configuration
public class ConfigurationFacturation {

    @Bean
    Paiement paiement(PaiementSimule simulation) { return new PaiementSurSimulation(simulation); }

    @Bean
    Abonnements abonnements(UsagersEnMemoire simulation) { return new AbonnementsSurSimulation(simulation); }

    @Bean
    Factures factures() { return new FacturesEnMemoire(); }

    @Bean
    ServiceFacturation serviceFacturation(Factures factures, Paiement paiement, Abonnements abonnements) {
        return new ServiceFacturation(factures, paiement, abonnements, GrilleTarifaire.standard());
    }

    @Bean
    EcouteurLocationTerminee ecouteurLocationTerminee(EmettreFacture emettreFacture) {
        return new EcouteurLocationTerminee(emettreFacture);
    }
}
