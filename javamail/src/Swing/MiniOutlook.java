package Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniOutlook extends JFrame implements ActionListener {
    private JTabbedPane menu;
    private JPanel panel1;
    private JTextPane texte;
    private JTextField destinataire;
    private JTextField objet;
    private JButton ajouterUnePièceJointeButton;
    private JButton envoyerButton;
    private JButton actualiserButton;

    public MiniOutlook() {
        panel1.setVisible(true);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MiniOutlook");
        setSize(800,600);

        ajouterUnePièceJointeButton.addActionListener(this);
        envoyerButton.addActionListener(this);

        // configurer la liaison avec le smails 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ajouterUnePièceJointeButton)
        {
            ajouterPieceJointe();
        }
        if(e.getSource()==envoyerButton)
        {
            envoyer();
        }
    }
    public void ajouterPieceJointe() {
        System.out.println("hello piece jointe");
    }
    public void envoyer() {
        System.out.println("test d'envois");
        System.out.println("destinataire = "+ destinataire.getText());
        System.out.println("objet = "+ objet.getText());
        System.out.println("text = "+ texte.getText());

        // ici que on va utiliser le systeme  mail et envoyer
    }

}

