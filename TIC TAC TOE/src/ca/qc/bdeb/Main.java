package ca.qc.bdeb;
import java.util.Scanner;

/**
 * La methode <u>main</u> qui permet de declarer des variables finals
 * les variables finale 3 et 4 y sont declarer en tant que final
 * Le message pour demander d'entrer si l'utilisateur veut du <span style="background-color:Grey;">3x3 ou 4x4</span> y
 * est declarer en tant que final
 *
 */

public class Main {

    private static final String message = "Entrez 3 pour jouer 3x3 ou 4 pour jouer 4x4: ";
    private static final int MIN = 3;
    private static final int MAX = 4;

    public static void main(String[] args) {
        TicTacToe jeu = new TicTacToe(lireDimension(MIN, MAX, message));
    }

    /**
     * La methode <u>lireDimension</u> demande  a l'utilisateur s'il veut du
     * <span style="background-color:Grey;">3x3 ou 4x4</span>.
     * @param min qui contient 3
     * @param max qui contient 4
     * @param message qui contient le message qui s'affichera pour demander une information a l'utilisateur
     * @return valeur qui contient le contenu de la saisie de l'utilisateur
     */

    private static int lireDimension(int min, int max, String message) {
        Scanner clavier = new Scanner(System.in);

        int valeur = min - 1;
        do {
            System.out.print(message);
            String chaine = clavier.nextLine();
            try {
                valeur = Integer.parseInt(chaine);
            } catch(NumberFormatException e) {
            }
        } while (valeur < min || valeur > max);
        return valeur;
    }
}


