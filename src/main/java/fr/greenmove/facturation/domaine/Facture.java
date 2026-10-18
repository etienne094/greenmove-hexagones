package fr.greenmove.facturation.domaine;

import java.util.Objects;
import java.util.Optional;

public final class Facture {

    private final IdFacture id;
    private final IdUsager usager;
    private final IdLocation location;
    private final Montant montant;

    private StatutFacture statut;
    private String referenceDePaiement;

    private Facture(IdFacture id, IdUsager usager, IdLocation location, Montant montant,
                    StatutFacture statut, String referenceDePaiement) {
        this.id = Objects.requireNonNull(id, "id");
        this.usager = Objects.requireNonNull(usager, "usager");
        this.location = Objects.requireNonNull(location, "location");
        this.montant = Objects.requireNonNull(montant, "montant");
        this.statut = Objects.requireNonNull(statut, "statut");
        this.referenceDePaiement = referenceDePaiement;
    }

    public static Facture payee(IdFacture id, IdUsager usager, IdLocation location, Montant montant,
                                String referenceDePaiement) {
        return new Facture(id, usager, location, montant, StatutFacture.PAYEE, verifier(referenceDePaiement));
    }

    public static Facture impayee(IdFacture id, IdUsager usager, IdLocation location, Montant montant) {
        return new Facture(id, usager, location, montant, StatutFacture.IMPAYEE, null);
    }

    public void regulariser(String referenceDePaiement) {
        if (statut == StatutFacture.PAYEE) {
            throw new FactureDejaPayee(id);
        }
        this.referenceDePaiement = verifier(referenceDePaiement);
        this.statut = StatutFacture.PAYEE;
    }

    private static String verifier(String referenceDePaiement) {
        if (referenceDePaiement == null || referenceDePaiement.isBlank()) {
            throw new IllegalArgumentException("Référence de paiement vide");
        }
        return referenceDePaiement;
    }

    public IdFacture id() {return id;}
    public IdUsager usager() {return usager;}
    public IdLocation location() {return location;}
    public Montant montant() {return montant;}
    public StatutFacture statut() {return statut;}
    public Optional<String> referenceDePaiement() {return Optional.ofNullable(referenceDePaiement);}
}
