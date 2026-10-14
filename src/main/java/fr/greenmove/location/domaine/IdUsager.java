package fr.greenmove.location.domaine;

public record IdUsager(String valeur) {
    public IdUsager{
        if(valeur == null || valeur.isBlank()){
            throw new IllegalArgumentException("Utilisateur vide");
        }
    }
}
