/**
 * Noms des étudiants : Robin GAUDIN, Yanick THOMANN
 */

package ch.heigvd.ser.labo2;

import ch.heigvd.ser.labo2.coups.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

class Main {

    public static void main(String... args) throws Exception {

        Document document = readDocument(new File("tournois_fse.xml"));

        Element root = document.getRootElement();
        List<Element> tournois = root.getChildren("tournois");

        writePGNfiles(tournois);

    }

    /**
     * Cette méthode doit parser avec SAX un fichier XML (file) et doit le transformer en Document JDOM2
     *
     * @param file
     */
    private static Document readDocument(File file) throws JDOMException, IOException {

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

        String nomTournoi;
        PrintWriter printWriter = null;

        // Iteration sur la liste de tournois
        for (Element ts : tournois) {
            // Iteration sur les tournois
            for (Element tournoi : ts.getChildren()) {
                // Nom du tournoi
                nomTournoi = tournoi.getAttributeValue("nom");
                Element parties = tournoi.getChild("parties");
                // Compteur de partie par tournoi
                int partieNo = 1;
                // Iteration sur les parties
                for (Element partie : parties.getChildren("partie")) {
                    // Definir le nom du fichier dans lequel nous allons ecrire
                    File outFile = new File(nomTournoi + "_partie_" + partieNo++ + "_PGN.txt");
                    // Instancier le PrintWriter
                    try {
                        printWriter = new PrintWriter(outFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Compteur utilisé pour suivre les tours
                    int roundCounter = 1;
                    
                    Element coups = partie.getChild("coups");
                    for (Element coup : coups.getChildren("coup")) {

                        // Afficher le numéro de tour
                        if (displayTurnNumber(roundCounter++))
                            //System.out.print(roundCounter / 2 + " ");
                            printWriter.write(roundCounter / 2 + " ");

                        // Si le coup est un deplacement
                        if (coup.getChild("deplacement") != null) {

                            // Obtenir les valeurs du fichier XML
                            String piece = coup.getChild("deplacement").getAttributeValue("piece");
                            String arrivee = coup.getChild("deplacement").getAttributeValue("case_arrivee");
                            String elimination = coup.getChild("deplacement").getAttributeValue("elimination");
                            String promotion = coup.getChild("deplacement").getAttributeValue("promotion");
                            String depart = coup.getChild("deplacement").getAttributeValue("case_depart");
                            String coupSpecial = coup.getAttributeValue("coup_special");

                            Case caseDepart = null;
                            if (depart != null)
                                caseDepart = new Case(depart.charAt(0), Character.getNumericValue(depart.charAt(1)));

                            Case caseArrivee = new Case(arrivee.charAt(0), Character.getNumericValue(arrivee.charAt(1)));
                            try {
                                // TypePiece pieceDeplacee, TypePiece elimination, TypePiece promotion, CoupSpecial coupSpecial, Case depart, Case arrivee
                                Deplacement deplacement = new Deplacement(getTypePiece(piece), getTypePiece(elimination), getTypePiece(promotion), getCoupSpecial(coupSpecial), caseDepart, caseArrivee);
                                printWriter.write(deplacement.notationPGN());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        // Si le coup est un roque
                        if (coup.getChild("roque") != null) {
                            // Obtenir le type de roque du fichier XML
                            String roqueType = coup.getChild("roque").getAttributeValue("type");
                            if (roqueType.equals("petit_roque")) {
                                printWriter.write(TypeRoque.valueOf("PETIT").notationPGN());
                            } else {
                                printWriter.write(TypeRoque.valueOf("GRAND").notationPGN());
                            }
                        }

                        // Afficher un espace si c'est au tour de noir, sinon un retour a la ligne
                        if (!displayTurnNumber(roundCounter)) {
                            printWriter.write(" ");
                        } else {
                            printWriter.write("\n");
                        }
                    }
                    // Fermer le flux d'ecriture
                    printWriter.close();
                }
            }
        }
    }

    /**
     * Cette methode sert a determiner si on doit afficher ou non le numero du tour,
     * @param counter le compteur utilise pour definir s'il s'agit d'un nouveau tour
     * @return booleen indiquant s'il faut afficher ou non le numero du tour
     */
    public static boolean displayTurnNumber(int counter) {
        if (counter % 2 != 0)
            return true;
        return false;
    }


    /**
     * Cette methode permet de retourner la notation PGN d'une pièce donne en String
     * @param piece Le string contenant le nom de la piece
     * @return La valeur PGN pour la piece, null si il n'y en a pas
     */
    public static TypePiece getTypePiece(String piece) {
        return piece == null ? null : TypePiece.valueOf(piece);
    }

    /**
     * Cette methode permet de retourner la notation PGN d'un coupSpecial donne en String
     * @param coup Le string contenant le nom du coup special
     * @return La valeur PGN du coup special, null s'il n'y en a pas
     */
    public static CoupSpecial getCoupSpecial(String coup){
        return coup == null ? null : CoupSpecial.valueOf(coup.toUpperCase());
    }



}