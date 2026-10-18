package fr.greenmove.facturation.domaine;

public class GrilleTarifaire {
    private final long priseEnCharge;
    private final long prixVelo;
    private final long prixTrottinette;
    private final int reductionAbonne;
    private final long plafond;

    private GrilleTarifaire(long priseEnCharge, long prixVelo, long prixTrottinette, int reductionAbonne, long plafond) {
        this.priseEnCharge = priseEnCharge;
        this.prixVelo = prixVelo;
        this.prixTrottinette = prixTrottinette;
        this.reductionAbonne = reductionAbonne;
        this.plafond = plafond;
    }

    public static GrilleTarifaire standard() {
        return new GrilleTarifaire(100, 15,22,20, 1500);
    }

    public Montant calculPrix(long nbMinutes, TypeVehicule typeVehicule, boolean estAbonne) {
        Montant prix = switch (typeVehicule) {
            case VELO -> new Montant(prixVelo).multiplierParMinutes(nbMinutes).additionner(priseEnCharge);
            case TROTTINETTE -> new Montant(prixTrottinette).multiplierParMinutes(nbMinutes).additionner(priseEnCharge);
        };

        if (prix.centimes() > plafond) {
           prix = new Montant(plafond);
        }

        if (estAbonne) {
            prix = prix.appliquerReduction(reductionAbonne);
        }

        return prix;
    }
}
