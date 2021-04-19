/*
 -----------------------------------------------------------------------------------
 Laboratoire : SER - Laboratoire 2
 Fichier     : CoupSpecial.java
 Auteur(s)   : Robin Gaudin, Yanick Thomann
 Date        : 25/04/2021
 But         : Enumeration définissant un coup spécial (Mise en échec, mat, partie nulle, roque)
 Remarque(s) : Implémente l'interface ConvertissableEnPGN
 -----------------------------------------------------------------------------------
*/



package ch.heigvd.ser.labo2.coups;

public enum CoupSpecial implements ConvertissableEnPGN {

    ECHEC("+"),
    MAT("#"),
    NULLE("");

    private final String notation;

    CoupSpecial(String notation) {
        this.notation = notation;
    }

    public String notationPGN() {
        return notation;
    }
}
