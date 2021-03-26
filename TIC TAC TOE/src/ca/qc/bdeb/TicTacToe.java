package ca.qc.bdeb;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * la class <u>TicTacToe</u>
 * C'est une classe qui contient tous les varibles statiques neccessaires pour faire fonctionner le tic tac toe
 */

public class TicTacToe extends JFrame implements ActionListener {
    private static final int MIN_DIMENSION = 3;
    private static final int MAX_DIMENSION = 4;
    private static final int POSITION_X = 55;
    private static final int POSITION_Y = 55;
    private static final int DIMENSION_X = 50;
    private static final int DIMENSION_Y = 50;
    private static final int DIMENSION_FRAME_X = 500;
    private static final int DIMENSION_FRAME_Y = 500;
    private static final String TITRE = "Tic Tac Toe";
    private static final String RECOMMENCER = "Recommencer";
    private static final String[] JOUEURS = {"X", "O"};
    private static final String MESSAGE_BIENVENUE = "Bonjour! %s commence!!!";
    private static final String PROCHAIN_TOUR = "C'est au tour de %s à jouer!!!";
    private static final String GAGNANT = "%s a gagné!";
    private static final String PAS_GAGNANT = "Personne n'a gagné!";
    private static final String ERREUR = "Erreur!!!";
    private static final String VIDE = "";

    private JFrame frame;

    private JButton[][] boutons;
    private String[][] tableau;
    private JButton remiseANeuf;
    private JLabel message;
    private int joueurCourant;
    private int tour;
    private boolean gagnant;
    private Random random;
    private int dimension;
    private int maxTours;

    /**
     * le constructeur <u>TicTacToe</u> initialise simplement le tableau selon la dimension du tableau que l'utilisateur
     * veut et creer en meme temp un inteface graphique
     * Quand le programme finit, il appelle la methode <u>RemettreANeuf</u> et affiche apres un message
     * @param dimension qui est une variable qui permet de determiner la taille du tableau
     * <span style="background-color:Grey;"> 3x3 ou 4x4</span>
     */

    public TicTacToe(int dimension) {
        if (dimension < MIN_DIMENSION) {
            dimension = MIN_DIMENSION;
        }

        if (dimension > MAX_DIMENSION) {
            dimension = MAX_DIMENSION;
        }

        this.dimension = dimension;
        maxTours = dimension * dimension;
        boutons = new JButton[dimension][dimension];
        tableau = new String[dimension][dimension];

        random = new Random();

        frame = new JFrame(TITRE);
        frame.setSize((dimension + 2) * POSITION_X, (dimension + 4) * POSITION_Y);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        message = new JLabel();
        message.setBounds(POSITION_X, POSITION_Y, dimension * POSITION_X - (POSITION_X - DIMENSION_X), DIMENSION_Y);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(message);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boutons[i][j] = new JButton();
                // attention, ici, il faut changer la position en X
                boutons[i][j].setBounds((j + 1) * POSITION_X, (i + 2) * POSITION_Y, DIMENSION_X, DIMENSION_Y);
                boutons[i][j].addActionListener(this);
                frame.add(boutons[i][j]);
            }
        }
        remiseANeuf = new JButton(RECOMMENCER);
        remiseANeuf.setBounds(POSITION_X, (dimension + 2) * POSITION_Y, dimension * POSITION_X - (POSITION_X - DIMENSION_X), DIMENSION_Y);
        remiseANeuf.addActionListener(this);
        frame.add(remiseANeuf);

        remettreANeuf();

        // message de bienvenue
        message.setText(String.format(MESSAGE_BIENVENUE, JOUEURS[joueurCourant]));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == remiseANeuf) {
            remettreANeuf();
            return;
        }

        // déterminer quel bouton a été sélectionné
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (e.getSource() == boutons[i][j]) {
                    jouerTour(i, j);
                    return;
                }
            }
        }
    }

    /**
     * La methode <u>prochainTour</u> permet de faire avancer le jeu et permetre au prochain joueur de jouer son tour
     */

    private void prochainTour() {
        joueurCourant++;
        joueurCourant %= JOUEURS.length;
        message.setText(String.format(PROCHAIN_TOUR, JOUEURS[joueurCourant]));
    }

    /**
     * La mehode <u>remettreANeuf</u> efface tout le contenu du tableau et le remet a neuf
     */
    private void remettreANeuf() {
        // choisir un nouveau joueur
        joueurCourant = random.nextInt(JOUEURS.length);
        tour = 0;
        gagnant = false;
        // Tout effacer
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boutons[i][j].setText(VIDE);
                tableau[i][j] = VIDE;
            }
        }
        message.setText(String.format(PROCHAIN_TOUR, JOUEURS[joueurCourant]));
    }

    /**
     * La methode <u>jouerTour</u> vas deja verifier si il y a eu un gagnant
     * par la suite elle va verifier si la case selectionne a ete joue ou pas
     * En suite s'il y a un gagnant, elle affiche un message du gagnant et s'il n'y a pas de gagnant elle affiche
     * message qui aucun gangant
     * @param i  qui prend la position dans la ligne
     * @param j  qui prend la position dans la colonne
     * ces deux variable vont venir etre affecte au boutons
     */

    private void jouerTour(int i, int j) {
        // est-ce fini?
        if (gagnant || tour == maxTours) {
            return;
        }

        // cette case a déjà été jouée?
        if (boutons[i][j].getText().length() > 0) {
            return;
        }

        boutons[i][j].setText(JOUEURS[joueurCourant]);
        tableau[i][j] = JOUEURS[joueurCourant];
        tour++;
        gagnant = validerGagnantTest();


        if (gagnant) {
            // Si on a un gagnant, afficher le bon message
            message.setText(String.format(GAGNANT, JOUEURS[joueurCourant]));
        } else if (tour == maxTours) {
            // aucun gagnant
            message.setText(PAS_GAGNANT);
        } else {
            prochainTour();
        }
    }
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   // c'est ici que j'ai besoin d'aide!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private boolean validerGagnantTest(){

        String ligne="";

        // Boucle pour la ligne
        for (int i = 0; i < tableau.length; i++) {
            ligne="";
            for (int j = 0; j < tableau[i].length; j++) {
                ligne+=tableau[i][j];

                // verrification du gagnant pour OOO et XXX
                if (ligne.contains("OOO")||ligne.contains("XXX")) {
                    return true;
                }


            }
        }
        // Boucle pour la colonne
        for (int i = 0; i < tableau.length; i++) {
          ligne="";
            for (int j = 0; j < tableau[i].length; j++) {
                ligne+=tableau[j][i];

                // verrification du gagnant pour OOO
                if (ligne.contains("OOO")||ligne.contains("XXX")) {
                    return true;
                }

            }
        }
        // Boucle pour la diagonale principal
        for (int i = 0; i <tableau.length ; i++) {
            ligne+=tableau[i][i];

            // verrification du gagnant pour OOO et XXX
            if (ligne.contains("OOO")||ligne.contains("XXX")) {
                return true;
            }

        }

       ligne="";
        // Boucle pour la diagonale inverse
        for (int i = 0; i < tableau.length; i++) {
            ligne+= tableau[i][tableau.length-1-i];

            // verrification du gagnant pour OOO et XXX
            if (ligne.contains("OO0")||ligne.contains("XXX")) {
                return true;
            }


        }



        return false;



    }


}

