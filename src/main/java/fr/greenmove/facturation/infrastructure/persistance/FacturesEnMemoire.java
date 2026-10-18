package fr.greenmove.facturation.infrastructure.persistance;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.StatutFacture;
import fr.greenmove.facturation.domaine.ports.Factures;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FacturesEnMemoire implements Factures {

    private final Map<IdFacture, Facture> parId = new ConcurrentHashMap<>();

    @Override
    public Optional<Facture> facture(IdFacture idFacture) {
        return Optional.ofNullable(parId.get(idFacture));
    }

    @Override
    public void enregistrer(Facture facture) {
        parId.put(facture.id(), facture);
    }

    @Override
    public Optional<Facture> facturePourLocation(IdLocation location) {
        return parId.values().stream().filter(f -> f.location().equals(location)).findFirst();
    }

    @Override
    public boolean aUneFactureImpayee(IdUsager idUsager) {
        return parId.values().stream()
                .anyMatch(f -> f.usager().equals(idUsager) && f.statut() == StatutFacture.IMPAYEE);
    }
}
