package fr.greenmove.facturation.domaine;

public record IdLocation(String valeur) {
    public IdLocation {
        if(valeur == null || valeur.isBlank()){
            throw new IllegalArgumentException("Identifiant de location vide");
        }
    }
}
