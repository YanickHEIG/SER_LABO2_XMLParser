/*
 -----------------------------------------------------------------------------------
 Laboratoire : SER - Laboratoire 2
 Fichier     : Roque.java
 Auteur(s)   : Robin Gaudin, Yanick Thomann
 Date        : 25/04/2021
 But         : Crée un roque qui est un type spécifique de coup
 Remarque(s) : Herite de la classe Coup
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.ser.labo2.coups;

public class Roque extends Coup {

    private final TypeRoque typeRoque;

    /**
     *
     * @param coupSpecial Indique si c'est un coup spécial (echec, mat, null) peut être null
     * @param typeRoque Indique le type de roque (petit ou grand)
     */
    public Roque(CoupSpecial coupSpecial, TypeRoque typeRoque) {

        super(coupSpecial);

        this.typeRoque = typeRoque;

    }

    /**
     *
     * @return Doit retourner la notation PGN du roque comme indiqué dans la donnée
     *
     *         Ici, vous pouvez faire appel directement à l'énumération typeRoque
     */
    @Override
    protected String notationPGNimplem() {
        return typeRoque.notationPGN();
    }

}
