package Othello;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Jeu {
    //public static Joueur [] joueurs = new Joueur[2];
    public Joueur joueurOrdi;
    public Joueur joueurH;
    public Joueur joueurCourant;
    public Pion[][] pionsJoues;
    public JButton[][] grilleboutons;

    public Jeu(){
        joueurH = new Joueur("humain","../Black_Circle.png");
        joueurOrdi = new Joueur("ordinateur","../White_Circle.png");
//		joueurs[0] = new Joueur("ordinateur","../Black_Circle.png");
//		joueurs[1] = new Joueur("humain","../White_Circle.png");
        joueurCourant = joueurH;
        System.out.println(joueurCourant.nom);
        pionsJoues= new Pion[8][8];
        grilleboutons = new JButton[8][8];
    }

    public void TraitementDuTour(JButton newButton) throws Exception{

        if(newButton.getIcon()==null && newButton.getBackground()== Color.red){
            //au clic je crée un nouveau pion avec le joueur actuel et les coordonnées du bouton sur lequel j'ai cliqué
            Pion monPion = new Pion(this.joueurCourant, newButton.getName(), this,newButton);
            //ici on va ajouter l'icone du bouton selon le joueur actuel et on va switcher de joueur
            try {
                if(this.joueurCourant.nom=="ordinateur" && newButton.getIcon()==null && newButton.getBackground()== Color.red){
                    monPion.bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../White_Circle.png"))));


                    System.out.println(joueurCourant.nom);
                }else{
                    if(newButton.getIcon()==null && newButton.getBackground()== Color.red){
                        monPion.bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Black_Circle.png"))));

                        System.out.println(joueurCourant.nom);
                    }
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            //je déseactive le bouton
            newButton.setEnabled(false);
            this.joueurCourant.score+=1;
            ActualisatioDeGrille(this,monPion, monPion.joueur);

            if(monPion.joueur==this.joueurH)
                this.joueurCourant=joueurOrdi;
            else
                this.joueurCourant=joueurH;
        }
    }



    public static void CoupsPossibles(Jeu jeu){
        //parcours de la grille
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(jeu.pionsJoues[i][j]==null){
                    boolean positionOK=false;
                    int pionAdverseTrouve=0;
                    int ligne = i;
                    int col = j;

                    while(ligne>=1 && col>=1 && positionOK==false){
                        if(jeu.pionsJoues[ligne-1][col-1]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne-1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col-1].joueur!=jeu.joueurCourant){
                            if(ligne-1==0 || col-1==0){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col-=1;
                        ligne-=1;
                    }


                    col=j;
                    ligne = i;
                    //ligne < |
                    while(ligne>=1 && positionOK==false){
                        if(jeu.pionsJoues[ligne-1][col]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne-1][col].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col].joueur!=jeu.joueurCourant){
                            if(ligne-1==0){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        ligne-=1;
                    }

                    ligne = i;
                    col = j;
                    //Diagonale / >
                    while(ligne>=1 && col <=6 && positionOK==false){
                        if(jeu.pionsJoues[ligne-1][col+1]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne-1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col+1].joueur!=jeu.joueurCourant){
                            if(ligne-1==0 || col+1==7){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col+=1;
                        ligne-=1;
                    }

                    ligne=i;
                    col = j;
                    //colonne <-
                    while(col >=1 && positionOK==false){
                        if(jeu.pionsJoues[ligne][col-1]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col-1].joueur!=jeu.joueurCourant){
                            if(col-1==0){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col-=1;
                    }

                    ligne=i;
                    col = j;
                    //ligne ->
                    while(col <=6 && positionOK==false){
                        if(jeu.pionsJoues[ligne][col+1]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col+1].joueur!=jeu.joueurCourant){
                            if(col+1==7){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col+=1;
                    }

                    ligne = i;
                    col = j;
                    //diaonale < /
                    while(ligne<=6 && col >=1 && positionOK==false) {
                        if(jeu.pionsJoues[ligne+1][col-1]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0){
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col-1].joueur!=jeu.joueurCourant){
                            if(col-1==0 || ligne+1==7){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col-=1;
                        ligne+=1;
                    }

                    ligne = i;
                    col=j;
                    //colone | >
                    while(ligne<=6 && positionOK==false){
                        if(jeu.pionsJoues[ligne+1][col]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne+1][col].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col].joueur!=jeu.joueurCourant){
                            if(ligne+1==7){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        ligne+=1;
                    }

                    ligne = i;
                    col = j;
                    //diagonale \ >
                    while(ligne<=6 && col <=6 && positionOK==false){
                        if(jeu.pionsJoues[ligne+1][col+1]==null){
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne+1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col+1].joueur!=jeu.joueurCourant){
                            if(col+1==7 || ligne+1==7){
                                pionAdverseTrouve=0;
                                break;
                            }
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col+=1;
                        ligne+=1;
                    }
                    if(positionOK){
                        jeu.grilleboutons[i][j].setBackground(Color.red);
                        jeu.grilleboutons[i][j].setEnabled(true);
                        positionOK=false;
                    }
                }
            }
        }

    }

    public static void MAJCouleur(Jeu jeu){
        for(int i=0; i<8;i++){
            for(int j=0;j<8;j++){
                jeu.grilleboutons[i][j].setBackground(new java.awt.Color(0.1f, 0.80f, 0.2f, 1f));
                jeu.grilleboutons[i][j].setEnabled(false);
            }
        }
    }

    public void ActualisatioDeGrille(Jeu jeu, Pion pion, Joueur joueur) throws Exception{
        //Ici on va parcourir la grille afin de déterminer si le pion que l'on vient de jouer encadre une ligne adverse
        int ligne = Character.getNumericValue(pion.position.charAt(0));
        int colonne = Character.getNumericValue(pion.position.charAt(1));
        int ligne2 = Character.getNumericValue(pion.position.charAt(0));
        int colonne2 = Character.getNumericValue(pion.position.charAt(1));
        int ligne3 = Character.getNumericValue(pion.position.charAt(0));
        int colonne3 = Character.getNumericValue(pion.position.charAt(1));
        int ligne4 = Character.getNumericValue(pion.position.charAt(0));
        int colonne4 = Character.getNumericValue(pion.position.charAt(1));
        int ligne5 = Character.getNumericValue(pion.position.charAt(0));
        int colonne5 = Character.getNumericValue(pion.position.charAt(1));
        int ligne6 = Character.getNumericValue(pion.position.charAt(0));
        int colonne6 = Character.getNumericValue(pion.position.charAt(1));
        int ligne7 = Character.getNumericValue(pion.position.charAt(0));
        int colonne7 = Character.getNumericValue(pion.position.charAt(1));
        int ligne8 = Character.getNumericValue(pion.position.charAt(0));
        int colonne8 = Character.getNumericValue(pion.position.charAt(1));
        int pionAdverseTrouve=0;



        //diagonale < \
        while(ligne>=1 && colonne >=1){

            if(jeu.pionsJoues[ligne-1][colonne-1]!=null){
                if(jeu.pionsJoues[ligne-1][colonne-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne-1][colonne-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0))-1;
                    int debc = Character.getNumericValue(pion.position.charAt(1))-1;
                    while(deb>=ligne && debc>=colonne) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        deb--;
                        debc--;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne-1][colonne-1].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            colonne-=1;
            ligne-=1;
        }

        //ligne < |
        while(ligne2>=1){
            if(jeu.pionsJoues[ligne2-1][colonne2]!=null){
                if(jeu.pionsJoues[ligne2-1][colonne2].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne2-1][colonne2].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0))-1; //ligne
                    int debc = Character.getNumericValue(pion.position.charAt(1)); //col
                    System.out.println(deb);
                    System.out.println(debc);
                    while(deb>=ligne2) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        deb--;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne2-1][colonne2].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            ligne2-=1;
        }

        //Diagonale / >
        while(ligne3>=1 && colonne3 <=6){
            if(jeu.pionsJoues[ligne3-1][colonne3+1]!=null){
                if(jeu.pionsJoues[ligne3-1][colonne3+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne3-1][colonne3+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0))-1;
                    int debc = Character.getNumericValue(pion.position.charAt(1))+1;
                    while(deb>=ligne3 && colonne3>=debc) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        deb--;
                        debc++;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne3-1][colonne3+1].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            colonne3+=1;
            ligne3-=1;
        }

        //colonne <-
        while(colonne4 >=1){
            if(jeu.pionsJoues[ligne4][colonne4-1]!=null){
                if(jeu.pionsJoues[ligne4][colonne4-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne4][colonne4-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0));
                    int debc = Character.getNumericValue(pion.position.charAt(1))-1;
                    while(debc>=colonne4) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        debc--;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne4][colonne4-1].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            colonne4-=1;
        }

        //ligne ->
        while(colonne5 <=6){
            if(jeu.pionsJoues[ligne5][colonne5+1]!=null){
                if(jeu.pionsJoues[ligne5][colonne5+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne5][colonne5+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0));
                    int debc = Character.getNumericValue(pion.position.charAt(1))+1;
                    while(colonne5>=debc) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        debc++;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne5][colonne5+1].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            colonne5+=1;
        }

        //diaonale < /
        while(ligne6<=6 && colonne6 >1){
            if(jeu.pionsJoues[ligne6+1][colonne6-1]!=null){
                if(jeu.pionsJoues[ligne6+1][colonne6-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne6+1][colonne6-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0))+1;
                    int debc = Character.getNumericValue(pion.position.charAt(1))-1;
                    while(ligne6>=deb && debc>=colonne6) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        deb++;
                        debc--;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne6+1][colonne6-1].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            colonne6-=1;
            ligne6+=1;
        }

        //colone | >
        while(ligne7<=6){
            if(jeu.pionsJoues[ligne7+1][colonne7]!=null){
                if(jeu.pionsJoues[ligne7+1][colonne7].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne7+1][colonne7].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0))+1;
                    int debc = Character.getNumericValue(pion.position.charAt(1));
                    while(ligne7>=deb) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        deb++;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne7+1][colonne7].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            ligne7+=1;
        }

        //diagonale \ >
        while(ligne8<=6 && colonne8 <=6){
            if(jeu.pionsJoues[ligne8+1][colonne8+1]!=null){
                if(jeu.pionsJoues[ligne8+1][colonne8+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                    break;
                if(jeu.pionsJoues[ligne8+1][colonne8+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                    //change couleur des autres
                    //j'envoie la position du premier bouton et du dernier bouton dont il faut changer la couleur et le joueur
                    int deb = Character.getNumericValue(pion.position.charAt(0))+1;
                    int debc = Character.getNumericValue(pion.position.charAt(1))+1;
                    while(ligne8>=deb && colonne8>=debc) {
                        jeu.pionsJoues[deb][debc].joueur=jeu.joueurCourant;
                        try {
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(true);
                            jeu.pionsJoues[deb][debc].bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(jeu.joueurCourant.couleur))));
                            jeu.pionsJoues[deb][debc].bouton.setEnabled(false);
                            jeu.joueurCourant.score+=1;
                            switch (jeu.joueurCourant.nom){
                                case "humain" : jeu.joueurOrdi.score-=1; break;
                                case "ordinateur" : jeu.joueurH.score-=1; break;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        deb++;
                        debc++;
                    }
                    Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
                    Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
                    pionAdverseTrouve=0;
                    break;
                }
                if(jeu.pionsJoues[ligne8+1][colonne8+1].joueur!=jeu.joueurCourant)
                    pionAdverseTrouve+=1;
            }
            else{
                break;
            }
            colonne8+=1;
            ligne8+=1;
        }
    }

    public static void reset(Jeu jeu){
        jeu.joueurCourant=jeu.joueurH;
        jeu.joueurH.score=2;
        jeu.joueurOrdi.score=2;
        Plateau.scoreHumain.setText("Score joueur :" + jeu.joueurH.score);
        Plateau.scoreMachine.setText("Score machine :" + jeu.joueurOrdi.score);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                String nom = jeu.grilleboutons[i][j].getName();
                switch (nom){
                    case "33" :
                        jeu.pionsJoues[3][3].joueur=jeu.joueurOrdi;
                        break;
                    case "34" :
                        jeu.pionsJoues[3][4].joueur=jeu.joueurH;
                        break;
                    case "43" :
                        jeu.pionsJoues[4][3].joueur=jeu.joueurH;
                        break;
                    case "44" :
                        jeu.pionsJoues[4][4].joueur=jeu.joueurOrdi;
                        break;
                    default :
                        jeu.pionsJoues[i][j]=null;
                        jeu.grilleboutons[i][j].setIcon(null);
                        break;
                }
            }
        }
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
//		Jeu partie = new Jeu();
//		Pion p1 = new Pion(partie, "")




    }

}
