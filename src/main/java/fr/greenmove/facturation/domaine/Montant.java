package fr.greenmove.facturation.domaine;

public record Montant(long centimes) {
    public Montant {
        if (centimes < 0) {
            throw new IllegalArgumentException("Le montant en centimes ne peut pas être négatif");
        }
    }

    public Montant additionner(long a) {
        return new Montant(centimes + a);
    }

    public Montant multiplierParMinutes(long minutes) {
        return new Montant(centimes * minutes);
    }

    public Montant appliquerReduction(int reduction) {
        return new Montant(centimes - (centimes * reduction / 100));
    }
}
