package fr.greenmove.facturation.infrastructure.rest;

import fr.greenmove.facturation.domaine.Facture;

public record FactureReponse(String id, String usager, String location, long montantCentimes, String statut,
                             String reference) {

    static FactureReponse depuis(Facture facture) {
        return new FactureReponse(
                facture.id().valeur(),
                facture.usager().valeur(),
                facture.location().valeur(),
                facture.montant().centimes(),
                facture.statut().name(),
                facture.referenceDePaiement().orElse(null));
    }
}
