/*
 -----------------------------------------------------------------------------------
 Laboratoire : SER - Laboratoire 2
 Fichier     : Coup.java
 Auteur(s)   : Robin Gaudin, Yanick Thomann
 Date        : 25/04/2021
 But         : Classe abstraite définissant un coup
 Remarque(s) : Implémente l'interface ConvertissableEnPGN
 -----------------------------------------------------------------------------------
*/


package ch.heigvd.ser.labo2.coups;


/**
 * Classe représentant un coup joué (soit un déplacement, soit un roque...)
 */
public abstract class Coup implements ConvertissableEnPGN {

    private final CoupSpecial coupSpecial; // ATTENTION : Cet attribut peut être null si ce n'est pas un coup special

    /**
     * @param coupSpecial A préciser si c'est un coup spécial (echec, mat, nulle)
     */
    Coup(CoupSpecial coupSpecial) {
        this.coupSpecial = coupSpecial;
    }

    /**
     * @return Doit retourner la notation PGN du coup selon ce qui est indiqué dans la donnée du labo
     */
    public String notationPGN() {

        return notationPGNimplem() + (coupSpecial == null ? "" : coupSpecial.notationPGN());

    }

    /**
     * Cette méthode doit être implémentée dans les sous classes : Deplacement et Roque
     *
     * @return Retourne la notation PGN qui concerne le déplacement
     */
    protected abstract String notationPGNimplem();

}

