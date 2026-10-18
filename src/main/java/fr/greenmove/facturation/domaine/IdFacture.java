package fr.greenmove.facturation.domaine;

import java.util.UUID;

public record IdFacture(String valeur) {
    public IdFacture {
        if(valeur == null || valeur.isBlank()){
            throw new IllegalArgumentException("Identifiant de facture vide");
        }
    }

    public static IdFacture nouveau() {
        return new IdFacture(UUID.randomUUID().toString());
    }
}
