/**
 * Noms des étudiants : // TODO : A compléter...
 */

package ch.heigvd.ser.labo2;

import ch.heigvd.ser.labo2.coups.Case;
import ch.heigvd.ser.labo2.coups.CoupSpecial;
import ch.heigvd.ser.labo2.coups.TypePiece;
import ch.heigvd.ser.labo2.coups.TypeRoque;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.List;


// TODO : Vous avez le droit d'ajouter des instructions import si cela est nécessaire

class Main {

    public static void main(String... args) throws Exception {

        Document document = readDocument(new File("tournois_fse.xml"));

        // TODO : Compléter en une ligne l'initialisation de cette liste d'éléments (c'est la seule ligne que vous pouvez modifier ici.
        Element root = document.getRootElement();
        List<Element> tournois = root.getChildren("tournois"); /*null /* A compléter en utilisant la variable root */

        writePGNfiles(tournois);

    }

    /**
     * Cette méthode doit parser avec SAX un fichier XML (file) et doit le transformer en Document JDOM2
     *
     * @param file
     */
    private static Document readDocument(File file) throws JDOMException, IOException {

        // TODO : A compléter... (ne doit pas faire plus d'une ligne). Vous êtes autorisés à créer des méthodes utilitaires, mais pas des classes
        return (Document) new SAXBuilder().build(file);

    }

    /**
     * Cette méthode doit générer un fichier PGN pour chaque partie de chaque tournoi recu en paramètre comme indiqué dans la donnée
     * <p>
     * Le nom d'un fichier PGN doit contenir le nom du tournoi ainsi que le numéro de la partie concernée
     * <p>
     * Nous vous conseillons d'utiliser la classe PrinterWriter pour écrire dans les fichiers PGN
     * <p>
     * Vous devez utiliser les classes qui sont dans le package coups pour générer les notations PGN des coups d'une partie
     *
     * @param tournois Liste des tournois pour lesquelles écrire les fichiers PGN
     *                 <p>
     *                 (!!! Un fichier par partie, donc cette méthode doit écrire plusieurs fichiers PGN !!!)
     */
    private static void writePGNfiles(List<Element> tournois) {

        // TODO : A compléter... selon ce qui est indiqué dans la donnée... vous devez comprendre la DTD fournie avant de faire ceci !

        String nomTournoi;
        PrintWriter printWriter = null;


        // Iteration sur la liste de tournois
        for (Element ts : tournois) {
            // Iteration sur les tournois
            for (Element tournoi : ts.getChildren()) {
                // Nom du tournoi
                nomTournoi = tournoi.getAttributeValue("nom");
                Element parties = tournoi.getChild("parties");
                // Iteration sur les parties
                int partieNo = 1;
                for (Element partie : parties.getChildren("partie")) {
                    // Compteur utilisé pour suivre les tours
                    int roundCounter = 1;

                    // Creer le fichier dans lequel nous allons ecrire
                    File outFile = new File(nomTournoi + "_partie_" + partieNo++ + "_PGN.txt");


                    try {
                        //FileWriter outFile = new FileWriter(nomTournoi + "_partie_" + partieNo++ + "_PGN.txt");
                        printWriter = new PrintWriter(outFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Element coups = partie.getChild("coups");
                    for (Element coup : coups.getChildren("coup")) {

                        // Afficher le numéro de tour
                        if (displayTurnNumber(roundCounter++))
                            //System.out.print(roundCounter / 2 + " ");
                            printWriter.write(roundCounter / 2 + " ");

                        // Si le coup est un deplacement
                        if (coup.getChild("deplacement") != null) {

                            // Existe pour chaque coup
                            String piece = coup.getChild("deplacement").getAttributeValue("piece");
                            String arrivee = coup.getChild("deplacement").getAttributeValue("case_arrivee");
                            // Existe pour certains coups
                            String elimination = coup.getChild("deplacement").getAttributeValue("elimination");
                            String promotion = coup.getChild("deplacement").getAttributeValue("promotion");
                            String depart = coup.getChild("deplacement").getAttributeValue("case_depart");
                            String coupSpecial = coup.getAttributeValue("coup_special");

                            // Obtenir les notations PGN des coups
                            String piecePGN = piece == null ?
                                    "" : getTypePiecePGNNotation(piece);
                            String eliminationPGN = elimination == null ?
                                    "" : "x" + getTypePiecePGNNotation(elimination);
                            String promotionPGN = promotion == null ?
                                    "" : "=" + getTypePiecePGNNotation(promotion);

                            String caseDepartPGN = getCasePGNNotation(depart);
                            String caseArriveePGN = getCasePGNNotation(arrivee);
                            String coupSpecialPGN = coupSpecial == null ?
                                    "" : CoupSpecial.valueOf(coupSpecial.toUpperCase()).notationPGN();

                            // ----- TEST OUTPUT -----
                            /*System.out.print(piecePGN);
                            System.out.print(caseDepartPGN);
                            System.out.print(eliminationPGN);
                            System.out.print(caseArriveePGN);
                            System.out.print(promotionPGN);
                            System.out.print(coupSpecialPGN);

                             */

                            printWriter.write(piecePGN);
                            printWriter.write(caseDepartPGN);
                            printWriter.write(eliminationPGN);
                            printWriter.write(caseArriveePGN);
                            printWriter.write(promotionPGN);
                            printWriter.write(coupSpecialPGN);

                        }

                        // Si le coup est un roque
                        if (coup.getChild("roque") != null) {
                            String roqueType = coup.getChild("roque").getAttributeValue("type");
                            if (roqueType == "petit_roque") {
                                //System.out.print(TypeRoque.valueOf("PETIT").notationPGN());
                                printWriter.write(TypeRoque.valueOf("PETIT").notationPGN());
                            } else {
                                //System.out.print(TypeRoque.valueOf("GRAND").notationPGN());
                                printWriter.write(TypeRoque.valueOf("GRAND").notationPGN());
                            }
                        }

                        // Afficher un espace si c'est au tour de noir, sinon un retour a la ligne
                        if (!displayTurnNumber(roundCounter)) {
                            //System.out.print(" ");
                            printWriter.write(" ");
                        } else {
                            //System.out.print("\n");
                            printWriter.write("\n");
                        }
                    }
                    printWriter.close();
                }
            }
        }
    }


    public static String getTypePiecePGNNotation(String piece) {
        return piece == null ? "" : TypePiece.valueOf(piece).notationPGN();
    }

    public static String getCasePGNNotation(String caseStringValue) {
        return caseStringValue == null ? "" : new Case(caseStringValue.charAt(0), Character.getNumericValue(caseStringValue.charAt(1))).notationPGN();
    }

    public static boolean displayTurnNumber(int counter) {
        if (counter % 2 != 0)
            return true;
        return false;
    }


}