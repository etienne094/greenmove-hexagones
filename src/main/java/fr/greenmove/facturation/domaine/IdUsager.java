package fr.greenmove.facturation.domaine;

public record IdUsager(String valeur) {
    public IdUsager {
        if(valeur == null || valeur.isBlank()){
            throw new IllegalArgumentException("Identifiant de l'usager vide");
        }
    }
}
