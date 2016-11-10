package Othello;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Pion {
    public Joueur joueur;
    public String position;
    public Jeu jeu;
    public JButton bouton;



    public Pion(Joueur joueur, String nomBouton, Jeu jeu, JButton bouton){
        this.joueur = joueur;
        position = nomBouton;
        this.jeu=jeu;
        this.bouton=bouton;
        RemplirGrille(this, jeu);
    }

    public void RemplirGrille(Pion pion, Jeu jeu){
        //placer le pion dans la grille
        int ligne = Character.getNumericValue(pion.position.charAt(0));
        int colonne = Character.getNumericValue(pion.position.charAt(1));
//		System.out.println(ligne);
//		System.out.println(colonne);
        jeu.pionsJoues[ligne][colonne]=this;
    }




    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
