/*
 -----------------------------------------------------------------------------------
 Laboratoire : SER - Laboratoire 2
 Fichier     : TypePiece.java
 Auteur(s)   : Robin Gaudin, Yanick Thomann
 Date        : 25/04/2021
 But         : Enumération de toutes les pièces possibles
 Remarque(s) : Implémente l'interface ConvertissableEnPGN
 -----------------------------------------------------------------------------------
*/



package ch.heigvd.ser.labo2.coups;

public enum TypePiece implements ConvertissableEnPGN {

    Tour("R"),
    Cavalier("N"),
    Fou("B"),
    Roi("K"),
    Dame("Q"),
    Pion("");

    private final String pgn;

    TypePiece(String pgn) {

        this.pgn = pgn;

    }

    public String notationPGN() {

        return pgn;

    }
}
