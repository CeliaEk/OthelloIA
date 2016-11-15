package Othello;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public Map<JButton, Integer> grilleCoupsPossible;
    public Map<JButton, Integer> coupsPossiblesScore;
    int coupsPossible=0;
    public String modeJeu="";
    public int[][] tabPoint;


    public Jeu(){
        joueurH = new Joueur("humain","../Black_Circle.png");
        joueurOrdi = new Joueur("ordinateur","../White_Circle.png");
        joueurCourant = joueurH;
        pionsJoues= new Pion[8][8];
        grilleboutons = new JButton[8][8];
        grilleCoupsPossible = new HashMap<JButton, Integer>();
        coupsPossiblesScore = new HashMap<JButton, Integer>();
        tabPoint = new int[][]
                {
                        {1000, 100, 700, 400, 400, 700, 100, 1000},

                        {100, 0, 310, 310, 310, 310, 0, 100},

                        {700, 310, 350, 325, 325, 350, 310, 700},

                        {400, 310, 325, 500, 500, 325, 310, 400},

                        {400, 310, 325, 500, 500, 325, 310, 400},

                        {700, 310, 350, 325, 325, 350, 310, 700},

                        {100, 0, 310, 310, 310, 310, 0, 100},

                        {1000, 100, 700, 400, 400, 700, 100, 1000}
                };

    }

    public void Go(Jeu jeu){
        jeu.modeJeu=Plateau.listeMode.getSelectedItem().toString();
        System.out.println(jeu.modeJeu);
        CoupsPossibles(jeu);
        System.out.println(Minmax(5, jeu));


    }

    public void modifiePoint()
    {
        if (joueurCourant == joueurH)
        {
            if (pionsJoues[0][0]!=null && pionsJoues[0][0].joueur == joueurH)
            {
                tabPoint[1][2] = 1000;
                tabPoint[2][1] = 1000;
                tabPoint[2][2] = 1000;
            }
            if (pionsJoues[0][7]!=null && pionsJoues[0][7].joueur == joueurH)
            {
                tabPoint[0][6] = 1000;
                tabPoint[1][7] = 1000;
                tabPoint[1][6] = 1000;
            }
            if (pionsJoues[7][0]!=null && pionsJoues[7][0].joueur == joueurH)
            {
                tabPoint[6][0] = 1000;
                tabPoint[6][1] = 1000;
                tabPoint[7][1] = 1000;
            }
            if (pionsJoues[7][7]!=null && pionsJoues[7][7].joueur == joueurH)
            {
                tabPoint[6][7] = 1000;
                tabPoint[6][6] = 1000;
                tabPoint[7][6] = 1000;
            }
        }
        else if (joueurCourant == joueurOrdi)
        {
            if (pionsJoues[0][0]!=null && pionsJoues[0][0].joueur == joueurOrdi)
            {
                tabPoint[0][1] = 1000;
                tabPoint[1][0] = 1000;
                tabPoint[1][1] = 1000;
            }
            if (pionsJoues[0][7]!=null && pionsJoues[0][7].joueur == joueurOrdi)
            {
                tabPoint[0][6] = 1000;
                tabPoint[1][7] = 1000;
                tabPoint[1][6] = 1000;
            }
            if (pionsJoues[7][0]!=null && pionsJoues[7][0].joueur == joueurOrdi)
            {
                tabPoint[6][0] = 1000;
                tabPoint[6][1] = 1000;
                tabPoint[7][1] = 1000;
            }
            if (pionsJoues[7][7]!=null && pionsJoues[7][7].joueur == joueurOrdi)
            {
                tabPoint[6][7] = 1000;
                tabPoint[6][6] = 1000;
                tabPoint[7][6] = 1000;
            }
        }
    }


    //GESTION DES COUPS DU JOUEUR
    public void TraitementDuTourJoueur(JButton newButton) throws Exception{

        System.out.println("TourH"+ this.joueurCourant.nom);
        if(newButton.getBackground()== Color.red){
            //au clic je crée un nouveau pion avec le joueur actuel et les coordonnées du bouton sur lequel j'ai cliqué
            Pion monPion = new Pion(this.joueurH, newButton.getName(), this,newButton);
            //ici on va ajouter l'icone du bouton selon le joueur actuel et on va switcher de joueur
            try {
                monPion.bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Black_Circle.png"))));

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            this.joueurH.score+=1;
            ActualisatioDeGrille(this,monPion, monPion.joueur);
            MAJCouleur(this);
            this.joueurCourant=this.joueurOrdi;
            CoupsPossibles(this);


            TraitementDuTourIA(this);
        }
    }


    //GESTION DES COUPS IA
    public void TraitementDuTourIA(Jeu jeu) {
        System.out.println("TourIA"+ this.joueurCourant.nom);
        if(!jeu.grilleCoupsPossible.isEmpty()){
            switch (jeu.modeJeu){
                //Heuristique 1
                case "Niveau 1" :

                    int indice =  (int) (Math.random()*((jeu.coupsPossible-1)-0+1));
                    JButton bouton = (JButton) jeu.grilleCoupsPossible.keySet().toArray()[indice];
                    Pion monPion = new Pion(jeu.joueurOrdi, bouton.getName(), jeu, bouton);
                    try {
                        monPion.bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../White_Circle.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    this.joueurOrdi.score+=1;
                    ActualisatioDeGrille(jeu,monPion, monPion.joueur);

                    break;

                //Heuristique 2
                case "Niveau 2" :
                    int max = 0;
                    JButton bouton2 = new JButton();
                    for(Map.Entry<JButton, Integer> valMax :  jeu.grilleCoupsPossible.entrySet()){
                        if(valMax.getValue()>max){
                            max = valMax.getValue();
                            bouton2 = valMax.getKey();
                        }
                    }

                    Pion monPion2 = new Pion(jeu.joueurOrdi, bouton2.getName(), jeu, bouton2);
                    try {
                        monPion2.bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../White_Circle.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    this.joueurOrdi.score+=1;
                    ActualisatioDeGrille(jeu,monPion2, monPion2.joueur);


                    break;

                //Heuristique 3
                case "Niveau 3" :
                    int plusGrandScore =0;
                    JButton bouton3 = new JButton();
                    for(Map.Entry<JButton, Integer> valMax :  jeu.coupsPossiblesScore.entrySet()){
                        if(valMax.getValue()>plusGrandScore){
                            plusGrandScore = valMax.getValue();
                            bouton3 = valMax.getKey();
                        }
                    }
                    Pion monPion3 = new Pion(jeu.joueurOrdi, bouton3.getName(), jeu, bouton3);
                    try {
                        monPion3.bouton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../White_Circle.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    this.joueurOrdi.score+=1;
                    ActualisatioDeGrille(jeu,monPion3, monPion3.joueur);
                    break;


                    //MINMAX







                default : break;
            }
        }
        MAJCouleur(jeu);
        jeu.joueurCourant=jeu.joueurH;
        CoupsPossibles(jeu);

    }

    public int Minmax(int profondeur, Jeu jeu){
        if((profondeur==0)||((jeu.joueurH.score + jeu.joueurOrdi.score) >=64))
            return 0000;

        int bestScore;

        if(jeu.joueurCourant==jeu.joueurOrdi) {
            bestScore = -5000;
            for (Map.Entry<JButton, Integer> valMax : jeu.coupsPossiblesScore.entrySet()) {
                Pion pion = new Pion(jeu.joueurOrdi, valMax.getKey().getName(), this, valMax.getKey());
                int ligne = Character.getNumericValue(pion.position.charAt(0));
                int colonne = Character.getNumericValue(pion.position.charAt(1));

                jeu.joueurCourant = jeu.joueurH;
                int score = Minmax(profondeur - 1, jeu);
                jeu.joueurCourant = jeu.joueurOrdi;

                jeu.pionsJoues[ligne][colonne] = null;
                if (score > bestScore) {
                    bestScore = score;
                    Pion pion2 = new Pion(jeu.joueurOrdi, valMax.getKey().getName(), this, valMax.getKey());
                }

            }
        }
            else { //type MIN = adversaire
                bestScore = +5000;
                for (Map.Entry<JButton, Integer> valMax :  jeu.coupsPossiblesScore.entrySet()) {
                    Pion pion = new Pion(jeu.joueurOrdi, valMax.getKey().getName(), this, valMax.getKey());
                    int ligne = Character.getNumericValue(pion.position.charAt(0));
                    int colonne = Character.getNumericValue(pion.position.charAt(1));

                    jeu.joueurCourant=jeu.joueurH;
                    int score = Minmax(profondeur - 1, jeu);
                    jeu.joueurCourant=jeu.joueurOrdi;

                    jeu.pionsJoues[ligne][colonne] = null;
                    if (score < bestScore) {
                        bestScore = score;
                        Pion pion3 = new Pion(jeu.joueurOrdi, valMax.getKey().getName(), this, valMax.getKey());
                    }
                }
            }
            return bestScore;
        }


    //ON IDENTIFIE LES COUPS POSSIBLES A CHAQUE TOUR
    //ET ON REMPLI LES MAP POUR LE TRAITEMENT DES ALGO DE L'IA A PARTIR DES COUPS POSSIBLES IDTENTIFES
    public void CoupsPossibles(Jeu jeu){
        //parcours de la grille
        modifiePoint();
        jeu.coupsPossiblesScore.clear();
        jeu.grilleCoupsPossible.clear();
        jeu.coupsPossible=0;
        System.out.println("couppossible"+ jeu.joueurCourant.nom);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(jeu.pionsJoues[i][j]==null){
                    int scoreTemp=0;
                    int scoreTotal=jeu.tabPoint[i][j];
                    int pionMaxRetournes =0;
                    boolean positionOK=false;
                    int pionAdverseTrouve=0;
                    int ligne = i;
                    int col = j;


                    while(ligne>=1 && col>=1){
                        if(jeu.pionsJoues[ligne-1][col-1]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne-1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            pionMaxRetournes+=pionAdverseTrouve;
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col-1].joueur!=jeu.joueurCourant){
                            if(ligne-1==0 || col-1==0){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne-1][col-1];
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
                    while(ligne>=1){
                        if(jeu.pionsJoues[ligne-1][col]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne-1][col].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col].joueur!=jeu.joueurCourant){
                            if(ligne-1==0){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne-1][col];
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
                    while(ligne>=1 && col <=6){
                        if(jeu.pionsJoues[ligne-1][col+1]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne-1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne-1][col+1].joueur!=jeu.joueurCourant){
                            if(ligne-1==0 || col+1==7){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne-1][col];
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
                    while(col >=1){
                        if(jeu.pionsJoues[ligne][col-1]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col-1].joueur!=jeu.joueurCourant){
                            if(col-1==0){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne][col-1];
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
                    while(col <=6){
                        if(jeu.pionsJoues[ligne][col+1]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne][col+1].joueur!=jeu.joueurCourant){
                            if(col+1==7){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne][col+1];
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
                    while(ligne<=6 && col >=1) {
                        if(jeu.pionsJoues[ligne+1][col-1]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve==0){
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col-1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col-1].joueur!=jeu.joueurCourant){
                            if(col-1==0 || ligne+1==7){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne+1][col-1];
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
                    while(ligne<=6){
                        if(jeu.pionsJoues[ligne+1][col]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne+1][col].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col].joueur!=jeu.joueurCourant){
                            if(ligne+1==7){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            //System.out.println(col);
                            //System.out.println(ligne);
                            scoreTemp+=jeu.tabPoint[ligne+1][col];
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
                    while(ligne<=6 && col <=6){
                        if(jeu.pionsJoues[ligne+1][col+1]==null){
                            scoreTemp=0;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve==0)
                            break;
                        if(jeu.pionsJoues[ligne+1][col+1].joueur==jeu.joueurCourant && pionAdverseTrouve!=0){
                            positionOK=true;
                            scoreTotal+=scoreTemp;
                            scoreTemp=0;
                            pionMaxRetournes+=pionAdverseTrouve;
                            pionAdverseTrouve=0;
                            break;
                        }
                        if(jeu.pionsJoues[ligne+1][col+1].joueur!=jeu.joueurCourant){
                            if(col+1==7 || ligne+1==7){
                                scoreTemp=0;
                                pionAdverseTrouve=0;
                                break;
                            }
                            scoreTemp+=jeu.tabPoint[ligne+1][col+1];
                            pionAdverseTrouve+=1;
                        }
                        else{
                            break;
                        }
                        col+=1;
                        ligne+=1;
                    }
                    if(positionOK){
                        //System.out.println("coup possible " + i + "," + j + "pions retournés " + pionMaxRetournes);
                        System.out.println("coup possible " + i + "," + j + "score obtenu " + scoreTotal);
                        jeu.grilleboutons[i][j].setBackground(Color.red);
                        jeu.grilleboutons[i][j].setEnabled(true);
                        jeu.coupsPossiblesScore.put(jeu.grilleboutons[i][j], scoreTotal);
                        jeu.grilleCoupsPossible.put(jeu.grilleboutons[i][j], pionMaxRetournes);
                        jeu.coupsPossible+=1;
                        positionOK=false;
                    }
                }
            }
        }
        //System.out.println(jeu.coupsPossible);
    }

    public void MAJCouleur(Jeu jeu){
        for(int i=0; i<8;i++){
            for(int j=0;j<8;j++){
                jeu.grilleboutons[i][j].setBackground(new java.awt.Color(0.1f, 0.80f, 0.2f, 1f));
                jeu.grilleboutons[i][j].setEnabled(false);
            }
        }
    }

    public void ActualisatioDeGrille(Jeu jeu, Pion pion, Joueur joueur) {
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
        jeu.MAJCouleur(jeu);
        jeu.CoupsPossibles(jeu);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
//		Jeu partie = new Jeu();
//		Pion p1 = new Pion(partie, "")




    }

}
