/*
 -----------------------------------------------------------------------------------
 Laboratoire : SER - Laboratoire 2
 Fichier     : TypeRoque.java
 Auteur(s)   : Robin Gaudin, Yanick Thomann
 Date        : 25/04/2021
 But         : Enumération définissant le type de coup roque
 Remarque(s) : Implémente l'interface ConvertissableEnPGN
 -----------------------------------------------------------------------------------
*/



package ch.heigvd.ser.labo2.coups;

public enum TypeRoque implements ConvertissableEnPGN {

    PETIT("O-O"),
    GRAND("O-O-O");

    private final String pgn;

    TypeRoque(String pgn) {
        this.pgn = pgn;
    }

    public String notationPGN() {
        return pgn;
    }

}
