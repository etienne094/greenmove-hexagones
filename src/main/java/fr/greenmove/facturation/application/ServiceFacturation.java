package fr.greenmove.facturation.application;

import fr.greenmove.facturation.application.ports.ConsulterFacture;
import fr.greenmove.facturation.application.ports.ConsulterImpayes;
import fr.greenmove.facturation.application.ports.EmettreFacture;
import fr.greenmove.facturation.application.ports.RegulariserFacture;
import fr.greenmove.facturation.domaine.*;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.facturation.domaine.ports.Factures;
import fr.greenmove.facturation.domaine.ports.Paiement;

import java.util.Optional;

public class ServiceFacturation implements ConsulterFacture, ConsulterImpayes, EmettreFacture, RegulariserFacture {
    private final Abonnements abonnements;
    private final Factures factures;
    private final Paiement paiement;
    private final GrilleTarifaire grilleTarifaire;

    public ServiceFacturation(Factures factures, Paiement paiement, Abonnements abonnements, GrilleTarifaire grilleTarifaire) {
        this.abonnements = abonnements;
        this.factures = factures;
        this.paiement = paiement;
        this.grilleTarifaire = grilleTarifaire;
    }

    @Override
    public Facture consulter(IdFacture idFacture) {
        return factures.facture(idFacture).orElseThrow(() -> new FactureIntrouvable(idFacture));
    }

    @Override
    public Optional<Facture> consulterParLocation(IdLocation idLocation) {
        return factures.facturePourLocation(idLocation);
    }

    @Override
    public boolean aUneFactureImpayee(String idUsager) {
        return factures.aUneFactureImpayee(new IdUsager(idUsager));
    }

    @Override
    public Facture emettre(IdUsager idUsager, IdLocation idLocation, long nbMinutes, TypeVehicule typeVehicule) {
        Optional<Facture> factureExistante = factures.facturePourLocation(idLocation);
        if (factureExistante.isPresent()) {
            return factureExistante.get();
        }

        Facture facture;
        Montant prix = grilleTarifaire.calculPrix(nbMinutes,typeVehicule, abonnements.estAbonne(idUsager));
        Optional<String> referencePaiement = paiement.debiter(idUsager, prix);
        if (referencePaiement.isPresent()) {
            facture = Facture.payee(IdFacture.nouveau(), idUsager, idLocation, prix, referencePaiement.get());
        } else  {
            facture = Facture.impayee(IdFacture.nouveau(), idUsager, idLocation, prix);
        }
        factures.enregistrer(facture);
        return facture;
    }

    @Override
    public Facture regulariser(IdFacture idFacture) {
        Facture facture = factures.facture(idFacture).orElseThrow(() -> new FactureIntrouvable(idFacture));
        if (facture.statut() == StatutFacture.PAYEE) {
            throw new FactureDejaPayee(idFacture);
        }
        Optional<String> referencePaiement = paiement.debiter(facture.usager(), facture.montant());
        if (referencePaiement.isPresent()) {
            facture.regulariser(referencePaiement.get());
        } else {
           throw new PaiementRefuse(idFacture);
        }
        factures.enregistrer(facture);
        return facture;
    }
}
