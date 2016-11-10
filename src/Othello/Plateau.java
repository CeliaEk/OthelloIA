package Othello;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.sun.prism.paint.Color;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.beans.PropertyChangeEvent;

public class Plateau {


    public static Jeu partie = new Jeu();
    public static JLabel scoreMachine = new JLabel("");
    public static JLabel scoreHumain = new JLabel("");
    private JFrame frame;
    private JTextField expression;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    UIManager.setLookAndFeel(new MetalLookAndFeel());
                    Plateau window = new Plateau();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Plateau() {
        initialize();
        Jeu.CoupsPossibles(partie);
    }




    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        java.awt.Color fondCase = new java.awt.Color(0.1f, 0.80f, 0.2f, 1f);
        frame = new JFrame("Notre jeu Othello");

        frame.setBounds(100, 100, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelHaut = new JPanel();
        frame.getContentPane().add(panelHaut, BorderLayout.NORTH);
        panelHaut.setLayout(new GridLayout(1,9));


        JPanel panelBas = new JPanel();
        frame.getContentPane().add(panelBas, BorderLayout.SOUTH);
        panelBas.setLayout(new FlowLayout());


        JPanel panelCases = new JPanel();
        frame.getContentPane().add(panelCases, BorderLayout.CENTER);
        panelCases.setLayout(new GridLayout(9,8));
        //panelCases.setBackground(java.awt.Color.GREEN);

        JPanel panelGauche = new JPanel();
        frame.getContentPane().add(panelGauche, BorderLayout.WEST);
        panelGauche.setLayout(new GridLayout(9,1));

        JPanel panelDroit = new JPanel();
        frame.getContentPane().add(panelDroit, BorderLayout.EAST);
        panelDroit.setLayout(new GridLayout(9,1));

        JLabel enTete = new JLabel("");
        enTete.setText("Que le meilleur gagne");
        enTete.setHorizontalAlignment(SwingConstants.CENTER);
        enTete.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        panelBas.add(enTete);

        JButton restart = new JButton("Rejouer");
        restart.setHorizontalAlignment(SwingConstants.CENTER);
        restart.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Jeu.reset(partie);
                try {
                    partie.grilleboutons[3][3].setIcon(new ImageIcon(ImageIO.read(getClass().getResource(partie.joueurOrdi.couleur))));
                    partie.grilleboutons[3][4].setIcon(new ImageIcon(ImageIO.read(getClass().getResource(partie.joueurH.couleur))));
                    partie.grilleboutons[4][3].setIcon(new ImageIcon(ImageIO.read(getClass().getResource(partie.joueurH.couleur))));
                    partie.grilleboutons[4][4].setIcon(new ImageIcon(ImageIO.read(getClass().getResource(partie.joueurOrdi.couleur))));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Jeu.MAJCouleur(partie);
                Jeu.CoupsPossibles(partie);
            }
        });
        panelBas.add(restart);

        scoreHumain.setText("Score joueur :" + partie.joueurH.score);
        scoreHumain.setHorizontalAlignment(SwingConstants.CENTER);
        scoreHumain.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        panelHaut.add(scoreHumain);


        scoreMachine.setText("Score machine :" + partie.joueurOrdi.score);
        scoreMachine.setHorizontalAlignment(SwingConstants.CENTER);
        scoreMachine.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        panelHaut.add(scoreMachine);

        panelBas.setBorder(null);
        panelHaut.setBorder(null);
        panelCases.setBorder(null);
        panelGauche.setBorder(null);
        panelDroit.setBorder(null);



        JButton A = new JButton("A");
        A.setEnabled(false);
        panelCases.add(A);

        JButton B = new JButton("B");
        B.setEnabled(false);
        panelCases.add(B);

        JButton C = new JButton("C");
        C.setEnabled(false);
        panelCases.add(C);

        JButton D = new JButton("D");
        D.setEnabled(false);
        panelCases.add(D);

        JButton E = new JButton("E");
        E.setEnabled(false);
        panelCases.add(E);

        JButton F = new JButton("F");
        F.setEnabled(false);
        panelCases.add(F);

        JButton G = new JButton("G");
        G.setEnabled(false);
        panelCases.add(G);

        JButton H = new JButton("H");
        H.setEnabled(false);
        panelCases.add(H);

        //Ici on va creer notre grille de 64 boutons cliquable
        for(int ligne = 0; ligne < 8 ; ligne ++){
            for(int colonne = 0; colonne <8 ; colonne ++){

                //on récupère les coordonnées du bouton
                String button = ""+ligne + colonne;
                JButton newButton = new JButton();
                newButton.setEnabled(false);

                //on y applique la mise en forme
                newButton.setBackground(fondCase);
                newButton.setUI((ButtonUI)MetalButtonUI.createUI(newButton));
                //on lui attribut un nom correspondant à ses coordonnées
                newButton.setName(button);
                partie.grilleboutons[ligne][colonne]=newButton;
                switch (button){
                    case "33" :
                        new Pion(partie.joueurOrdi, button, partie,newButton);
                        try {
                            newButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../White_Circle.png"))));
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        newButton.setEnabled(false);
                        break;
                    case "34" :
                        new Pion(partie.joueurH, button, partie,newButton);
                        try {
                            newButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Black_Circle.png"))));
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        newButton.setEnabled(false);
                        break;
                    case "43" :
                        new Pion(partie.joueurH, button, partie,newButton);
                        try {
                            newButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../Black_Circle.png"))));
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        newButton.setEnabled(false);
                        break;
                    case "44" :
                        new Pion(partie.joueurOrdi, button, partie,newButton);
                        try {
                            newButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../White_Circle.png"))));
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        newButton.setEnabled(false);
                        break;
                    default :
                        //on ajoute l'évennement clic où l'on va gérer certaines actions
                        newButton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {

                                try {
                                    partie.TraitementDuTour(newButton);
                                    Jeu.MAJCouleur(partie);
                                    Jeu.CoupsPossibles(partie);
                                } catch (Exception e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }


                                //------------------------------------
                            }
                        });
                        break;
                }






                panelCases.add(newButton);

            }


        }






        JButton vide = new JButton();
        vide.setEnabled(false);
        panelGauche.add(vide);


        JButton A1 = new JButton("1");
        A1.setEnabled(false);
        panelGauche.add(A1);

        JButton B1 = new JButton("2");
        B1.setEnabled(false);
        panelGauche.add(B1);

        JButton C1 = new JButton("3");
        C1.setEnabled(false);
        panelGauche.add(C1);

        JButton D1 = new JButton("4");
        D1.setEnabled(false);
        panelGauche.add(D1);

        JButton E1 = new JButton("5");
        E1.setEnabled(false);
        panelGauche.add(E1);

        JButton F1 = new JButton("6");
        F1.setEnabled(false);
        panelGauche.add(F1);

        JButton G1 = new JButton("7");
        G1.setEnabled(false);
        panelGauche.add(G1);

        JButton H1 = new JButton("8");
        H1.setEnabled(false);
        panelGauche.add(H1);






    }

}
